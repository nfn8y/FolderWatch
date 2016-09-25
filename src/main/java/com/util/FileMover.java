package com.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.exception.FileNotMovedException;

public class FileMover {
	private Path source;
	private Path destination;
	public FileMover(Path source, Path destination, String fileName) {
		super();
		this.source = Paths.get(source.toString()+File.separator+fileName);
		this.destination = Paths.get(destination.toString()+File.separator+fileName);
	}
	public boolean moveFile() throws FileNotMovedException {
		boolean fileMoved = true;
		//Deleting any existing file with same name 
		//In order to implement overwriting
		try {
			Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
		} catch(NoSuchFileException e) {
			fileMoved = false;
			System.out.println("File was not created or has been deleted");
		} catch(IOException e) {
			fileMoved = false;
			System.out.println("File in use by another process");
		} catch(Exception e) {
			fileMoved = false;
			throw new FileNotMovedException(e.getMessage());
		}
		return fileMoved;
	}
}
