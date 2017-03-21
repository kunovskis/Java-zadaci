import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class BinaryTreeSetTest {
	
	public static void main(String[] args) {
		Scanner jin = new Scanner(System.in);
		int t = jin.nextInt();
		if ( t == 0 ) {
			BinaryTreeSet<Integer> ts = new BinaryTreeSet<Integer>();
			while ( jin.hasNextInt() ) {
				ts.addElement(jin.nextInt());
			}
			System.out.println(ts);
		}
		if ( t == 1 ) {
			BinaryTreeSet<String> ts = new BinaryTreeSet<String>();
			while ( true ) {
				String next = jin.next();
				if ( next.equals("stop") ) break;
				ts.addElement(next);
			}
			System.out.println(ts);
		}
		if ( t == 2 ) {
			BinaryTreeSet<Long> ts = new BinaryTreeSet<Long>();
			while ( jin.hasNextLong() ) {
				ts.addElement(jin.nextLong());
			}
			jin.next();
			System.out.println(ts);
			while ( jin.hasNextLong() ) {
				System.out.println(ts.contains(jin.nextLong()));
			}
			System.out.println(ts);
		}
		if ( t == 3 ) {
			BinaryTreeSet<String> ts = new BinaryTreeSet<String>();
			int counter = 0;
			while ( true ) {
				if ( counter % 20 == 0 ) System.out.println(ts);
				++counter;
				String next = jin.next();
				if ( next.equals("stop") ) break;
				if ( next.equals("add") ) {
					ts.addElement(jin.next());
				}
				if ( next.equals("remove") ) {
					ts.removeElement(jin.next());
				}
				if ( next.equals("query") ) {
					System.out.println(ts.contains(jin.next()));
				}
			}
			System.out.println(ts);
		}
		if ( t == 4 ) {
			BinaryTreeSet<Long> ts = new BinaryTreeSet<Long>();
			TreeSet<Long> control_set = new TreeSet<Long>();
			ArrayList<Long> all = new ArrayList<Long>();
			all.add(5L);
			int n = jin.nextInt();
			boolean exact = true;
			for ( int i = 0 ; exact&&i < n ; ++i ) {
				if ( Math.random() < 0.4 ) {
					if ( Math.random() < 0.6 ) {
						long to_add = (long)(Math.random()*98746516548964156L);
						ts.addElement(to_add);
						control_set.add(to_add);
						all.add(to_add);
					}
					else {
						int add_idx = (int)(Math.random()*all.size());
						long to_add = all.get(add_idx);
						ts.addElement(to_add);
						control_set.add(to_add);
					}
				}
				else {
					if ( Math.random() < 0.4 ) {
						if ( Math.random() < 0.1 ) {
							long to_remove = (long)(Math.random()*98746516548964156L);
							ts.removeElement(to_remove);
							control_set.remove(to_remove);
						}
						else {
							int remove_idx = (int)(Math.random()*all.size());
							long to_remove = all.get(remove_idx);
							ts.removeElement(to_remove);
							control_set.remove(to_remove);
						}
					}
					else {
						if ( Math.random() < 0.3 ) {
							long to_query = (long)(Math.random()*98746516548964156L);
							exact &= ts.contains(to_query)==control_set.contains(to_query);
						}
						else {
							int query_idx = (int)(Math.random()*all.size());
							long to_query = all.get(query_idx);
							exact &= ts.contains(to_query)==control_set.contains(to_query);
						}
					}
				}
			}
			System.out.println(exact);
		}
	}

}


class BinaryTreeSet<T extends Comparable<T>>{
	
	private class TreeNode<T extends Comparable<T>>{
		
		T data;
		TreeNode<T> left, right;
		boolean exist;
		
		TreeNode(T data, TreeNode<T> left, TreeNode<T> right){
			this.data=data;
			this.left=left;
			this.right=right;
			exist=true;
		}
		
	}
	
	TreeNode<T> root;
	
	BinaryTreeSet(){
		root=null;
	}
	
	void addElement(T element){
		root = add(element, root);
	}
	
	private TreeNode<T> add(T element, TreeNode<T> root){
		if(root==null)
			return new TreeNode<T>(element, null, null);
		if(root.data.equals(element)){
			if(!root.exist)
				root.exist=true;
			return root;
		}
		if(element.compareTo(root.data) < 0)
			root.left = add(element, root.left);
		else
			root.right = add(element, root.right);
		return root;
	}
	
	boolean removeElement(T element){
		return remove(element, root);
	}
	
	private boolean remove(T element, TreeNode<T> root){
		if(root==null)
			return false;
		if(root.data.equals(element)){
			root.exist = false;
			return true;
		}
		if(element.compareTo(root.data) < 0)
			return remove(element, root.left);
		return remove(element, root.right);
	}
	
	boolean contains(T element){
		return contains(element, root);
	}
	
	private boolean contains(T element, TreeNode<T> root){
		if(root == null)
			return false;
		if(element.equals(root.data)){
			if (root.exist)
				return true;
			return false;
		}
		if(element.compareTo(root.data)<0)
			return contains(element, root.left);
		return contains(element, root.right);
	}
	
	private boolean isEmpty(){
		return root == null;
	}
	
	private void inorder(TreeNode<T> root, StringBuilder sb){
		if(root != null){
			inorder(root.left, sb);
			if(root.exist)
				sb.append(root.data + " ");
			inorder(root.right, sb);
		}
	}
	
	@Override
	public String toString(){
		if(isEmpty())
			return "EMPTY";
		StringBuilder sb = new StringBuilder();
		inorder(root, sb);
		return sb.toString().substring(0,  sb.length()-1);
	}
}
