import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

abstract class Contact{
	private String date;

	public Contact(String date) {
		this.date = date;
	}
	
	public boolean isNewerThan(Contact c){
		String niza1[]=this.date.split("-");
		String niza2[]=c.date.split("-");
		int c1[]=new int[3];
		int c2[]=new int [3];
		for(int i=0; i<3; i++){
			c1[i]=Integer.parseInt(niza1[i]);
			c2[i]=Integer.parseInt(niza2[i]);
			if(c1[i]>c2[i])
				return true;
			else if(c1[i]<c2[i])
				return false;
		}
		return false;
	}
	
	public String getPhone(){
		return "";
	}
	
	public String getEmail(){
		return"";
	}
	
	public abstract String getType();
}

class EmailContact extends Contact{
	private String email;
	
	public EmailContact(String date, String email){
		super(date);
		this.email=email;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getType(){
		return "Email";
	}
}

class PhoneContact extends Contact{
	enum Operator{
		VIP, ONE, TMOBILE
	}
	private Operator operator;
	private String phone;
	
	public PhoneContact(String date, String phone){
		super(date);
		this.phone=phone;
		String f[]=phone.split("/");
		int k=Integer.parseInt(f[0]);
		if(k==70 || k==71 || k==72)
			this.operator=Operator.TMOBILE;
		else if(k==75 || k==76)
			this.operator=Operator.ONE;
		else
			this.operator=Operator.VIP;
	}
	
	public String getPhone(){
		return this.phone;
	}
	
	public Operator getOperator(){
		return this.operator;
	}
	
	public String getType(){
		return "Phone";
	}
}

class Student{
	private String firstName;
	private String lastName;
	private String city;
	private int age;
	private long index;
	private Contact[] contact=new Contact[0];
	
	public Student(String firstName, String lastName, 
			String city, int age, long index){
		this.firstName=firstName;
		this.lastName=lastName;
		this.city=city;
		this.age=age;
		this.index=index;
	}
	
	public void addEmailContact(String date, String email){
		Contact temp[]= new Contact[contact.length+1];
		for(int i=0; i<contact.length; i++)
			temp[i]=contact[i];
		EmailContact f=new EmailContact(date, email);
		temp[contact.length]=f;
		contact=temp;
	}
	
	public void addPhoneContact(String date, String phone){
		Contact temp[]= new Contact[contact.length+1];
		for(int i=0; i<contact.length; i++)
			temp[i]=contact[i];
		PhoneContact f=new PhoneContact(date, phone);
		temp[contact.length]=f;
		contact=temp;
	}
	
	public Contact[] getEmailContacts(){
		Contact temp[]=new Contact[contact.length];
		int k=0;
		for(int i=0; i<contact.length; i++){
			if(contact[i].getType()=="Email"){
				temp[k]=contact[i];
				k++;
			}
		}
		Contact t[]=new Contact[k];
		for(int i=0; i<k; i++){
			t[i]=temp[i];
		}
		return t;
	}
	
	public Contact[] getPhoneContacts(){
		Contact temp[]=new Contact[contact.length];
		int k=0;
		for(int i=0; i<contact.length; i++){
			if(contact[i].getType()=="Phone"){
				temp[k]=contact[i];
				k++;
			}
		}
		Contact t[]=new Contact[k];
		for(int i=0; i<k; i++){
			t[i]=temp[i];
		}
		return t;
	}
	
	public String getCity(){
		return this.city;
	}
	
	public String getFullName(){
		return this.firstName+" "+this.lastName;
	}
	
	public long getIndex(){
		return this.index;
	}
	
	public Contact getLatestContact(){
		Contact latest=contact[0];
		for(int i=1; i<contact.length; i++){
			if(contact[i].isNewerThan(latest))
				latest=contact[i];
		}
		return latest;
	}

	@Override
	public String toString() {
		String s="{\"ime\":\"" + this.firstName + "\", \"prezime\":\"" + this.lastName+"\", \"vozrast\":"+this.age + ", \"grad\":\"" + this.city +
				"\", \"indeks\":" + this.index + ", \"telefonskiKontakti\":[";
		Contact temp[]=this.getPhoneContacts();
		if(temp.length>=1){
			for(int i=0; i<temp.length-1; i++){
				s+="\""+temp[i].getPhone()+"\", ";
			}
			s+="\""+temp[temp.length-1].getPhone()+"\"";
		}
		s+="], \"emailKontakti\":[";
		Contact []t=this.getEmailContacts();
		if(t.length>=1){
			for(int i=0; i<t.length-1; i++){
				s+="\""+t[i].getEmail()+"\", ";
			}
			s+="\""+t[t.length-1].getEmail()+"\"";
		}
		s+="]}";
		return s;
	}
}

class Faculty{
	private String name;
	private Student[] students;
	
	public Faculty(String name, Student[] students){
		this.name=name;
		this.students=new Student[students.length];
		for(int i=0; i<students.length; i++)
			this.students[i]=students[i];
	}
	
