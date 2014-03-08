import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.FileSystem;


public class FileSystemTest {

	private FileSystem fs;
	
	@Before
	public void init() {
		String basedir = System.getProperty("user.dir") + "/public/cache/";
		fs = new FileSystem(basedir);
	}
	
	@Test
    public void downloadFileHTTP() {
		String url = "http://www.gnu.org/software/emacs/refcards/pdf/refcard.pdf";
		download(url);
    }
	
	@Test 
	public void downloadFileHTTPS() {
		String url = "https://dl.dropboxusercontent.com/u/23829853/Course%20description.pdf";
		download(url);
	}

	public void download(String url) {
		fs.downloadFile(url);
		
		String filepath = fs.getFilePath(url);
		System.out.println("file path: " + filepath);
		assertTrue(fs.doesFileExist(url, filepath));
		assertTrue(fs.getFileSize(filepath) > 0);
	}
}
