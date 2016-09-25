package com.util;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.exception.FileNotMovedException;
import com.exception.FolderWatchException;
import com.exception.InvalidPathException;

public class FolderWatcherAndMover {
	private Path pathSource;
	private Path pathDest;

	/**
	 * @author Yash
	 * @param pathSource
	 * @throws InvalidPathException
	 */
	public FolderWatcherAndMover(Path pathSource, Path pathDest) throws InvalidPathException {
		/*
		 * Creates a FolderWatcher for the provided path
		 * Throws an InvalidPathException if the provided path
		 * does not point to a valid file directory
		 */
		try {
			if((Boolean) Files.getAttribute(pathSource, "basic:isDirectory", NOFOLLOW_LINKS)) {
				//FolderWatcher is bound to the path provided as the path is valid
				this.pathSource = pathSource;
			} else {
				//Letting the end user know that the path provided is not a valid one
				throw new InvalidPathException("Path "+pathSource+" is not a valid directory.");
			}
			if((Boolean) Files.getAttribute(pathDest, "basic:isDirectory", NOFOLLOW_LINKS)) {
				//FolderWatcher is bound to the path provided as the path is valid
				this.pathDest = pathDest;
			} else {
				//Letting the end user know that the path provided is not a valid one
				throw new InvalidPathException("Path "+pathDest+" is not a valid directory.");
			}
			
		} catch (IOException e) {
			//Letting the user know that an Exception is breaking the flow of the program
			throw new InvalidPathException(e.getMessage());
		}
	}

	public void watchAndMove() throws FolderWatchException, FileNotMovedException {
		//Creating a WatchService for the filesystem of the path provided
		try(WatchService service = this.getPathSource().getFileSystem().newWatchService()) {
			//Registering service to the create event of the filesystem
			this.getPathSource().register(service, ENTRY_CREATE);
			WatchKey key = null;
			while(true) {
				key = service.take();
				Kind<?> kind = null;
				for(WatchEvent<?> watchEvent : key.pollEvents()) {
					kind = watchEvent.kind();
					if (OVERFLOW == kind) {
						continue;
					} else if (ENTRY_CREATE == kind) {
						//Takes the path of the newly created file or folder from the event
						Path newPath = ((WatchEvent<Path>) watchEvent).context();
						System.out.println("Found File. "+newPath+"\n");
						//Implement a fileMover for moving the file from source folder to destination folder
						FileMover fileMover = new FileMover(this.getPathSource(), this.getPathDest(), newPath.toString());
						if(fileMover.moveFile()) {
							//Notifying the user of the successful movement of file
							System.out.print("Moved "+newPath+" to "+this.getPathDest().toString()+"\n");
						} else {
							System.out.println("File couldn't be moved");
						}
					}
				}
				
				if(!key.reset()) {
					break;
				}
			}
			
		} catch(IOException ioe) {
			//Throws a FolderWatchException when there is trouble in closing 
			//or cleaning up the WatchService
			throw new FolderWatchException(ioe.getMessage());
		} catch(InterruptedException ie) {
			//Throws a FolderWatchException when there is trouble in 
			throw new FolderWatchException(ie.getMessage());
		} 
		
	}

	public Path getPathSource() {
		return pathSource;
	}

	public void setPathSource(Path path) {
		this.pathSource = path;
	}

	public Path getPathDest() {
		return pathDest;
	}

	public void setPathDest(Path pathDest) {
		this.pathDest = pathDest;
	}
}