import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class AssignmentTask2Client {

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
		BufferedReader dIn = null;
		PrintWriter out = null;
		
		try{
			//getting server IP address
			String IPAddress = null;
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			IPAddress = ip.getHostAddress();
			
			//connecting socket
			cSocket = new Socket(IPAddress, 9999);
			System.out.println("Socket connected");
			//register
			System.out.println("Type \"r\" to register, or \"x\" to quit.");
			dIn = new BufferedReader(new InputStreamReader (cSocket.getInputStream()));
			
			out = new PrintWriter(cSocket.getOutputStream(), true);
			
		}catch(Exception e){
			System.out.println(e);
		}
		
		try{
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			String uInput;
			
			while((uInput = stdIn.readLine()) != null){
				//generate client number
				if(uInput.equalsIgnoreCase("g")){
					int uInt = 0;
					uInt = clientGenerate();
					out.println(uInt);
				}else
					out.println(uInput);
				while(dIn != null){
					//check "X"
					if((dIn.readLine()).equalsIgnoreCase("x")){
						System.out.println("See ya");
						break;
					}
					
					//check choosen
					if((dIn.readLine()).equals("You are the choosen one!!!")){
						System.out.println("Please enter \"g\" to continue.");
					}
//					System.out.println("Echo: " + dIn.readLine());
				}				
				out.flush();
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
