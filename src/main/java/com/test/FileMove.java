package com.test;

import com.exception.FileNotMovedException;
import com.exception.FolderWatchException;
import com.exception.InvalidPathException;
import com.util.FolderWatcherAndMover;

import java.nio.file.Paths;
import java.util.Scanner;

public class FileMove {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the source folder: \t");
		String sourceFolder = scanner.nextLine();
		System.out.println("Enter the destination folder: \t");
		String destinationFolder = scanner.nextLine();
		
		try {
			FolderWatcherAndMover folderWatcher = new FolderWatcherAndMover(Paths.get(sourceFolder), Paths.get(destinationFolder));
			System.out.println(sourceFolder+" is being watched\n\nPress ctrl+c to stop the program");
			folderWatcher.watchAndMove();
		} catch (FolderWatchException e) {
			e.printStackTrace();
		} catch (FileNotMovedException e) {
			e.printStackTrace();
		} catch (InvalidPathException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
