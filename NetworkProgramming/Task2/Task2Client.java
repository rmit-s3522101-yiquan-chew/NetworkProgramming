import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Task2Client {
	private BufferedReader in;
	private PrintWriter out;
	
	public Task2Client(){
		Scanner sc = new Scanner(System.in);
//		String response = sc.nextLine();	
		out.println(sc.nextLine());
        String response;
	    try {
	         response = in.readLine();
	         if (response == null || response.equals("X")) {
	               System.exit(0);
	           }
	     } catch (Exception ex) {
	            response = "Error: " + ex;
	     }
	     System.out.println(response);
	}
	
	public void connectToServer() throws UnknownHostException, IOException{
		//getting server IP address
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Please enter the IP Address");
		String IPAddress = null;
		InetAddress ip;
		ip = InetAddress.getLocalHost();
		IPAddress = ip.getHostAddress();
		System.out.println("IPAddress = " + IPAddress);
		
		//initialize Streams
		Socket socket = new Socket(IPAddress, 9898);
		in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        
//     // Consume the initial welcoming messages from the server
//        for (int i = 0; i < 3; i++) {
//            System.out.print((in.readLine() + "\n"));
//        }
	}
	
	public static void main(String[] args) throws Exception{
		Task2Client client = new Task2Client();
		client.connectToServer();
	}
}
