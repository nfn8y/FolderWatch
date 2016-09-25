import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.junit.Test;

import com.exception.FileNotMovedException;
import com.util.FileMover;
import com.util.FolderWatcherAndMover;

public class FolderWatchTester {

	@Test
	public void TestForException() {
		/*
		 * Expects the method to throw an exception as the directories demanded are not supposed to be present
		 * Test case will fail if concerned folders are however, manually created and then the test is run
		 */
		try {
			new FolderWatcherAndMover(Paths.get("created-for-testing"), Paths.get("created-for-testing2"));
			fail("Expected an exception because the paths don't exist");
		} catch(Exception e) {
			
		}
	}
	
	@Test
	public void TestFileMovement() {
			try {
				/*
				 * Creates two directories, creates file in one of them and calls a filemover instance to move
				 * the file to another. Fails in case of exceptions and succeeds if the file is successfully
				 * delivered to the second folder.
				 */
				Path dirSource = Files.createDirectory(Paths.get(System.getProperty("user.dir")+File.separator+"created-for-testing"));
				Path dirDestin = Files.createDirectory(Paths.get(System.getProperty("user.dir")+File.separator+"created-for-testing2"));
				File newFile = new File(System.getProperty("user.dir")+File.separator+"created-for-testing"+File.separator+"abc.file");
				FileMover fileMover = new FileMover(dirSource, dirDestin, newFile.getName());
				if(!newFile.exists()) {
					newFile.createNewFile();
				}
				boolean fileMoved =  fileMover.moveFile();
				new File(dirDestin.toString()+File.separator+newFile.getName()).delete();
				Files.delete(dirDestin);
				Files.delete(dirSource);
				assertEquals(true, fileMoved);
			} catch (FileNotMovedException e) {
				fail(e.getMessage());
			} catch (IOException e) {
				fail(e.getMessage());
			}
	}

}
