import java.util.*;

enum Color {
	RED, GREEN, BLUE
}
public class ShapesTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Canvas canvas = new Canvas();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] parts = line.split(" ");
			int type = Integer.parseInt(parts[0]);
			String id = parts[1];
			if (type == 1) {
                Color color = Color.valueOf(parts[2]);
				float radius = Float.parseFloat(parts[3]);
				canvas.add(id, color, radius);
			} else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
				float width = Float.parseFloat(parts[3]);
				float height = Float.parseFloat(parts[4]);
				canvas.add(id, color, width, height);
			} else if (type == 3) {
				float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
				System.out.print(canvas);
				canvas.scale(id, scaleFactor);
				System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
				System.out.print(canvas);
			}

		}
		scanner.close();
	}
}

class Canvas {
	List<Form> forms;
	
	public Canvas(){
		forms=new ArrayList<Form>();
	}
	
	public void add(Form temp){
		if(forms.size()==0){
			forms.add(temp);
			return;
		}
		else{
			for(int i=0; i<forms.size(); i++){
				if(forms.get(i).weight()<temp.weight()){
					forms.add(i, temp);
					return;
				}
			}
		}
		forms.add(temp);
	}
	
	public void add(String id, Color c, float r){
		Form temp=new Circle(id,c,r);
		add(temp);
	}
	
	public void add(String id, Color c, float w, float h){
		Rectangle temp=new Rectangle(id,c,w,h);
		add(temp);
	}
	
	public String toString(){
		String s="";
		for(int i=0; i<forms.size(); i++)
			s+=forms.get(i).toString();
		return s;
	}
	
	public void scale(String id, float r){
		for(int i=0; i<forms.size(); i++){
			if(forms.get(i).getID().equals(id)){
				Form temp=forms.get(i);
				temp.scale(r);
				forms.remove(i);
				add(temp);
				return;
			}
		}
	}
}

class Form implements Scalable, Stackable{
	private String id;
	private Color color;
	
	public Form(String i, Color c){
		id=i;
		color=c;
	}
	
	public String getID(){
		return id;
	}
	
	public float weight(){
		return 0;
	}
	
	public void scale(float scaleFactor){
		
	}
	
	public String toString(){
		return (String.format("%-5s%-10s", id,color));
	}
}

class Circle extends Form{
	private float radius;
	
	public Circle(String id, Color c, float r){
		super(id,c);
		radius=r;
	}
	
	public float weight(){
		return (float)(radius*radius*Math.PI);
	}
	
	public void scale(float scaleFactor){
		radius*=scaleFactor;
	}
	
	public String toString(){
		return (String.format("C: %s%10.2f\n", super.toString(), weight()));
	}
}

class Rectangle extends Form{
	private float width, height;
	
	public Rectangle(String id, Color c, float w, float h){
		super(id,c);
		width=w;
		height=h;
	}
	
	public float weight(){
		return (float)(width*height);
	}
	
	public void scale(float scaleFactor){
		width*=scaleFactor;
		height*=scaleFactor;
	}
	
	public String toString(){
		return (String.format("R: %s%10.2f\n", super.toString(), weight()));
	}
}

interface Scalable{
	void scale(float scaleFactor);
}

interface Stackable{
	float weight();
}