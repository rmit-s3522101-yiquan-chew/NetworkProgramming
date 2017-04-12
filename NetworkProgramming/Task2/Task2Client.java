import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Task2Client {

	public static void main(String[] args) throws Exception{
		Socket cSocket = null;
		DataOutputStream dOut = null;
		BufferedReader dIn = null;
		PrintWriter out = null;
		String response;
		
		try{
			//getting server IP address
			String IPAddress = null;
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			IPAddress = ip.getHostAddress();
			
			//connecting socket
			cSocket = new Socket(IPAddress.toString(), 9999);
			System.out.println("socket connected");
			dIn = new BufferedReader(new InputStreamReader (cSocket.getInputStream()));
			
			out = new PrintWriter(cSocket.getOutputStream(), true);
			
		}catch(Exception e){
			System.out.println(e);
		}
		
		try{
			//Capitalization
			System.out.println("Echo now");
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			String uInput;
			while((uInput = stdIn.readLine()) != null){
				out.println(uInput);
				System.out.println("Echo: " + dIn.readLine());
			}
							
			//Closing the socket and stream
			dIn.close();
			stdIn.close();
			out.close();
			cSocket.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
}
