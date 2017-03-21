import java.util.Scanner;

interface Item{
	public String getType();
	public int getPrice();
}

class InvalidExtraTypeException extends Exception{
	public void msg(){
		System.out.println("InvalidExtraTypeException");
	}
}

class InvalidPizzaTypeException extends Exception{
	public void msg(){
		System.out.println("InvalidPizzaTypeException");
	}
}

class ItemOutOfStockException extends Exception{
	public void msg(Item item){
		System.out.println("ItemOutOfStockException"+item.getType());
	}
}

class OrderLockedException extends Exception{
	public void msg(){
		System.out.println("OrderLockedException");
	}
}

class EmptyOrderException extends Exception{
	public void msg(){
		System.out.println("EmptyOrder");
	}
}

class ExtraItem implements Item{
	private String type;
	
	public ExtraItem(String type){
		try{
			if(type.equals("Coke") || type.equals("Ketchup"))
				this.type=type;
			else{
				throw new InvalidExtraTypeException();
			}
		}
		catch(InvalidExtraTypeException e){
			e.msg();
			System.exit(0);
		}
	}
	
	public String getType(){
		return this.type;
	}
	
	public int getPrice(){
		if(this.type.equals("Ketchup"))
			return 3;
		else if(this.type.equals("Coke"))
			return 5;
		else
			return 0;
	}
}

class PizzaItem implements Item{
	private String type;
	
	public PizzaItem(String type){
		try{
			if(type.equals("Standard") || type.equals("Pepperoni") || type.equals("Vegetarian"))
				this.type=type;
			else{
				throw new InvalidPizzaTypeException();
			}
		}
		catch(InvalidPizzaTypeException e){
			e.msg();
			System.exit(0);
		}
	}
	
	public String getType(){
		return this.type;
	}
	
	public int getPrice(){
		if(this.type.equals("Standard"))
			return 10;
		else if(this.type.equals("Pepperoni"))
			return 12;
		else if(this.type.equals("Vegetarian"))
			return 8;
		else return 0;
	}
}

class Order{
	private Item[] item;
	private boolean lock=false;
	
	public Order(){
		this.item=new Item[0];
	}
	
	public void addItem(Item item, int count){
		try{
			if(lock){
				throw new OrderLockedException();
			}
			else if(count>10)
				throw new ItemOutOfStockException();
			else{
				int k=0,j=0;
				for(int i=0; i<this.item.length; i++)
					if(this.item[i].getType().equals(item.getType()))
						k++;
				if(k==0){
					Item []temp=new Item[this.item.length+count];
					for(int i=0; i<this.item.length; i++){
						if(!this.item[i].getType().equals(item.getType())){
							temp[j]=this.item[i];
							j++;
						}
					}
					for(int i=j; i<temp.length; i++){
						temp[i]=item;
					}
					this.item=temp;
				}
				else{
					Item [] temp=new Item[this.item.length+count-k];
					j=0;
					for(int i=0; i<this.item.length; i++){
						if(item.getType().equals(this.item[i].getType())){
							if(count>0){
								temp[j]=item;
								j++;
								count--;
							}
							else
								continue;
						}	
						else{
							temp[j]=this.item[i];
							j++;
						}
					}
					this.item=temp;
				}
			}
		}
		catch(ItemOutOfStockException e){
			e.msg(item);
		}
		catch(OrderLockedException e){
			e.msg();
		}
	}
	
	public int getPrice(){
		int s=0;
		for(int i=0; i<this.item.length; i++)
			s+=this.item[i].getPrice();
		return s;
	}
	
	public void removeItem(int idx){
		try{
			if(this.lock){
				throw new OrderLockedException();
			}
			int f=0;
			for(int i=0; i<this.item.length-1; i++)
				if(!item[i].getType().equals(item[i+1].getType()))
					f++;
			if(idx>f)
				throw new ArrayIndexOutOfBoundsException(idx);
			else{
				f=0;
				Item q=this.item[0];
				for(int i=0; i<this.item.length-1; i++){
					if(!item[i].getType().equals(item[i+1].getType())){
						f++;
						if(f==idx)
							q=this.item[i+1];
					}
				}
				int j=0,k=0;
				for(int i=0; i<item.length; i++)
					if(item[i].getType().equals(q.getType()))
						k++;
				Item[] temp=new Item[item.length-k];
				for(int i=0; i<item.length; i++)
				{
					if(item[i].getType().equals(q.getType()))
						continue;
					temp[j]=item[i];
					j++;
				}
				this.item=temp;
			}
		}
		catch(OrderLockedException e){
			e.msg();
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.getMessage();
		}
	}

	public void displayOrder(){
		int k=1,f=1;
		for(int i=0; i<this.item.length-1; i++)
		{
			if(item[i].getType().equals(item[i+1].getType())){
				k++;
			}
			else{
				System.out.format("%3d.%-15sx%2d%5d$\n",f,item[i].getType(),k,k*item[i].getPrice());
				k=1;
				f++;
			}
		}
		System.out.format("%3d.%-15sx%2d%5d$\n",f,item[item.length-1].getType(),k,k*item[item.length-1].getPrice());
		System.out.format("%-22s%5d$\n","Total:",this.getPrice());
	}
	
	public void lock(){
		try{
			if(this.item.length==0)
				throw new EmptyOrderException();
			this.lock=true;
			}
		catch(EmptyOrderException e){
			e.msg();
		}
	}
}




public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}