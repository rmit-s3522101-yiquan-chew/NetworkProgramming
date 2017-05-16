import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Week11Client {
	public static void main(String[] args){
		try {
			ByteBuffer buf = ByteBuffer.allocate(1024);
			SocketChannel socketChannel = SocketChannel.open();
			
			//Inet address for connection
			String IPAddress = null;
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			IPAddress = ip.getHostAddress();
			
			//non-blocking
			socketChannel.configureBlocking(false);
			socketChannel.connect(new InetSocketAddress(IPAddress, 9999));
			
			//if the server is blocking server, wait until finish connect
			boolean connect = socketChannel.finishConnect();
			while(!connect){
				System.out.println("Connecting...");
				connect = socketChannel.finishConnect();
			}
			
			if(connect){
				System.out.println("Successfully connect to " + IPAddress.toString() + ", port: " + 9999);
			}
			
			//scan for input and sent to serverSocketChannel
			Scanner sc = new Scanner(System.in);
			System.out.println("Start capitalizing");
			String read;
			while(connect){
				read = sc.nextLine();
				
				//write to server
				writeBuffer(socketChannel, read);
				
				//reading from server
				int bytesRead = socketChannel.read(buf);
				
				readBuffer(socketChannel, buf);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//receive buffer and print it out
	public static void readBuffer(SocketChannel socketChannel, ByteBuffer buffer){
		Charset charset = Charset.forName("ISO-8859-1");
		buffer.flip();
		String print = charset.decode(buffer).toString();
		
		if(print.equalsIgnoreCase("x")){
			System.out.println("Ending...");
			System.exit(0);
		}
		
		System.out.println(print);		
	}
	
	//write string to buffer and sent
	public static void writeBuffer(SocketChannel socketChannel, String string){
		ByteBuffer buf = ByteBuffer.allocate(1024);
		Charset charset = Charset.forName("ISO-8859-1");
		CharBuffer cb = CharBuffer.wrap(string.toCharArray());
		buf = charset.encode(cb);
		
		try {
			socketChannel.write(buf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
