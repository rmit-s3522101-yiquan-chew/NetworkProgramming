import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignmentTask1Client {
	//client generates random number
    private static int clientGNumber = 0;
    private static int clientGenerate(){
    	int min = 0;
    	int max = 2;
    	
    	int randomNum = ThreadLocalRandom.current().nextInt(min, max+1);
    	//refer to http://stackoverflow.com/questions/363681/how-to-generate-random-integers-within-a-specific-range-in-java
    	//quote: nextInt is normally exclusive of the top value, add 1 to make it inclusive.
    	return randomNum;
    }
    
    public static void connectingServer(){
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
			//Betting
			System.out.println("Echo now");
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			String uInput;
			
			out.println(clientGNumber);
			
			while((uInput = stdIn.readLine()) != null){
				
				//check "X"
				if(uInput.equalsIgnoreCase("x")){
					System.out.println("See ya");
					break;
				}
				
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
    
	public static void main(String[] args) throws Exception{
		clientGNumber = clientGenerate();
		connectingServer();	
	}
}
