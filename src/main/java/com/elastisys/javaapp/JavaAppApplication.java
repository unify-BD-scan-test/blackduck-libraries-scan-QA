package com.elastisys.javaapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.json.JsonSanitizer;
import org.codehaus.plexus.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.elastisys.javaapp.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamer;
import java.io.ObjectStreamException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootApplication
@RestController
public class JavaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaAppApplication.class, args);
	}

    @GetMapping("/xstream")
        public String xstream(){

 

            Test t = new Test(50,10);
            
            String xml = ""; 
            try {
                XStream xstream = new XStream();
                xstream.alias("test", Test.class);
                xml = new XStreamer().toXML(xstream, t);
            }
            catch (Exception e) {
                // System.out.println(e);
            }
            return  xml;
    }

    @GetMapping("/sanitize")
        public String sanitize(){

           String jsonString = "{\"key1\":\"value1\",\"type\":\"Booking\",\"sid\":\"A435211\",\"region\":\"ASIA\","
				+ "\"fetchFromFile\":\"false\",\"service\":\"true\",\"isEom\":\"true\",*#@!}";
            String response = JsonSanitizer.sanitize(jsonString);

            return response;
    }

    @GetMapping("/hello")
        public String hello() {
            String msg = "Hello World";
            return msg;
        }

}
