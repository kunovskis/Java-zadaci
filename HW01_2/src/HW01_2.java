import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HW01_2 {

	public static void main(String[] args) throws IOException {
		FileInputStream in = null;
		FileOutputStream out = null;
		
		try{
			in = new FileInputStream("izvor.txt");
			File f = new File("destinacija.txt");
			f.createNewFile();
			out = new FileOutputStream(f);
			String s = "";
			int i = 0;
			while((i = in.read()) != -1)
				s += (char) i;
			out.write(new StringBuilder(s).reverse().toString().getBytes());
		}
		finally{
			in.close();
			out.close();
		}
	}
}
