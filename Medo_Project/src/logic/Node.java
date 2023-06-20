package logic;

import java.util.LinkedList;
public class Node 
{
	private logic.Logic_Move _move;
	private LinkedList<Node> _possibleMoves;
	public Node(logic.Logic_Move move) 
	{
		/**
		 * Constructs a new node with the given logic move.
		 * @param move the logic move to store in the node
		 */
		_move = move;
		_possibleMoves = new LinkedList<>();
	}
	public LinkedList<Node> get_possibleMoves() {
		/**
		 * Returns a linked list of possible moves from this node.
		 * @return the linked list of possible moves
		 */
		return _possibleMoves;
	}
	public void set_possibleMoves(LinkedList<Node> _possibleMoves) {
		/**
		 * Sets the linked list of possible moves from this node.
		 * @param _possibleMoves the linked list of possible moves to set
		 */
		this._possibleMoves = _possibleMoves;
	}
	public logic.Logic_Move get_move() {
		/**
		 * Returns the logic move stored in this node.
		 * @return the logic move
		 */
		return _move;
	}
	public void set_move(logic.Logic_Move _move) {
		/**
		 * Sets the logic move stored in this node.
		 * @param _move the logic move to set
		 */
		this._move = _move;
	}
	@Override
	public String toString() {
		/**
		 * Returns a string representation of this node.
		 * @return the string representation of this node
		 */
		return "Node: move = " + _move + "]";
	}
	public void addChild(Node child) 
	{
		/**
		 * Adds a child node to the list of possible moves.
		 * @param child the child node to add
		 */
		_possibleMoves.add(child);
	}
	
	
	
	
}
