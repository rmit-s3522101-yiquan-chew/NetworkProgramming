import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Week9JAR {
	public static void main(String[] args){
		System.out.println("Reading jar...");
		readJAR("jar:http://m1-c45n1.csit.rmit.edu.au/~Course/HelloWorld.jar!/");
		System.out.println("Done");
	}
	
	public static void readJAR(String jarName){
		try{
			//specifying url with jar
			URL url = new URL(jarName);
			
			//getting the jar file
			JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
			JarFile jarFile = jarConnection.getJarFile();
			
			System.out.println("JAR name : " + jarFile.getName());
			
			//iterating through jar
			Enumeration jarEntries = jarFile.entries();
			while(jarEntries.hasMoreElements()){
				JarEntry entry = (JarEntry) jarEntries.nextElement();
				System.out.println("Entry Name : " + entry.getName());
				System.out.println("Entry size : " + entry.getSize());
			}
		}
		catch (MalformedURLException e) {}
		catch (IOException e) {}
	}
}
