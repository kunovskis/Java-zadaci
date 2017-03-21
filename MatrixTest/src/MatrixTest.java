import java.util.*;

public class MatrixTest {
	
	public static void main(String[] args) {
		Scanner jin = new Scanner(System.in);
		int t = jin.nextInt();
		if ( t == 0 ) {
			int r = jin.nextInt();
			int c = jin.nextInt();
			Matrix<Integer> matrix = new Matrix<Integer>(r,c);
			print(matrix);
		}
		if ( t == 1 ) {
			int r = jin.nextInt();
			int c = jin.nextInt();
			Matrix<Integer> matrix = new Matrix<Integer>(r,c);
			for ( int i = 0 ; i < r ; ++i ) {
				for ( int k = 0 ; k < c ; ++k ) {
					matrix.setElementAt(i, k, jin.nextInt());
				}
			}
			print(matrix);
		}
		if ( t == 2 ) {
			int r = jin.nextInt();
			int c = jin.nextInt();
			Matrix<String> matrix = new Matrix<String>(r,c);
			for ( int i = 0 ; i < r ; ++i ) {
				for ( int k = 0 ; k < c ; ++k ) {
					matrix.setElementAt(i, k, jin.next());
				}
			}
			print(matrix);
		}
		if ( t == 3 ) {
			int r = jin.nextInt();
			int c = jin.nextInt();
			Matrix<String> matrix = new Matrix<String>(r,c);
			for ( int i = 0 ; i < r ; ++i ) {
				for ( int k = 0 ; k < c ; ++k ) {
					matrix.setElementAt(i, k, jin.next());
				}
			}
			print(matrix);
			matrix.deleteRow(jin.nextInt());
			matrix.deleteRow(jin.nextInt());
			print(matrix);
			int ir = jin.nextInt();
			matrix.insertRow(ir);
			for ( int k = 0 ; k < c ; ++k ) {
				matrix.setElementAt(ir, k, jin.next());
			}
			ir = jin.nextInt();
			matrix.insertRow(ir);
			for ( int k = 0 ; k < c ; ++k ) {
				matrix.setElementAt(ir, k, jin.next());
			}
			print(matrix);
			matrix.deleteColumn(jin.nextInt());
			matrix.deleteColumn(jin.nextInt());
			print(matrix);
			int ic = jin.nextInt();
			matrix.insertColumn(ir);
			for ( int i = 0 ; i < r ; ++i ) {
				matrix.setElementAt(i, ic, jin.next());
			}
			ic = jin.nextInt();
			matrix.insertColumn(ic);
			for ( int i = 0 ; i < r ; ++i ) {
				matrix.setElementAt(i, ic, jin.next());
			}
			print(matrix);
		}
		if ( t == 4 ) {
			int r = jin.nextInt();
			int c = jin.nextInt();
			Matrix<Integer> matrix = new Matrix<Integer>(r,c);
			for ( int i = 0 ; i < r ; ++i ) {
				for ( int k = 0 ; k < c ; ++k ) {
					matrix.setElementAt(i, k, jin.nextInt());
				}
			}
			print(matrix);
			int nr = jin.nextInt();
			int nc = jin.nextInt();
			matrix.resize(nr, nc);
			print(matrix);
			matrix.fill(jin.nextInt());
			print(matrix);
		}
	}
	
	public static void print ( Matrix<?> m ) {
		int r = m.getNumRows();int c = m.getNumColumns();
		System.out.println("  "+r+" x "+c);
		System.out.print("    ");
		for ( int k = 0 ; k < c ; ++k ) {
			System.out.print(k+"    ");
		}
		System.out.println();
		System.out.print("  ");
		for ( int k = 0 ; k < c ; ++k ) {
			System.out.print("-----");
		}
		System.out.println();
		for ( int i = 0 ; i < r ; ++i ) {
			System.out.print(i+"|");
			for ( int k = 0 ; k < c ; ++k ) {
				if ( k > 0 ) System.out.print(" ");
				System.out.print(m.getElementAt(i, k));
			}
			System.out.println();
		}
		System.out.println();
	}

}

class Matrix<T>{
	private List<List<T>> matrix;
	private int n, m;
	
	public Matrix(int numRows, int numCols){
		this.n=numRows;
		this.m=numCols;
		matrix = new ArrayList<List<T>> (numCols);
		for(int i=0; i<numRows; i++){
			matrix.add(new ArrayList<T>());
		}
		for(int i=0; i<n; i++){
			for(int j=0; j<m; j++){
				matrix.get(i).add(null);
			}
		}
	}
	
	public int getNumRows(){
		return n;
	}
	
	public int getNumColumns(){
		return m;
	}
	
	public T getElementAt(int row, int col){
		return matrix.get(row).get(col);
	}
	
	public void setElementAt(int row, int col, T value){
		matrix.get(row).remove(col);
		matrix.get(row).add(col, value);
	}
	
	public void fill(T element){
		for(int i=0; i<n; i++){
			for(int j=0; j<m; j++){
				matrix.get(i).remove(j);
				matrix.get(i).add(j,element);
			}
		}
	}
	
	public void insertRow(int row){
		if(row<0 || row>n)
			throw new ArrayIndexOutOfBoundsException();
		matrix.add(row, new ArrayList<T>());
		for(int i=0; i<m; i++)
			matrix.get(row).add(null);
		n++;
	}
	
	public void deleteRow(int row){
		if(row<0 || row>=n)
			throw new ArrayIndexOutOfBoundsException();
		matrix.remove(row);
		n--;
	}
	
	public void insertColumn(int col){
		if(col<0 || col>m)
			throw new ArrayIndexOutOfBoundsException();
		for(int i=0; i<n; i++)
			matrix.get(i).add(col, null);
		m++;
	}
	
	public void deleteColumn(int col){
		if(col<0 || col>=m)
			throw new ArrayIndexOutOfBoundsException();
		for(int i=0; i<n; i++)
			matrix.get(i).remove(col);
		m--;
	}
	
	public void resize(int rows, int cols){
		int red=n,kol=m;
		if(rows>red){
			for(int i=0; i<rows-red; i++){
				matrix.add(red+i, new ArrayList<T>());
				for(int j=0; j<m; j++)
					matrix.get(red+i).add(null);
			}
		}
		else{
			for(int i=0; i<red-rows; i++){
				matrix.remove(n-i-1);
			}
		}
		n=rows;
		if(cols>kol){
			for(int i=0; i<n; i++){
				for(int j=0; j<cols-kol; j++){
					matrix.get(i).add(m+j, null);
				}
			}
		}
		else{
			for(int i=0; i<n; i++){
				for(int j=0; j<kol-cols; j++){
					matrix.get(i).remove(m-j-1);
				}
			}
		}
		m=cols;
	}
	
}


