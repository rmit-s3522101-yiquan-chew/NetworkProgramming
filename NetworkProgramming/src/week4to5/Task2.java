package week4to5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Task2 {
	public static void task2Server() throws Exception{
		/*
		 * According to notes:
		 * - Hosts have ports, 0->65535, server listen on a port
		 * - Multiple clients can be communicating with a server on a given port,
		 * each client is assigned a separate socket
		 * - Client get port n a socket on client machine when they successfully connect with a server
		 */
		
		System.out.println("Server running");
		int clientNumber = 0;
		ServerSocket listener = new ServerSocket(2017);
		Socket clientSocket = listener.accept();
		
		//reading from client
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
		
		System.out.println("Connection setup with client #" + clientNumber + "at " + clientSocket);
		out.println("Please enter a line you would like to capitalized. Enter \"X\" to end the program.");
		
		while (true){
			String input = in.readLine();
			//end condition
			if (input == null || input.equals("X")){
				break;
			}
			
			//capitalized the line
			out.println(input.toUpperCase());
			
			//write to file
			
			
			//close the socket
			clientSocket.close();
		}
		listener.close();
	}
	
	public static void task2Client() throws IOException, UnknownHostException {
		BufferedReader in;
		PrintWriter out;
		
		//connect to server
		//get local host address
		Socket socket = new Socket(InetAddress.getLocalHost(), 2017);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}
}
