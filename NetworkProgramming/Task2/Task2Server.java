import java.io.*;
import java.net.*;

public class Task2Server {
	public static void main (String[] args) throws Exception{
		System.out.println("The Server is running");
		ServerSocket listener = new ServerSocket(9898);
		try{
			while(true){
				new Capitalizer(listener.accept()).start();
			}			
		}finally{
			listener.close();
		}
	}
	
	/*
	 * private thread to handle capitalization request on a particular socket.
	 * client terminates by sending a single "X"
	 */
	private static class Capitalizer extends Thread{
		private Socket socket;
		
		public Capitalizer(Socket socket){
			this.socket = socket;
			System.out.println("New connection at socket(" + socket + ")");
		}
		
		public void run() {
			try{
				//ensure output is flushed after every newline.
				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				
				//file writer
				File file = new File("task2ServerOutput.txt");
				file.createNewFile();
				DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));
				
				//main function start
//				out.println("Hello World");
//				out.println("Please enter a line or \"X\" to quit");
				
				//get line from client, write to file, return capitalized
				while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals("X")) {
                        break;
                    }
                    out.println(input.toUpperCase());
                    stream.writeChars(input.toUpperCase());
                }
				stream.close();
				
			}catch (IOException ioe){
				ioe.getMessage();
			}finally {
				try {
					socket.close();
				}catch(IOException ioe){
					ioe.getMessage();
				}
			}
		}

		//log to file
	}
}
