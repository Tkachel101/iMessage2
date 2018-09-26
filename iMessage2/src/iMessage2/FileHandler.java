package iMessage2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileHandler {
	private File file;

	public FileHandler(File file) {
		this.file = file;
	}

	public FileHandler(String file) {
		this.file = new File(file);
	}

	public void openFile(String text) {
		try {
			PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
			writer.print(text);
			writer.close();
		} catch (IOException e) {
			System.err.println("Error in writeFile() for file: " + file);
			System.err.println(e.getMessage());
		}
	}

	public void appendFile(String text) {
		if (!file.exists()) {
			openFile("");
		}
		try {
			FileWriter fw = new FileWriter(file.getAbsolutePath(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			out.println(text);
			out.close();
		} catch (IOException e) {
			System.err.println("Error in appendFile() for file: " + file.getName());
		}
	}

	public void clearFile() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error in clearFile() for file: " + file.getName());
		}
	}

	public String readFile() {
		StringBuilder fileContents = new StringBuilder((int) file.length());
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String lineSeparator = System.getProperty("line.separator");
		try {
			while (scanner.hasNextLine()) {
				fileContents.append(scanner.nextLine() + lineSeparator);
			}
			return fileContents.toString();
		} finally {
			scanner.close();
		}
	}

	public String toString() {
		return file.getAbsolutePath();
	}
}
