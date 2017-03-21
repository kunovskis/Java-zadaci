import java.util.Date;
import java.util.*;
import java.util.Scanner;

public class ArchiveStoreTest {
	public static void main(String[] args) {
		ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
		Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
		int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
		int i;
		for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
			long days = scanner.nextLong();
			Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
					* 60 * 1000));
			LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
			store.archiveItem(lockedArchive, date);
		}
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
		for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
			int maxOpen = scanner.nextInt();
			SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
		}
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
			int open = scanner.nextInt();
			try{
            	store.openItem(open, date);
			}
			catch(NonExistingItemException e){
				e.msg();
			}
        }
		System.out.println(store.getLog());
	}
}

abstract class Archive{
	private int id;
	private Date dateArchived;
	
	public Archive(){
		this.id=0;
	}
	
	public Archive(int ID){
		this.id=ID;
	}
	
	public int Max(){
		return 0;
	}
	
	public Date getD(){
		return dateArchived;
	}
	
	public Date getDate(){
		return dateArchived;
	}
	
	public int getID(){
		return id;
	}
	
	public void plus(){
		
	}
	
	public void setDateA(Date d){
		this.dateArchived=d;
	}
	public abstract String open();
	public boolean canOpen(){
		return true;
	}
}

class LockedArchive extends Archive{
	private Date dateToOpen;
	
	public LockedArchive(int id, Date dateToOpen){
		super(id);
		this.dateToOpen=dateToOpen;
	}
	
	public Date getD(){
		return dateToOpen;
	}
	
	public String open(){
		return "L";
	}
	
}

class SpecialArchive extends Archive{
	private int maxOpen;
	private int counter;
	
	public SpecialArchive(int id, int maxOpen){
		super(id);
		this.maxOpen=maxOpen;
		counter=0;
	}
	
	public int Max(){
		return maxOpen;
	}
	
	public String open(){
		return "S";
	}
	
	public boolean canOpen(){
		return (counter<maxOpen);
	}
	
	public void plus(){
		this.counter+=1;
	}
}

class NonExistingItemException extends Exception{
	private int id;
	
	public NonExistingItemException(int i){
		id=i;
	}
	
	public void msg(){
		System.out.println("Item with id "+id+" doesn't exist");
	}
}

class ArchiveStore{
	private List<Archive> archive;
	private List<String> list;
	
	public ArchiveStore(){
		archive=new ArrayList<Archive>();
		list=new ArrayList<String>();
	}
	
	public void archiveItem(Archive item, Date date){
		item.setDateA(date);
		archive.add(item);
		list.add("Item "+item.getID()+" archived at "+date.toString());
	}
	
	public void openItem(int id, Date date) throws NonExistingItemException{
		int k=0,j=0;
			for(int i=0; i<archive.size(); i++){
				if(id==archive.get(i).getID()){
					k=1;
					j=i;
					break;
				}
			}
			if(k==0)
				throw new NonExistingItemException(id);
			else{
				if(archive.get(j).open().equals("L")){
					if(date.after(archive.get(j).getD())){
						list.add("Item "+id+" opened at "+date);
					}
					else{
						list.add("Item "+id+" cannot be opened before "+archive.get(j).getD().toString());
					}
				}
				else{
					if(archive.get(j).canOpen()){
						archive.get(j).plus();
						list.add("Item "+id+" opened at "+date);
					}
					else{
						list.add("Item "+id+" cannot be opened more than "+archive.get(j).Max()+" times");
					}
				}
			}
	}
	
	public String getLog(){
		String s="";
		for(int i=0; i<list.size(); i++)
			s+=list.get(i).replaceAll("GMT", "UTC")+"\n";
		return s;
	}
	
}


