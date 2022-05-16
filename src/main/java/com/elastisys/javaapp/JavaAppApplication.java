package com.elastisys.javaapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.JdkZlibDecoder;
import io.netty.handler.codec.compression.JdkZlibEncoder;
import org.codehaus.plexus.archiver.zip.ZipArchiver;
import org.codehaus.plexus.archiver.zip.ZipUnArchiver;
import com.google.json.JsonSanitizer;
// import org.codehaus.plexus.logging.console.ConsoleLogger;
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

	private static byte[] compress(byte[] data) {
        JdkZlibEncoder enc = new JdkZlibEncoder();
        EmbeddedChannel channel = new EmbeddedChannel(enc);
        channel.writeOutbound(Unpooled.wrappedBuffer(data));
        channel.finish();

        int outputSize = 0;
        ByteBuf o;
        List<ByteBuf> outbound = new ArrayList<>();
        while ((o = channel.readOutbound()) != null) {
            outbound.add(o);
            outputSize += o.readableBytes();
        }

        byte[] output = new byte[outputSize];
        int readCount = 0;
        for (ByteBuf b : outbound) {
            int readableBytes = b.readableBytes();
            b.readBytes(output, readCount, readableBytes);
            b.release();
            readCount += readableBytes;
        }

        return output;
    }

    private static byte[] decompress(byte[] data) {
        JdkZlibDecoder dec = new JdkZlibDecoder();
        EmbeddedChannel channel = new EmbeddedChannel(dec);
        channel.writeInbound(Unpooled.wrappedBuffer(data));
        channel.finish();

        int outputSize = 0;
        ByteBuf o;
        List<ByteBuf> inbound = new ArrayList<>();
        while ((o = channel.readInbound()) != null) {
            inbound.add(o);
            outputSize += o.readableBytes();
        }

        byte[] output = new byte[outputSize];
        int readCount = 0;
        for (ByteBuf b : inbound) {
            int readableBytes = b.readableBytes();
            b.readBytes(output, readCount, readableBytes);
            b.release();
            readCount += readableBytes;
        }

        return output;
    }


	@GetMapping("/decompress")
		public String decompress(@RequestParam(value = "data", defaultValue = "example data") String data) {
		byte[] original = data.getBytes(StandardCharsets.UTF_8);
        byte[] compressed = compress(original);
        byte[] decompressed = decompress(compressed);
		String output = "Original: " + new String(original) + "\n"; 
		output += "Compressed: " + new String(compressed) + "\n"; 
		output += "Decompressed: " + new String(decompressed);

		return output;
	}

    @GetMapping("/vulnerable")
        public String sanitize(){

            String jsonString = "{\"key1\":\"value1\",\"type\":\"Booking\",\"sid\":\"A435211\",\"region\":\"ASIA\","
				+ "\"fetchFromFile\":\"false\",\"service\":\"true\",\"isEom\":\"true\",*#@!}";
            String response = JsonSanitizer.sanitize(jsonString);

            Test t = new Test(50,10);
            int res = t.HelloWorld();
            String xml = ""; 
            try {
                XStream xstream = new XStream();
                xstream.alias("test", Test.class);
                xml = new XStreamer().toXML(xstream, t);
            }
            catch (Exception e) {
                // System.out.println(e);
            }
            return response + xml;
    }

    @GetMapping("/hello")
        public String hello(){

            String response = "Hello CNOSA Team";

            return response;
    }


}
