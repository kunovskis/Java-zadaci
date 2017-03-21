import java.util.*;
import java.util.concurrent.TimeUnit;

public class FrontPageTest {
	public static void main(String[] args) {
        // Reading
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		String[] parts = line.split(" ");
		Category[] categories = new Category[parts.length];
		for (int i = 0; i < categories.length; ++i) {
			categories[i] = new Category(parts[i]);
		}
		int n = scanner.nextInt();
		scanner.nextLine();
		FrontPage frontPage = new FrontPage(categories);
		Calendar cal = Calendar.getInstance();
		for (int i = 0; i < n; ++i) {
			String title = scanner.nextLine();
			cal = Calendar.getInstance();
            int min = scanner.nextInt();
			cal.add(Calendar.MINUTE, -min);
			Date date = cal.getTime();
			scanner.nextLine();
			String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
			TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
			frontPage.addNewsItem(tni);
		}
        
		n = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < n; ++i) {
			String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -min);
			scanner.nextLine();
			Date date = cal.getTime();
			String url = scanner.nextLine();
			int views = scanner.nextInt();
			scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
			MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
			frontPage.addNewsItem(mni);
		}
        // Execution
        String category = scanner.nextLine();
		System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
        	System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
	}
}

class Category{
	String category;
	
	public Category(String s){
		category=s;
	}
	
	public String getC(){
		return category;
	}
	
	public boolean equals(Category c){
		return (category.equals(c.category));
	}
	
	public String toString(){
		return category;
	}
}


class NewsItem{
	String title;
	Date date;
	Category category;
	
	public NewsItem(String t, Date d, Category c){
		title=t;
		date=d;
		category=c;
	}
	
	public Category getCategory(){
		return category;
	}
	
	public String getTeaser(){
		Date now=new Date();
		long temp = now.getTime() - date.getTime();
		long minutes = TimeUnit.MILLISECONDS.toMinutes(temp);
		return (title +"\n"+minutes+"\n");
	}
	
}

class TextNewsItem extends NewsItem{
	String text;
	
	public TextNewsItem(String t, Date d, Category c, String te){
		super(t,d,c);
		text=te;
	}
	
	public String getTeaser(){
		String s=super.getTeaser();
		if(text.length()>80)
			s+=text.substring(0, 80)+"\n";
		else
			s+=text+"\n";
		return s;
	}
}

class MediaNewsItem extends NewsItem{
	String url;
	int views;
	
	public MediaNewsItem(String t, Date d, Category c, String u, int v){
		super(t,d,c);
		url=u;
		views=v;
	}
	
	public String getTeaser(){
		return (super.getTeaser()+url+"\n"+views+"\n");
	}
	
}

class FrontPage{
	List<NewsItem> news;
	Category[] categories;
	
	public FrontPage(Category[] c){
		categories=new Category[c.length];
		for(int i=0; i<c.length; i++)
			categories[i]=c[i];
		news=new ArrayList<NewsItem>();
	}
	
	public void addNewsItem(NewsItem newsItem){
		news.add(newsItem);
	}
	
	public List<NewsItem> listByCategory(Category category){
		List<NewsItem> temp=new ArrayList<NewsItem>();
		for(int i=0; i<news.size(); i++){
			if(news.get(i).getCategory().equals(category))
				temp.add(news.get(i));
		}
		return temp;
	}
	
	public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException{
		List<NewsItem> temp=new ArrayList<NewsItem>();
		int k=0;
		for(int i=0; i<news.size(); i++){
			if(news.get(i).getCategory().getC().equals(category)){
				k++;
				temp.add(news.get(i));
			}
		}
		for(int i=0; i<categories.length; i++){
			if(categories[i].getC().equals(category))
				k++;
		}
		if(k==0)
			throw new CategoryNotFoundException(category);
		else
			return temp;
	}
	
	public String toString(){
		String s="";
		for(int i=0; i<news.size(); i++){
			s+=news.get(i).getTeaser();
		}
		return s;
	}
}

class CategoryNotFoundException extends Exception{
	String s;
	
	public CategoryNotFoundException(String s){
		this.s=s;
	}
	
	public String getMessage(){
		return ("Category "+s+" was not found");
	}
}

