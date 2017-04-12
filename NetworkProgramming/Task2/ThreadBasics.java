import java.io.*;
import java.net.*;
import java.util.*;

class runnable implements Runnable {
	
	private String thread;
	
	public runnable(String thread) throws IOException{
		this.thread = thread;
	}

	public void run() {
		System.out.println("Running thread " + thread);
		try{
			Scanner sc = new Scanner(System.in);
			String inputLine;
			while ((inputLine = sc.nextLine()) != null){
				System.out.println("Thread: "+ inputLine);
				System.out.println(inputLine);
				
				if (inputLine.equals("x")){
					System.out.println("Thread close");
					System.exit(0);
				}
			}
			
			return;
		}
		catch (Exception e){
			System.out.println("Thread Error: " + e);
		}
		
	}
}

public class ThreadBasics {

	public static void main(String[] args) {
		try {
			Thread first = new Thread(new runnable("task 1"));
			first.start();
		}
		catch (IOException e){
			System.out.println(e);
		}

	}

}
