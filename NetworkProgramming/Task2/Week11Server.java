import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;

public class Week11Server {
	public static void main(String[] args){
		blockingServerSocketChannel();
	}
	
	public static void blockingServerSocketChannel(){
		try {
			//Inet address for connection
			String IPAddress = null;
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			IPAddress = ip.getHostAddress();
			
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			serverChannel.socket().bind(new InetSocketAddress(IPAddress, 9999));
			SocketChannel socketChannel = serverChannel.accept();
			System.out.println(socketChannel.toString());
			ByteBuffer buf = ByteBuffer.allocate(1024);
//			int bytesRead = socketChannel.read(buf);
			
			boolean check;
			while(check = socketChannel.isConnected()){
				
				int bytesRead = socketChannel.read(buf);
				if(bytesRead == -1){
					socketChannel.close();
				}else{
					ByteBuffer cBuf = ByteBuffer.allocate(1024);
					
					buf.flip();
					cBuf = capitalizeBuffer(buf);
					
					//send back to channel
					socketChannel.write(cBuf);
					buf.clear();
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//buffer draining, capitalization should be done here
	private static ByteBuffer capitalizeBuffer(ByteBuffer buffer){
		CharBuffer cb;
		ByteBuffer cBuf = ByteBuffer.allocate(1024);
		Charset charset = Charset.forName("ISO-8859-1");
		while(buffer.hasRemaining()){
			String capitalize = (charset.decode(buffer)).toString();
			
			cb = CharBuffer.wrap((capitalize.toUpperCase()).toCharArray());
			cBuf = charset.encode(cb);
		}
		return cBuf;
	}
}
