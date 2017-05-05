import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

class AssignmentServer {
	
	//server generates random number
    private static int serverGNumber = 0;
    private static int combineNumber = 0;
    
    private static int serverGenerate(){
    	int min = 0;
    	int max = 2;
    	
    	int randomNum = ThreadLocalRandom.current().nextInt(min, max+1);
    	//refer to http://stackoverflow.com/questions/363681/how-to-generate-random-integers-within-a-specific-range-in-java
    	//quote: nextInt is normally exclusive of the top value, add 1 to make it inclusive.
    	return randomNum;
    }
    
    public static void log(String log) throws IOException{
    	DataOutputStream stream = null;
    	
    	//preparing file
		File file = new File("ServerLog.txt");
//		if(file.exists() == false)
//			file.createNewFile();
		stream = new DataOutputStream(new FileOutputStream(file));
		
		//write to file
		stream.writeChars(log + "\n");
		
		stream.close();
    }
    
    //check differents of clientGnumber and combineNumber
    public static boolean check(int clientNumber){
    	if(clientNumber > 4 || clientNumber < 0){
    		return false;
    	}
    	else if (Integer.compare(clientNumber, combineNumber) < 2){
    		return true;
    	}    	
    	return false;
    }
    
    public static void connectingClient(){
    	//initializing
    	ServerSocket sSocket = null;
    	int serverPort = 9999;
    	
    	String inputLine;
    	BufferedReader in;
    	PrintWriter out;
    	Socket cSocket = null;
    			
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
    		
    		//get and combine number from client
    		int clientGNumber = Integer.parseInt(in.readLine());
    		combineNumber = serverGNumber + clientGNumber;
    		System.out.println("serverGNumber = " + serverGNumber);
    		System.out.println("clientGNumber = " + clientGNumber);
    		System.out.println("combineNumber = " + combineNumber);
    		    				
    		while((inputLine = in.readLine()) != null){
    			int inputInt = Integer.parseInt(inputLine);
    			
    			//check
    			boolean check = check(inputInt);
    			
    			if(check == true){
    				out.println("You got it!");
    				break;
    			}
    			else{
    				out.println("Please try again. Do remember to guess between 0-4!");
    			}
    		}
    			out.close();
    			in.close();
    			cSocket.close();
    			sSocket.close();    				
    	}catch(Exception e){
    		System.out.println(e);
    	}
    }
	
	public static void main(String[] args){
		serverGNumber = serverGenerate();
		connectingClient();		
	}
}
