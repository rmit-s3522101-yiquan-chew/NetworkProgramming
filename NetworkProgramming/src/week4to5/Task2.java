package week4to5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Task2 extends Thread {
	
	private Socket socket;
	private int cNumber;
	
	public void task2Server(Socket socket, int cNumber){
		/*
		 * According to notes:
		 * - Hosts have ports, 0->65535, server listen on a port
		 * - Multiple clients can be communicating with a server on a given port,
		 * each client is assigned a separate socket
		 * - Client get port n a socket on client machine when they successfully connect with a server
		 */
		this.socket = socket;
		this.cNumber = cNumber;
	}
	
	public void run(){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			
			pw.println("Please enter a String or a single \"X\" to quit");
			
			while(true){
				String input = br.readLine();
				if(input == null || input.equals("X")){
					break;
				}
				pw.println(input.toUpperCase());
			}
		}catch(IOException ioe){
			ioe.toString();
		}finally{
			try{
				socket.close();
			}catch(IOException ioe){
				ioe.toString();
			}
		}
	}
	
	public static void task2Client(int port) throws IOException, UnknownHostException {
		BufferedReader in;
		PrintWriter out;
		
		//connect to server
		//get local host address
		Socket socket = new Socket(InetAddress.getLocalHost(), port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}
}
