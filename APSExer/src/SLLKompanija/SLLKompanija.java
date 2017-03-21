package SLLKompanija;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



class SLLNode {
	protected int id;
	protected int plata;
	protected SLLNode succ;

	public SLLNode(int id,int plata, SLLNode succ) {
		this.id = id;
		this.plata=plata;
		this.succ = succ;
	}

	
}

class SLL {
	private SLLNode first;

	public SLL() {
		// Construct an empty SLL
		this.first = null;
	}

	public void deleteList() {
		first = null;
	}

	public int length() {
		int ret;
		if (first != null) {
			SLLNode tmp = first;
			ret = 1;
			while (tmp.succ != null) {
				tmp = tmp.succ;
				ret++;
			}
			return ret;
		} else
			return 0;

	}


	public void insertFirst(int id, int plata) {
		SLLNode ins = new SLLNode(id,plata, first);
		first = ins;
	}

	public void insertLast(int id,int plata) {
		if (first != null) {
			SLLNode tmp = first;
			while (tmp.succ != null)
				tmp = tmp.succ;
			SLLNode ins = new SLLNode(id, plata, null);
			tmp.succ = ins;
		} else {
			insertFirst(id,plata);
		}
	}
	public void insertAfter(int id, int plata, SLLNode after) {
		if (after != null) {
			SLLNode ins = new SLLNode(id, plata , after.succ);
			after.succ = ins;
		} else {
			System.out.println("Dadenot jazol e null");
		}
	}
	public SLLNode getFirst() {
		return first;
	}
	
	
	public SLL brisi_pomali_od(int iznos) {
		SLL ret = new SLL();
		
		SLLNode curr = first;
		
		while(curr != null){
			if (curr.plata >= iznos)
				ret.insertLast(curr.id, curr.plata);
			curr = curr.succ;
		}
		
		return ret;
	}
   
	public SLL sortiraj_opagacki() {
		
		SLL ret = new SLL();
		SLLNode curr = first;
		
		while(curr != null){
			
			if ( ret.first == null || curr.id > ret.first.id ){
				ret.insertFirst(curr.id, curr.plata);
			}else{				
				SLLNode currInner = ret.first;
				boolean inserted = false;
				
				while(currInner.succ != null){
					if(curr.id > currInner.succ.id){
						ret.insertAfter(curr.id, curr.plata, currInner);
						inserted = true;
						break;
					}
					currInner = currInner.succ;
				}
				if(!inserted){
					ret.insertLast(curr.id, curr.plata);
				}
				
			}
			curr = curr.succ;
		}
		return ret;
    	
	}
    public void pecati (SLL lista)
    {
    	SLLNode p=lista.first;
    	while(p!=null)
    	{
    		System.out.println(p.id+","+p.plata);
    		p=p.succ;
    	}
    }
	
}
public class SLLKompanija {
	public static void main(String[] args) throws IOException {

		SLL lista1 = new SLL();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String s = stdin.readLine();
		int N = Integer.parseInt(s);
		
		for (int i = 0; i < N; i++) {
			s=stdin.readLine();
			String s1=stdin.readLine();
			lista1.insertLast(Integer.parseInt(s),Integer.parseInt(s1));
		}
		s = stdin.readLine();
		
		lista1=lista1.brisi_pomali_od(Integer.parseInt(s));
		if(lista1!=null)
        {
		    lista1=lista1.sortiraj_opagacki();
		    lista1.pecati(lista1);
        }
		
	}
}
