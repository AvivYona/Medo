package logic;

import graphic.Square._soldierColor;

public class Tree {
	private Node _root;
	public Tree() 
	{
		/**
		 * Constructs a new empty tree with a null root node.
		 */
		_root = new Node(null);
	}
	public Node get_root() {
		/**
		 * Returns the root node of the tree.
		 * @return the root node
		 */
		return _root;
	}
	public void set_root(Node _root) {
		/**
		 * Sets the root node of the tree.
		 * @param _root the root node to set
		 */
		this._root = _root;
	}
	public void printTree(Node node) {
		/**
		 * Prints the tree starting from the given node, including the logic move of each node and its child nodes.
		 * @param node the node to start printing from
		 */
		if(node.get_move() != null)
			System.out.print("father: " + node.get_move().toString());
		if(!node.get_possibleMoves().isEmpty())
		{
			System.out.print("\nsons: ");
			for (Node child : node.get_possibleMoves())
			{
				System.out.println(child.get_move().toString() + "||");
			}
		}
		else 
		{
			System.out.println();
		}
	    System.out.println("-----------------------------");
	    
	    System.out.println();
	    for (Node child : node.get_possibleMoves()) {
	        printTree(child);
	    }
	}
}
