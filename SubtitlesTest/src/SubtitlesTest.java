import java.io.*;
import java.util.*;

public class SubtitlesTest {
	public static void main(String[] args) {
		Subtitles subtitles = new Subtitles();
		int n = subtitles.loadSubtitles(System.in);
		System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
		subtitles.print();
		int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
		System.out.println(String.format("SHIFT FOR %d ms", shift));
		subtitles.shift(shift);
		System.out.println("+++++ SHIFTED SUBTITLES +++++");
		subtitles.print();
	}
}

// Вашиот код овде

class Subtitles{

	class Time{
		int h;
		int m;
		int s;
		int ms;
		
		public Time(int h, int m, int s, int ms){
			this.h=h;
			this.m=m;
			this.s=s;
			this.ms=ms;
		}
		
		public void add(int a){
			ms+=a;
			if(ms<0){
				ms+=1000;
				s--;
			}
			s+=ms/1000;
			ms=ms%1000;
			if(s<0){
				s+=60;
				m--;
			}
			m+=s/60;
			s=s%60;
			h+=m/60;
			m=m%60;
		}
		
		public String toString(){
			return String.format("%02d:%02d:%02d,%03d", h,m,s,ms);
		}
	}
	
	class Subtitle{
		
		int red;
		Time start;
		Time finish;
		String text;
		
		public Subtitle(int r, Time s, Time f, String t){
			text=t;
			finish=f;
			start=s;
			red=r;
		}		
		
		public String toString(){
			String s="";
			s+=red;
			s+="\n";
			s+=start.toString();
			s+=" --> ";
			s+=finish.toString();
			s+="\n";
			s+=text;
			s+="\n";
			return s;
		}
	}
	
	ArrayList<Subtitle> subtitles;
	
	public Subtitles(){
		subtitles=new ArrayList<Subtitle> ();
	}
	
	public int loadSubtitles(InputStream inputStream){
		Scanner sc=new Scanner(inputStream);
		int b=0;
		while(sc.hasNextLine()){
			String t="";
			String s=sc.nextLine();
			int r=Integer.parseInt(s);
			String []sn=sc.nextLine().split(" ");
			String []p=sn[0].split("(:)|(,)");
			String []k=sn[2].split("(:)|(,)");
			String line=null;
			while(sc.hasNextLine()){
				line=sc.nextLine();
				if(line.isEmpty())
					break;
				t+=line+"\n";
			}
			subtitles.add(new Subtitle(r, new Time(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]), Integer.parseInt(p[3])), new Time(Integer.parseInt(k[0]), Integer.parseInt(k[1]), Integer.parseInt(k[2]), Integer.parseInt(k[3])), t));
			b++;
		}
		return b;
	}
	
	public void print(){
		for(Subtitle sub:subtitles)
			System.out.print(sub.toString());
	}
	
	public void shift(int ms){
		for(Subtitle sub : subtitles){
			sub.finish.add(ms);
			sub.start.add(ms);
		}
	}
	
}


