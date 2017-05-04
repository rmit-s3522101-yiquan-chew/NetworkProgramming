import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

public class Week9URL {
	//reader
    public static void main(String[] args) throws Exception {
        System.out.println("Starting");
        String link = "http://m1-c45n1.csit.rmit.edu.au/~Course/index.php";
        readURL(link);
        System.out.println("Done");
    }
    
    //reading url
    public static void readURL(String link){
    	URL url = null;
    	try{
    		//create url object from string
    		url = new URL(link);
    	} catch (MalformedURLException e) {}
    	try{
    		//Create an input stream
    		URLConnection urlConnection = url.openConnection();
    		InputStream input = urlConnection.getInputStream();
    		
    		//map for storing headers
    		Map<String, List<String>>headers = urlConnection.getHeaderFields();
    		
    		//print the first 8 header fields
    		int i=0;
    		for(Map.Entry<String, List<String>> header:headers.entrySet()){
    			System.out.println("Title : " + header.getKey() + ", Value : " + header.getValue());
    			i++;
    			if(i == 8)
    				break;
    		}
    		
    		//printing all content
    		int data = input.read();
    		while (data != -1){
    			System.out.print((char) data);
    			data = input.read();
    		}
    		input.close();
    	} catch (IOException e) {}
    }
}