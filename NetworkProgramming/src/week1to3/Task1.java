package week1to3;

import java.io.*;

public class Task1 {
	public static String task1a() throws IOException{
		DataInputStream stream = new DataInputStream(System.in);
		String letters = stream.readLine();
		String uLetters = letters.toUpperCase();
		stream.close();
		System.out.println("toUppercase...done");
		return uLetters;
	}

	public static void task1aOutput(String uLetters) throws IOException{
		File file = new File("task1aOutput.txt");
		file.createNewFile();
		
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));
		stream.writeChars(uLetters);
		stream.close();
		System.out.println("1aOutput.txt...done");
	}
	
	public static void task1b() throws IOException{
		File file = new File("task1aOutput.txt");
		DataInputStream stream = new DataInputStream(new FileInputStream(file));
		System.out.println(stream.readLine());
		System.out.println("Done reading");
		stream.close();
	}
}
