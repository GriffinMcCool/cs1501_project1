/**
 * A very very basic driver for CS1501 Project 1
 * @author	Dr. Farnan
 */
package cs1501_p1;

public class App {
	public static void main(String[] args) {
		BST tree = new BST();
		System.out.println("Successfully built an empty tree!");
		//System.out.println("Tree height: " + tree.height());
		// int x = 3;
		// int y = 5;
		// int z = 1;
		// tree.put(x);
		// tree.put(y);
		// tree.put(z);
		tree.put(50);
		System.out.println("Tree height: " + tree.height());
		tree.put(40);
		tree.put(60);
		tree.put(30);
		tree.put(35);
		tree.put(70);
		tree.put(65);
		tree.put(10);
		tree.put(100);
		tree.put(36);
		tree.put(37);
		tree.put(34);
		System.out.println("Tree height: " + tree.height());
		tree.delete(7);
		tree.delete(10);
		tree.delete(70);
		tree.delete(34);
		tree.delete(40);
		tree.delete(50);
		tree.delete(10);
		tree.delete(101);
		tree.delete(99);
		tree.delete(30);
		tree.delete(35);
		tree.delete(100);
		tree.put(50);
		tree.delete(37);
		tree.put(99);
		tree.put(50);
		tree.delete(50);
		tree.put(1);
		tree.delete(65);
		tree.delete(0);
		tree.delete(60);

		tree.put(10);
		tree.put(5);
		tree.put(2);
		tree.put(8);
		tree.put(37);
		tree.put(45);
		tree.put(16);

		tree.put(10);
		tree.put(5);
		tree.put(2);
		tree.put(37);
		tree.put(45);

		tree.put(36);
		tree.put(1);
		tree.put(99);
		tree.put(100);
		BST_Inter reverse = tree.reverse();
		tree.delete(1);
		System.out.println("serialize: " + tree.serialize());
		System.out.println("serialize reverse: " + reverse.serialize());
		System.out.println("tree in order: " + tree.inOrderTraversal());
		System.out.println("reverse tree in order: " + reverse.inOrderTraversal());
		System.out.println("Tree height: " + tree.height());
		if(tree.isBalanced()) System.out.println("Tree is balanced.");
		else System.out.println("Tree is not balanced.");
	}
}
