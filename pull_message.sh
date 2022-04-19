while true; do curl -Ss -XPOST http://localhost:8563/api -d '

{

  "action":"pull_results",

  "sessionId" : "4be040c5-643f-4b99-919e-7e07cd97e90b",

  "consumerId" : "57e4b47a26e24dba9adf1d764fcc9464_2"

}

' | json_pp; sleep 2; done
