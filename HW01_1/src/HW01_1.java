import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;

public class HW01_1 {
	public static void main(String[] args) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		double avg = 0;
		File f = new File(in.readLine());
		File[] list = f.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File f, String s) {
				return s.endsWith(".txt");
			}
		});
		for(File q: list){
			avg+=q.length();
		}
		System.out.println(avg/list.length);
	}
}
