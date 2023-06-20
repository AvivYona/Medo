package logic;


public class Logic_Square 
{
	
	private boolean _isReal; // whether the square is real or not
	private graphic.Square._soldierColor _stoneColor; // color of the soldier on the square
	private int _row; // row index of the square on the board
	private int _col; // column index of the square on the board
	public Logic_Square(graphic.Square._soldierColor color,boolean real,int row,int col)
	{
		/**
		 * Constructor for creating a new square object with the given parameters.
		 * 
		 * @param color - color of the soldier on the square.
		 * @param real - whether the square is real or not.
		 * @param row - row index of the square on the board.
		 * @param col - column index of the square on the board.
		 */
		_stoneColor = color;
		_isReal = real;
		_row = row;
		_col = col;
		
		
	}
	public graphic.Square._soldierColor get_stoneColor() {
		/**
		 * Returns the color of the soldier on the square.
		 * 
		 * @return the color of the soldier on the square.
		 */
		return _stoneColor;
	}
	public void set_stoneColor(graphic.Square._soldierColor _stoneColor) {
		/**
		 * Sets the color of the soldier on the square.
		 * 
		 * @param _stoneColor - the color of the soldier on the square.
		 */
		this._stoneColor = _stoneColor;
	}
	
	public boolean get_isReal() {
		/**
		 * return if the square is real or not.
		 * 
		 * @return true if the square is real, false otherwise.
		 */
		return _isReal;
	}
	public void set_isReal(boolean _isReal) {
		/**
		 * Sets whether the square is real or not.
		 * 
		 * @param _isReal - true if the square is real, false otherwise.
		 */
		this._isReal = _isReal;
	}
	
	
	
	@Override
	public int hashCode() {
		/**
		 * Computes the hash code of the square based on its row and column indices.
		 * 
		 * @return the hash code of the square.
		 */
		// TODO Auto-generated method stub
		return _row * 100 + _col;
	}
	@Override
	public boolean equals(Object obj) 
	{
		/**
		 * Checks whether the given object is equal to this square based on its hash code.
		 * 
		 * @param obj - the object to compare with.
		 * @return true if the object is equal to this square, false otherwise.
		 */
		if(obj.hashCode() == this.hashCode()) 
		{
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		/**
		 * Returns a string representation of the square, containing its row and column indices.
		 * 
		 * @return a string representation of the square.
		 */
		return "Square[_row=" + _row + ", _col=" + _col +", _color="+_stoneColor + "]";
	}
	public int get_row() {
		return _row;
	}
	public void set_row(int _row) {
		this._row = _row;
	}
	public int get_col() {
		return _col;
	}
	public void set_col(int _col) {
		this._col = _col;
	}

}

