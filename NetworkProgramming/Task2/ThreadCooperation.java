import java.io.*;
import java.net.*;
import java.util.*;

//class for reading input function
class InputLine {
	private String input;
	public InputLine(){
		Scanner sc = new Scanner(System.in);
		input = sc.nextLine();
		
		//exit the program if input = "x"
		if(input.equals("x")){
			System.out.println("Exiting Threads");
			System.exit(0);
		}
	}
	//for global usage on input
	public String toString(){return input;}
}

class InputThread extends Thread {
	private ThreadCooperation tc;
	
	//initializing inputThread
	public InputThread(ThreadCooperation tc) throws IOException{
		this.tc = tc;
		start();
	}

	public void run() {
		System.out.println("Running input thread");
		while(true){
			while(tc.inputLine == null){
				synchronized(this){
					try{
						wait();
					} catch(InterruptedException e){
						throw new RuntimeException(e);
					}
				}
				tc.inputLine = null;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
		}
		
	}
}

class PrinterThread extends Thread {
	private ThreadCooperation tc;
	private InputThread iThread;
	
	//initialing printerThread
	public PrinterThread(ThreadCooperation tc, InputThread iThread) throws IOException{
		this.iThread = iThread;
		this.tc = tc;
		start();
	}

	public void run() {
		System.out.println("Running printer thread");
		while(true){
			if(tc.inputLine == null){
				tc.inputLine = new InputLine();
				System.out.println("Waiting for input...");
				System.out.println(tc.inputLine.toString());
				synchronized(iThread){
					iThread.notify();
				}
			}
		}
	}
}

//Main function
public class ThreadCooperation{
	
	InputLine inputLine;
	public static void main(String[] args) {
		try {
			ThreadCooperation tc = new ThreadCooperation();
			InputThread it = new InputThread(tc);
			PrinterThread pt = new PrinterThread(tc, it);
			
		}
		catch (IOException e){
			System.out.println(e);
		}

	}

}
