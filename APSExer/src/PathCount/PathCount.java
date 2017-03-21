package PathCount;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

class Graph<E> {

	int num_nodes; // broj na jazli
	E nodes[]; // informacija vo jazlite - moze i ne mora?
	int adjMat[][]; // matrica na sosednost

	@SuppressWarnings("unchecked")
	public Graph(int num_nodes) {
		this.num_nodes = num_nodes;
		nodes = (E[]) new Object[num_nodes];
		adjMat = new int[num_nodes][num_nodes];

		for (int i = 0; i < this.num_nodes; i++)
			for (int j = 0; j < this.num_nodes; j++)
				adjMat[i][j] = 0;
	}

	int adjacent(int x, int y) { // proveruva dali ima vrska od jazelot so
									// indeks x do jazelot so indeks y
		return (adjMat[x][y] != 0) ? 1 : 0;
	}

	void addEdge(int x, int y) { // dodava vrska megu jazlite so indeksi x i y
		adjMat[x][y] = 1;
		adjMat[y][x] = 1;
	}

	void deleteEdge(int x, int y) {
		// ja brise vrskata megu jazlite so indeksi x i y
		adjMat[x][y] = 0;
		adjMat[y][x] = 0;
	}

	E get_node_value(int x) { // ja vraka informacijata vo jazelot so indeks x
		return nodes[x];
	}

	void set_node_value(int x, E a) { // ja postavuva informacijata vo jazelot
										// so indeks na a
		nodes[x] = a;
	}
	
	public int [] bfs(int node){
		
		boolean visited[] = new boolean[num_nodes];
		int distance[] = new int[num_nodes];
		for (int i = 0; i < num_nodes; i++){
			visited[i] = false;
			distance[i] = 0;
		}
		visited[node] = true;
		distance[node] = 0;
		System.out.println(node+": " + nodes[node]);
		Queue<Integer> q = new LinkedQueue<Integer>();
		q.enqueue(node);
		
		int pom;
		
		while(!q.isEmpty()){
			pom = q.dequeue();
			for (int i = 0; i < num_nodes; i++) {
				if(adjacent(pom, i)==1){
					if (!visited[i]){
						visited[i] = true;
						distance[i] = distance[pom] + 1;
						System.out.println(i+": " + nodes[i]);
						q.enqueue(i);
					}
					
				}
			}
			
			
		}
		
		for(int i = 0; i < num_nodes; i++){
			System.out.println(distance[i]);
		}
		return distance;
	}

	@Override
	public String toString() {
		String ret = "  ";
		for (int i = 0; i < num_nodes; i++)
			ret += nodes[i] + " ";
		ret += "\n";
		for (int i = 0; i < num_nodes; i++) {
			ret += nodes[i] + " ";
			for (int j = 0; j < num_nodes; j++)
				ret += adjMat[i][j] + " ";
			ret += "\n";
		}
		return ret;
	}

	public void printMatrix() {
		for (int i = 0; i < num_nodes; i++) {
			for (int j = 0; j < num_nodes; j++)
				System.out.print(adjMat[i][j] + " ");
			System.out.println();
		}
	}
	
	public int pathCount(int nodeIndex,int pathLength){
		int total = 0;
		for(int i = 0; i < num_nodes; i++){
			if( adjMat[nodeIndex][i] == 1){
				if(pathLength == 1)
					total++;
				else
					total+= pathCount( i, pathLength -1 );
			}
		}
		return total;
	}

}

public class PathCount {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Graph<Integer> g=new Graph<>(N);
		int M = Integer.parseInt(br.readLine());
		for (int i=0;i<M;i++){
			String line=br.readLine();
			String edgeLine[]=line.split(" ");
			g.addEdge(Integer.parseInt(edgeLine[0]), Integer.parseInt(edgeLine[1]));
		}
		int nodeIndex=Integer.parseInt(br.readLine());
		int pathLength=Integer.parseInt(br.readLine());
		System.out.println(g.pathCount(nodeIndex, pathLength));
		br.close();

	}

}

interface Queue<E> {

	// Elementi na redicata se objekti od proizvolen tip.

    // Metodi za pristap:

    public boolean isEmpty ();
    	// Vrakja true ako i samo ako redicata e prazena.

    public int size ();
    	// Ja vrakja dolzinata na redicata.

    public E peek ();
    	// Go vrakja elementot na vrvot t.e. pocetokot od redicata.

    // Metodi za transformacija:

    public void clear ();
    	// Ja prazni redicata.

    public void enqueue (E x);
    	// Go dodava x na kraj od redicata.

    public E dequeue ();
    	// Go otstranuva i vrakja pochetniot element na redicata.

}
class LinkedQueue<E> implements Queue<E> {

	// Redicata e pretstavena na sledniot nacin:
    // length go sodrzi brojot na elementi.
    // Elementite se zachuvuvaat vo jazli dod SLL
    // front i rear se linkovi do prviot i posledniot jazel soodvetno.
    SLLNode<E> front, rear;
    int length;

    // Konstruktor ...

    public LinkedQueue () {
        clear();
    }

    public boolean isEmpty () {
       	// Vrakja true ako i samo ako redicata e prazena.
        return (length == 0);
    }

    public int size () {
    	// Ja vrakja dolzinata na redicata.
        return length;
    }

    public E peek () {
       	// Go vrakja elementot na vrvot t.e. pocetokot od redicata.
        if (front == null)
            throw new NoSuchElementException();
        return front.element;
    }

    public void clear () {
    	// Ja prazni redicata.
        front = rear = null;
        length = 0;
    }

    public void enqueue (E x) {
    	// Go dodava x na kraj od redicata.
        SLLNode<E> latest = new SLLNode<E>(x, null);
        if (rear != null) {
            rear.succ = latest;
            rear = latest;
        } else
            front = rear = latest;
        length++;
    }

    public E dequeue () {
    	// Go otstranuva i vrakja pochetniot element na redicata.
        if (front != null) {
            E frontmost = front.element;
            front = front.succ;
            if (front == null)  rear = null;
            length--;
            return frontmost;
        } else
            throw new NoSuchElementException();
    }

}
class SLLNode<E> {
	protected E element;
	protected SLLNode<E> succ;

	public SLLNode(E elem, SLLNode<E> succ) {
		this.element = elem;
		this.succ = succ;
	}

	@Override
	public String toString() {
		return element.toString();
	}
}
