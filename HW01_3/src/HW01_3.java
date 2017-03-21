import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HW01_3 {

	public static void main(String[] args) throws IOException {
		BufferedReader in = null;
		BufferedWriter out = null;
		
		try{
			FileReader IN = new FileReader("izvor.txt");
			in = new BufferedReader(IN);
			File f = new File("destinacija.txt");
			f.createNewFile();
			FileWriter OUT = new FileWriter(f);
			out = new BufferedWriter(OUT);
			String s = "", q = "";
			while((s = in.readLine()) != null)
				q += s + "\n";
			out.write(new StringBuilder(q).reverse().toString());
		}
		finally{
			in.close();
			out.close();
		}
	}
}