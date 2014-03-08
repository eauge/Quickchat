package controllers;

import java.io.*;
import java.nio.channels.*;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

public class FileSystem {
	
	private String basedir;
	private Properties routes;
	private final String routesFileName = "routes.txt";
	private File routesFile;
	
	public FileSystem(String basedir) {
		this.basedir = basedir;
		this.routes = new Properties();
		readRoutesFile();
	}
	
	public void readRoutesFile() {
		routesFile = new File(this.basedir, routesFileName);
		
		try {
			if (!routesFile.exists()) {
				routesFile.createNewFile();
			} else {
				routes.load(new FileInputStream(routesFile));
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Routes file could not be found");	
		}
	}
	
	public void addFile(String url, String path) {
		routes.setProperty(url, path);
		try {
			routes.store(new FileOutputStream(routesFile), "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String generateNameForFile(String url) {
		String pathAssigned = routes.getProperty(url);
		
		if(pathAssigned != null) {
			// the url already has a name assigned 
			return pathAssigned;
		} 
		
		int index = 1;
		
		String filename = FilenameUtils.getName(url).replace("%20", " ");
		String ext = getFileExtension(filename);
		String fileWithoutExtension = getFileNameWithoutExtension(filename);
		String filepath = null;
		
		do {
			filepath = combine(basedir, fileWithoutExtension) + "-" + Integer.toString(index) + "." + ext;
		} while(routes.containsValue(filepath));
		
		return filepath;
	}
	
	public static String combine (String path1, String path2)
	{
	    File file1 = new File(path1);
	    File file2 = new File(file1, path2);
	    return file2.getPath();
	}
	
	
	private String getFileExtension(String filename) {
		return FilenameUtils.getExtension(filename);
	}
	
	private String getFileNameWithoutExtension(String filename) {		
		return FilenameUtils.removeExtension(filename); 
	}
	
	public void downloadFile(String url) {
		String filepath = generateNameForFile(url);
		System.out.println("filepath: " + filepath);
		
		if (doesFileExist(url, filepath)) {
			// no need to download a file in the routes.txt file
			// that is in the file system
			return;
		}
		
		try {
			downloadFileFromUrl(url, filepath);	
			addFile(url, filepath);
		} catch(IOException e) {
			System.err.println("File at " + url + " could not be download");
		}
	}
	
	public void downloadFileFromUrl(String url, String path) throws IOException {
		URL website = new URL(url);
		System.out.println("download file from: " + url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(path);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		System.out.println("file from: " + url + " downloaded");
	}
	
	// we say that a file exists when it can be found in the routes file
	// and it's present in the file system
	public boolean doesFileExist (String url, String path) {
		if (doesFileExistInRoutesFile(url) && 
				doesFileExistInFileSystem(path)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getFilePath(String url) {
		if (doesFileExistInRoutesFile(url)) {
			return routes.getProperty(url);
		} else {
			return null;
		}
	}
	
	private boolean doesFileExistInRoutesFile(String url) {
		String filepath = routes.getProperty(url, null);
		if (filepath == null) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean doesFileExistInFileSystem(String path) {
		File f = new File(path);
		if(f.exists() && !f.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}
	
	public long getFileSize(String path) {
		File f = new File(path);
		return f.length();
	}
}
