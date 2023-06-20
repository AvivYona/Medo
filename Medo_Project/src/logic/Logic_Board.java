package logic;


public class Logic_Board 
{
	private Logic_Square _matrix[][];//the board matrix
	public Logic_Board() 
	{
		/**
		* The constructor initializes the game board matrix.
		*/
		_matrix = new Logic_Square[9][9];
		initMatrix();
	}
	public Logic_Square getSquare(int row,int col)
	{
		/**
		* Returns the square at the given row and column index.
		* @param row the row index of the square
		* @param col the column index of the square
		* @return the square at the specified row and column index
		* @rtype Logic_Square
		*/
		return _matrix[row][col];
	}
	public void initMatrix() 
	{
		/**
		* Initializes the game board matrix by setting the values of all squares.
		*/
		int i,j;//indexes
		for(i=0;i < 9;i++) 
		{
			for(j=0;j< 9 ;j++) 
			{
				if((i == 0 && j>4) || (i == 8 && j < 4)
				|| (i == 1 && j>5) || (i == 7 && j < 3) 
				|| (i == 2 && j>6) || (i == 6 && j < 2)
				|| (i == 3 && j>7) || (i == 5 && j < 1))
					_matrix[i][j] = new Logic_Square(graphic.Square._soldierColor.EMPTY,false,i,j);//not real square
				else
					_matrix[i][j] = new Logic_Square(graphic.Square._soldierColor.EMPTY,true,i,j);//real square,default rating is 2
			}
		}
	}
	public void clearMat() 
	{
		/**
		* Sets the color of all squares to EMPTY.
		*/
		int i,j;//indexes
		for(i=0;i < 9;i++) 
		{
			for(j=0;j< 9 ;j++) 
			{
				_matrix[i][j].set_stoneColor(graphic.Square._soldierColor.EMPTY);//change color to empty
			}
		}
	}
	public void printMat() 
	{
		/**
		* Prints the game board matrix.
		*/
		for(int i=0;i < 9;i++) 
		{
			if(i==3||i==5) 
				System.out.print("  ");
			else if(i==2||i==6) 
				System.out.print("   ");
			else if(i==1||i==7) 
				System.out.print("    ");
			else if(i==0||i==8) 
				System.out.print("     ");
			for(int j=0;j< 9 ;j++) 
			{
				
				if(_matrix[i][j].get_isReal()) //only print the real ones
				{					
					if(_matrix[i][j].get_stoneColor()==graphic.Square._soldierColor.BLUE) 
					{
						System.out.print("RB,");//Regular Blue
					}
					else if(_matrix[i][j].get_stoneColor()==graphic.Square._soldierColor.LIGHTBLUE) 
					{
						System.out.print("LB,");//Light Blue
					}
					else if(_matrix[i][j].get_stoneColor()==graphic.Square._soldierColor.RED) 
					{
						System.out.print("RR,");//Regular Red
					}
					else if(_matrix[i][j].get_stoneColor()==graphic.Square._soldierColor.LIGHTRED) 
					{
						System.out.print("LR,");//Light Red
					}
					else if(_matrix[i][j].get_stoneColor()==graphic.Square._soldierColor.EMPTY) 
					{
						System.out.print("EM,");//EMpty
					}
					
				}
				
			}
			System.out.println();
		}
		System.out.println("\n----------------------------------------\n");
	}
	
	public Logic_Square[][] get_matrix() {
		/*
		 * Returns the current game board matrix.
		 * :return: The current game board matrix.
		 * :rtype: matrix of Logic_Square objects
		 */
		return _matrix;
	}
	/*public void copyMatrix(Logic_Board board) 
	{
		int i,j;
		for(i=0;i<9;i++) 
		{
			for(j=0;j<9;j++) 
			{
				this._matrix[i][j] = board.getSquare(i, j);
			}
		}
	}*/
	public void set_matrix(Logic_Square[][] matrix) {
		/*
		 * Sets the game board matrix to the provided matrix.
		 * :param matrix: The new game board matrix.
		 * :type matrix: matrix of Logic_Square objects
		 */
		_matrix = matrix;
	}
	
}

