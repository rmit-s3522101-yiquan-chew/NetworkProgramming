import java.io.*;
import java.net.*;

class Task2Server {

	public static void main(String[] args){
		
		//initializing
		ServerSocket sSocket = null;
		int serverPort = 9999;
		
		String inputLine;
		BufferedReader in;
		PrintWriter out;
		Socket cSocket = null;
		
		DataOutputStream stream = null;
		
		//creating ServerSocket
		try{
			sSocket = new ServerSocket(serverPort);
		}catch(IOException ioe){
			System.out.println(ioe);
		}
		
		try{
			//client Socket
			cSocket = sSocket.accept();
			in = new BufferedReader(new InputStreamReader (cSocket.getInputStream()));
			out = new PrintWriter(cSocket.getOutputStream(), true);
			
			//preparing file
			File file = new File("Capitalization.txt");
			file.createNewFile();			
			stream = new DataOutputStream(new FileOutputStream(file));
			
			while((inputLine = in.readLine()) != null){
				
				//check "X"
				if(inputLine.equals("X")){
					out.println("See ya");
					break;
				}
				
				//toUpperCase				
				inputLine = inputLine.toUpperCase();
				
				//write to file
				stream.writeChars(inputLine + "\n");
				
				System.out.println("Server: " + inputLine);
				out.println(inputLine);				
			}
			out.close();
			in.close();
			cSocket.close();
			sSocket.close();
			stream.close();
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
