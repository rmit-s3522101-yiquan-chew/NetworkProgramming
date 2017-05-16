import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class Buffers {
	public static void main(String[] argv) throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(100);
		while (fillBuffer(buffer)) {
			buffer.flip();
			drainBuffer(buffer);
			buffer.clear();
		}
	}

	private static void drainBuffer(ByteBuffer buffer) {
		Charset charset = Charset.forName("ISO-8859-1");
		while (buffer.hasRemaining()) {
			String test = (charset.decode(buffer)).toString();
			System.out.print(test.toUpperCase());
		}
		System.out.println("");
	}

	private static boolean fillBuffer(ByteBuffer buffer) {
		if (index >= strings.length) {
			return (false);
		}
		String string = strings[index++];
		for (int i = 0; i < string.length(); i++) {
			buffer.put((byte) string.charAt(i));
		}
		return (true);
	}

	private static int index = 0;
	private static String[] strings = { "String 1", "String 2", "String 3", };
}