	public int countStudentsFromCity(String cityName){
		int s=0;
		for(int i=0; i<students.length; i++){
			if(cityName.equals(students[i].getCity()))
				++s;
		}
		return s;
	}
	
	public Student getStudent(long index){
		Student f=students[0];
		for(int i=1 ;i<students.length; i++)
		{
			if(index==students[i].getIndex())
				f=students[i];
		}
		return f;
	}
	
	public double getAverageNumberOfContacts(){
		int k=0;
		for(int i=0; i<students.length; i++){
			Contact temp[]=students[i].getEmailContacts();			
			Contact t[]=students[i].getPhoneContacts();
			k+=temp.length+t.length;
		}
		return k*1.0/students.length;
	}
	
	public Student getStudentWithMostContacts(){
		int k=students[0].getEmailContacts().length+students[0].getPhoneContacts().length;
		Student s=students[0];
		for(int i=1; i<students.length; i++)
		{
			int f=students[i].getEmailContacts().length+students[i].getPhoneContacts().length;
			if(f>k){
				k=f;
				s=students[i];
			}
			else if(f==k){
				if(students[i].getIndex()>s.getIndex())
				{
					k=f;
					s=students[i];
				}
			}
		}
		return s;
	}
	
	@Override
	public String toString(){
		String s="{\"fakultet\":\""+this.name+"\", \"studenti\":[";
		for(int i=0; i<students.length-1; i++)
			s+=students[i].toString()+", ";
		s+=students[students.length-1]+"]}";
		return s;
	}
	
}


public class ContactsTester {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int tests = scanner.nextInt();
		Faculty faculty = null;

		int rvalue = 0;
		long rindex = -1;

		DecimalFormat df = new DecimalFormat("0.00");

		for (int t = 0; t < tests; t++) {

			rvalue++;
			String operation = scanner.next();

			switch (operation) {
			case "CREATE_FACULTY": {
				String name = scanner.nextLine().trim();
				int N = scanner.nextInt();

				Student[] students = new Student[N];

				for (int i = 0; i < N; i++) {
					rvalue++;

					String firstName = scanner.next();
					String lastName = scanner.next();
					String city = scanner.next();
					int age = scanner.nextInt();
					long index = scanner.nextLong();

					if ((rindex == -1) || (rvalue % 13 == 0))
						rindex = index;

					Student student = new Student(firstName, lastName, city,
							age, index);
					students[i] = student;
				}

				faculty = new Faculty(name, students);
				break;
			}

			case "ADD_EMAIL_CONTACT": {
				long index = scanner.nextInt();
				String date = scanner.next();
				String email = scanner.next();

				rvalue++;

				if ((rindex == -1) || (rvalue % 3 == 0))
					rindex = index;

				faculty.getStudent(index).addEmailContact(date, email);
				break;
			}

			case "ADD_PHONE_CONTACT": {
				long index = scanner.nextInt();
				String date = scanner.next();
				String phone = scanner.next();

				rvalue++;

				if ((rindex == -1) || (rvalue % 3 == 0))
					rindex = index;

				faculty.getStudent(index).addPhoneContact(date, phone);
				break;
			}

			case "CHECK_SIMPLE": {
				System.out.println("Average number of contacts: "
						+ df.format(faculty.getAverageNumberOfContacts()));

				rvalue++;

				String city = faculty.getStudent(rindex).getCity();
				System.out.println("Number of students from " + city + ": "
						+ faculty.countStudentsFromCity(city));

				break;
			}

			case "CHECK_DATES": {

				rvalue++;

				System.out.print("Latest contact: ");
				Contact latestContact = faculty.getStudent(rindex)
						.getLatestContact();
				if (latestContact.getType().equals("Email"))
					System.out.println(((EmailContact) latestContact)
							.getEmail());
				if (latestContact.getType().equals("Phone"))
					System.out.println(((PhoneContact) latestContact)
							.getPhone()
							+ " ("
							+ ((PhoneContact) latestContact).getOperator()
									.toString() + ")");

				if (faculty.getStudent(rindex).getEmailContacts().length > 0
						&& faculty.getStudent(rindex).getPhoneContacts().length > 0) {
					System.out.print("Number of email and phone contacts: ");
					System.out
							.println(faculty.getStudent(rindex)
									.getEmailContacts().length
									+ " "
									+ faculty.getStudent(rindex)
											.getPhoneContacts().length);

					System.out.print("Comparing dates: ");
					int posEmail = rvalue
							% faculty.getStudent(rindex).getEmailContacts().length;
					int posPhone = rvalue
							% faculty.getStudent(rindex).getPhoneContacts().length;

					System.out.println(faculty.getStudent(rindex)
							.getEmailContacts()[posEmail].isNewerThan(faculty
							.getStudent(rindex).getPhoneContacts()[posPhone]));
				}

				break;
			}

			case "PRINT_FACULTY_METHODS": {
				System.out.println("Faculty: " + faculty.toString());
				System.out.println("Student with most contacts: "
						+ faculty.getStudentWithMostContacts().toString());
				break;
			}

			}

		}

		scanner.close();
	}
}
