package logic;

public class Logic_Player {
	
	private int _points;//player's points
	private int _turnLeft;//turn left,from the second play in the game the players start from two movs to do
	private String _lastTurn;//color of the last turn
	public Logic_Player() 
	{
		/**
		 * Constructor for Logic_Player class.
		 * Initializes points to 0, turnLeft to 2, and lastTurn to "Empty".
		 */
		_points = 0;
		_turnLeft = 2;
		_lastTurn = "Empty";
	}
	public void incPoints() 
	{
		 //Increases the player's points by 1.
		this._points++;
	}
	public int get_points() {
		/**
		 * Returns the player's current points.
		 * @return the player's current points.
		 */
		return _points;
	}
	public void set_points(int _points) {
		/**
		 * Sets the player's current points to the specified value.
		 * @param _points the new value for the player's points.
		 */
		this._points = _points;
	}
	public int get_turnLeft() {
		/**
		 * Returns the number of turns left for the player.
		 * @return the number of turns left for the player.
		 */
		return _turnLeft;
	}
	public void set_turnLeft(int _turnLeft) {
		/**
		 * Sets the number of turns left for the player.
		 * @param _turnLeft the new value for the number of turns left.
		 */
		this._turnLeft = _turnLeft;
	}
	public String get_lastTurn() {
		/**
		 * Returns the color of the last turn played by the player.
		 * @return the color of the last turn played by the player.
		 */
		return _lastTurn;
	}
	public void set_lastTurn(String _lastTurn) {
		/**
		 * Sets the color of the last turn played by the player.
		 * @param _lastTurn the new value for the color of the last turn played.
		 */
		this._lastTurn = _lastTurn;
	}
}
