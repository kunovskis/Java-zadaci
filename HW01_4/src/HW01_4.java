import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;

public class HW01_4 {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		File file = new File(in.readLine());
		String s = "", zbor = in.readLine();
		int counter = 0;
		FileReader IN = new FileReader(file);
		in = new BufferedReader(IN);
		while((s = in.readLine()) != null){
			String[] n = s.split("\\s");
			for(String k : n){
				if(k.toLowerCase().equals(zbor.toLowerCase()));
					counter++;
			}
		}
		System.out.println(counter);
		in.close();
	}

}
