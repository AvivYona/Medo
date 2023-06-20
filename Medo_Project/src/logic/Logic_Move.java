package logic;
public class Logic_Move {
	private int _i; // the row index of the move on the game board
	private int _j; // the column index of the move on the game board
	private Rating _moveRating; // the rating of the move
	private graphic.Square._soldierColor _color; // the color of the soldier making the move
	private int _eatStones;
	private int _defStones;
	public Logic_Move(int i,int j, Rating moveRating,graphic.Square._soldierColor color) 
	{
		/**
		 * Constructs a new Logic_Move object with the specified row index, column index, move rating, and soldier color.
		 * @param i the row index of the move.
		 * @param j the column index of the move.
		 * @param moveRating the rating of the move.
		 * @param color the color of the soldier making the move.
		 */
		_i = i;
		_j=j;
		_moveRating = moveRating;
		_color = color;
		_eatStones = 0;
		_defStones = 0;
	}
	public int get_i() {
		/**
		 * Returns the row index of the move.
		 * @return the row index of the move.
		 */
		return _i;
	}
	public void set_i(int _i) {
		/**
		 * Sets the row index of the move.
		 * @param _i the new row index of the move.
		 */
		this._i = _i;
	}
	public Rating get_moveRating() {
		/**
		 * Returns the rating of the move.
		 * @return the rating of the move.
		 */
		return _moveRating;
	}
	public void set_moveRating(Rating _moveRating) {
		/**
		 * Sets the rating of the move.
		 * @param _moveRating the new rating of the move.
		 */
		this._moveRating = _moveRating;
	}
	public int get_j() {
		/**
		 * Returns the column index of the move.
		 * @return the column index of the move.
		 */
		return _j;
	}
	public void set_j(int _j) {
		/**
		 * Sets the column index of the move.
		 * @param _j the new column index of the move.
		 */
		this._j = _j;
	}
	@Override
	public String toString() {
		return "Logic_Move [_i=" + _i + ", _j=" + _j + ", _moveRating=" + _moveRating + ", _color=" + _color + "]";
	}
	public graphic.Square._soldierColor get_color() {
		/**
		 * Sets the column index of the move.
		 * @param _j the new column index of the move.
		 */
		return _color;
	}
	public void set_color(graphic.Square._soldierColor _color) {
		/**
		Sets the color of the soldier making the move.
		@param _color The new color of the soldier.
		*/
		this._color = _color;
	}
	public static Rating cmpBetweenRatings(Rating r1,Rating r2) 
	{
		/**

		Compares two ratings and returns the higher one based on predefined priorities.
		@param r1 The first rating to compare.
		@param r2 The second rating to compare.
		@return The higher rating between the two based on predefined priorities.
		*/
		
		if(r1 == Rating.eatTheMost)
			return r1;
		if(r2 == Rating.eatTheMost)
			return r2;
		if(r1 == Rating.eat)
			return r1;
		if(r2 == Rating.eat)
			return r2;
		if(r1 == Rating.defenceTheMost)
			return r1;
		if(r2 == Rating.defenceTheMost)
			return r2;
		if(r1 == Rating.defence)
			return r1;
		if(r2 == Rating.defence)
			return r2;
		if(r1 == Rating.oneFromEat)
			return r1;
		if(r2 == Rating.oneFromEat)
			return r2;
		if(r1 == Rating.oneFromDefence)
			return r1;
		if(r2 == Rating.oneFromDefence)
			return r2;
		if(r1 == Rating.twoFromEat)
			return r1;
		if(r2 == Rating.twoFromEat)
			return r2;
		if(r1 == Rating.threeFromEat)
			return r1;
		if(r2 == Rating.threeFromEat)
			return r2;
		if(r1 == Rating.fourFromEat)
			return r1;
		if(r2 == Rating.fourFromEat)
			return r2;
		if(r1 == Rating.fiveFromEat)
			return r1;
		if(r2 == Rating.fiveFromEat)
			return r2;
		if(r1 == Rating.sixFromEat)
			return r1;
		if(r2 == Rating.sixFromEat)
			return r2;
		if(r1 == Rating.sevenFromEat)
			return r1;
		if(r2 == Rating.sevenFromEat)
			return r2;
		if(r1 == Rating.eightFromEat)
			return r1;
		if(r2 == Rating.eightFromEat)
			return r2;
		if(r1 == Rating.nerbyenemy)
			return r1;
		if(r2 == Rating.nerbyenemy)
			return r2;
		if(r1 == Rating.nerbyTeam)
			return r1;
		if(r2 == Rating.nerbyTeam)
			return r2;
		if(r1 == Rating.regular)
			return r1;
		if(r2 == Rating.regular)
			return r2;
		if(r1 == Rating.Suicide)
			return r1;
		if(r2 == Rating.Suicide)
			return r2;
		if(r1 == Rating.teamSuicide)
			return r1;
		if(r2 == Rating.teamSuicide)
			return r2;
		
		return r1;
	}
	public int get_eatStones() {
		return _eatStones;
	}
	public void set_eatStones(int _eatStones) {
		this._eatStones = _eatStones;
	}
	public int get_defStones() {
		return _defStones;
	}
	public void set_defStones(int _defStones) {
		this._defStones = _defStones;
	}
	
	
}
//check from the heigher and cmp them
		//if r1 is eatTheMost so it's the biggest he will exit
		//if r2 is eatTheMost and not r1, he is the biggest
