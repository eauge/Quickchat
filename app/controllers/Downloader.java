package controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Downloader {
	
	public Downloader() {
		
	}
	
	public void download(String url, String path) throws IOException {
		URL website = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(path);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);	
	}
	
}
