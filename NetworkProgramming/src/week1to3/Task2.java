package week1to3;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class Task2 {
	public static void task2a() throws FileNotFoundException, IOException{
		FileInputStream fIn = new FileInputStream("task1aOutput.txt");
		FileOutputStream fOut = new FileOutputStream("task2a.txt");
		
		Deflater deflater = new Deflater();
		DeflaterOutputStream dos = new DeflaterOutputStream(fOut, deflater);
		FileCopy(fIn, dos);
		dos.close();
	}
	
	public static void task2b() throws FileNotFoundException, IOException{
		FileInputStream fIn = new FileInputStream("task2a.txt");
		FileOutputStream fOut = new FileOutputStream("task2b.txt");
		
		Inflater inflater = new Inflater();
		InflaterOutputStream ios = new InflaterOutputStream(fOut, inflater);
		FileCopy(fIn, ios);
		ios.close();
	}
	
	public static void FileCopy(InputStream is, OutputStream os) throws IOException{
		byte[] buffer = new byte[500];
		int len;
		while((len = is.read(buffer)) > 0){
			os.write(buffer, 0, len);
		}
		is.close();
		os.close();
	}
}
