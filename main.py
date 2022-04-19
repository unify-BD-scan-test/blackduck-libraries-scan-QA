import requests 
from time import sleep 
import threading

ENDPOINT = "http://localhost:8563/api"

class Arthas(object):
  def __init__(self):
    r = requests.post(ENDPOINT, json={'action': 'init_session'})
    if r.json()['state'] == "SUCCEEDED":
      self.sessionId = r.json()['sessionId']
      self.consumerId = r.json()['consumerId']
    else :
      raise RuntimeError('Could not initialize a new Arthas Session! Try again.')

  def join_session(self):
    r = requests.post(ENDPOINT, json={'action': 'join_session', 'sessionId': self.sessionId})
    if r.json()['state'] == "SUCCEEDED":
      print("Session %s was joined" % self.sessionId)
      self.consumerId = r.json()['consumerId']
    else :
      raise RuntimeError(r.json()['message'])

  def async_exec(self, command):
    self.command = command 
    r = requests.post(ENDPOINT, json={'action': 'async_exec', 'sessionId': self.sessionId, 'command': command})
    if r.json()['state'] == "SCHEDULED":
      print(f"Command : %s was scheduled" % command)
    else :
      raise RuntimeError(r.json()['message'])

  def interrupt_job(self):
    r = requests.post(ENDPOINT, json={'action': 'interrupt_job', 'sessionId': self.sessionId})
    if r.json()['state'] == "SUCCEEDED":
      print("Foreground job was interrupted")
    else :
      raise RuntimeError(r.json()['message'])

  def close_session(self):
    r = requests.post(ENDPOINT, json={'action': 'close_session', 'sessionId': self.sessionId})
    if r.json()['state'] == "SUCCEEDED":
      print(f"Session %s is closed" % self.sessionId)
    else :
      raise RuntimeError(r.json()['message'])


def pull_results(sessionId, consumerId, command):
  gotResults = False
  initialRequest = True
  while not gotResults:
    r = requests.post(ENDPOINT, json={'action': 'pull_results', 'sessionId': sessionId, 'consumerId': consumerId})
    if r.json()['state'] == "SUCCEEDED":
      if initialRequest:
        initialRequest = False
      else :
        if len(r.json()['body']['results']) > 0:
          gotResults = True
          with open(f"%s.stack" % ".".join(command.split(" ")[1:]), "w") as f:
            for stacktrace in r.json()['body']['results'][0]['stackTrace'] :
              f.write("at %s.%s(%s:%s) \n" % (stacktrace['className'], stacktrace['methodName'], stacktrace['fileName'], stacktrace['lineNumber']))
        else :
          print("Waiting for results ....")
          sleep(2)
    else :
      raise RuntimeError(r.json()['message'])



a = Arthas()
a.async_exec("stack com.google.json.JsonSanitizer sanitizeString")
ta = threading.Thread(target=pull_results, args=(a.sessionId, a.consumerId, a.command))
ta.start()

b = Arthas()
b.async_exec("stack com.thoughtworks.xstream.XStreamer toXML")
tb = threading.Thread(target=pull_results, args=(b.sessionId, b.consumerId, b.command))
tb.start()

ta.join()
tb.join()

a.interrupt_job()
b.interrupt_job()
a.close_session()
b.close_session()

