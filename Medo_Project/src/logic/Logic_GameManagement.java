package logic;

import java.util.HashSet;
import java.util.LinkedList;
import graphic.BoardFrame;
import graphic.Square;
import graphic.WinScreen;
import graphic.Square._soldierColor;


import logic.Rating;

public class Logic_GameManagement 
{
	private Logic_Player _player1;//colors:blue,light blue
	private Logic_Player _player2;//colors:red,light red
	private Logic_Board _boardGame;//the real board game
	private Logic_Board _tempBoardGame;//temp board for tree
	private boolean _isWin;//if there is AnyWin
	private int _countTurnAi;//how much turns the ai left to do, 2 or 1;
	private HashSet<Logic_Square> _overGrowthSet;
	private HashSet<Logic_Square> _eatenSet;
	private HashSet<Logic_Square> _emptySet;//to the ai, emptySet that surround group
	private int _emptySetIndex;
	private boolean _findEmpty = false;
	private boolean _tempEatFlag = false;//if it was eaten
	private LinkedList<Logic_Move> _possibleMoves;
	private final graphic.Square._soldierColor BLUE = graphic.Square._soldierColor.BLUE;
	private final graphic.Square._soldierColor LIGHTBLUE = graphic.Square._soldierColor.LIGHTBLUE;
	private final graphic.Square._soldierColor RED = graphic.Square._soldierColor.RED;
	private final graphic.Square._soldierColor LIGHTRED = graphic.Square._soldierColor.LIGHTRED;
	private final graphic.Square._soldierColor EMPTY = graphic.Square._soldierColor.EMPTY;
	private Square._soldierColor _lastTurnColorAi = EMPTY;
	private Tree _tree;
	LinkedList<Logic_Move> _tempMoves = new LinkedList<>();
	public Logic_GameManagement() 
	{
		/**
		* constructor initialize variables.
		*/
		_tempBoardGame = new Logic_Board();
		_possibleMoves = new LinkedList<>();
		_player1 = new Logic_Player();
		_player2 = new Logic_Player();
		_boardGame = new Logic_Board();
		_isWin = false;
		_countTurnAi = 2;
		_overGrowthSet = new HashSet<Logic_Square>();
		_eatenSet = new HashSet<Logic_Square>();
		_emptySet = new HashSet<Logic_Square>();
		//Play();
	}
	
	public void checkOverGrowth(int row,int col,graphic.Square._soldierColor color,Logic_Board board)
	//from where to check,do loop for all the stones
	{
		/**
		* check if overGrowth happend.
		* @param row the row index of the square
		* @param col the column index of the square
		* @param color the color of the stone
		* @param board, on which board to check
		* @return void
		*/
		
		//dont forget to clear set before calling the func
		if(_overGrowthSet.contains(board.getSquare(row, col)))
		{
			
			return;
		}
			
		if(board.getSquare(row, col).get_stoneColor() == color) 
		{
			
			_overGrowthSet.add(board.getSquare(row, col));
			//check left:
			//left:
			if(col > 0 && board.getSquare(row, col-1).get_isReal()) 
				checkOverGrowth(row, col-1, color,board);
			//left up:
			if(row > 0 && col > 0)
				checkOverGrowth(row-1, col-1, color,board);
			//left down:
			if(row < 8 && board.getSquare(row+1, col).get_isReal())
				checkOverGrowth(row+1, col, color,board);
			//check right:
			//right:
			if(col < 8 && board.getSquare(row, col+1).get_isReal())
				checkOverGrowth(row, col+1, color,board);
			//right up:
			if(row > 0 && board.getSquare(row-1, col).get_isReal())
				checkOverGrowth(row-1, col, color,board);
			//right down:
			if(row < 8 && col < 8 &&board.getSquare(row+1, col+1).get_isReal() )
				checkOverGrowth(row+1, col+1, color,board);

		}
			
		else 
			return;
		
	}
	public boolean doOverGrowth(Logic_Board board) 
	{
		
		/**
		* discrioption: do the overGrowth
		* @param board, on which board.
		* @return boolean, if the OverGrowth happened
		* @rtype boolean
		*/
		if(_overGrowthSet.size() > 5) 
		{//overgrowth is only from 6
			for(int i=0;i < 9;i++) 
			{
				for(int j=0;j< 9 ;j++) 
				{
					if(board.getSquare(i, j).get_isReal()) 
					{
						if(_overGrowthSet.contains(board.getSquare(i, j))) 
						{
							if(board == _boardGame)
							{
								if(board.getSquare(i, j).get_stoneColor() == BLUE 
										|| board.getSquare(i, j).get_stoneColor() == LIGHTBLUE) 
									_player2.incPoints();
								else if(board.getSquare(i, j).get_stoneColor() == RED 
										|| board.getSquare(i, j).get_stoneColor() == LIGHTRED)
									_player1.incPoints();
								
								BoardFrame.getSquare(i,j).set_stoneColor(graphic.Square._soldierColor.EMPTY); 
								BoardFrame.getSquare(i,j).repaint();
							}
							board.getSquare(i, j).set_stoneColor(EMPTY);
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	public void checkWin() 
	{
		/**
		* discrioption:check win,open win screen if win
		* @return void
		*/
		if(_player1.get_points() >= 20)
		{
			
			_isWin = true;
			new WinScreen(true).setVisible(true);
		}
		else if(_player2.get_points() >= 20)
		{
			
			_isWin = true;
			
			new WinScreen(false).setVisible(true);
		}
	}
	
	public void callToCheckEaten(int row,int col,graphic.Square._soldierColor color,Logic_Board board) 
	{
		/**
		* discrioption:call to the function check eaten
		* @param row the row index of the square
		* @param col the column index of the square
		* @param color the color of the stone
		* @param board, on which board to check
		* @return void
		*/
		
		graphic.Square._soldierColor colorOfStone;
		LinkedList<graphic.Square._soldierColor> oppositeColors = new LinkedList<>();
		oppositeColors.clear();
		if(color == BLUE || color == LIGHTBLUE)
		{
			oppositeColors.add(RED);
			oppositeColors.add(LIGHTRED);
		}
		else if(color == RED || color == LIGHTRED)
		{
			oppositeColors.add(BLUE);
			oppositeColors.add(LIGHTBLUE);
		}
		
		_eatenSet.clear();
		_findEmpty = false;
		
		if(col > 0 && board.getSquare(row, col-1).get_isReal())
		{
			colorOfStone = board.getSquare(row, col-1).get_stoneColor();
			if(oppositeColors.contains(colorOfStone))
			{
				checkEaten(row, col-1,oppositeColors.getFirst(),board);
				if(!_findEmpty) //if findEmpty = false
				{
					if(board == this._boardGame)//we don't want to remove from temp board
						doEat();
					_tempEatFlag = true;
					return;
				}
			}
		}
		_eatenSet.clear();
		_findEmpty = false;
		//left up:
		if(row > 0 && col > 0)
		{
			colorOfStone = board.getSquare(row-1, col-1).get_stoneColor();
			if(oppositeColors.contains(colorOfStone))	{
				checkEaten(row-1, col-1,oppositeColors.getFirst(),board);
				if(!_findEmpty) 
				{
					if(board == this._boardGame)
						doEat();
					_tempEatFlag = true;
					return;
				}
			}
		}
		_eatenSet.clear();
		_findEmpty = false;
		//left down:
		if(row < 8 && board.getSquare(row+1, col).get_isReal())
		{
			colorOfStone = board.getSquare(row+1, col).get_stoneColor();
			if(oppositeColors.contains(colorOfStone))
			{
				checkEaten(row+1, col,oppositeColors.getFirst(),board);
				if(!_findEmpty) 
				{
					if(board == this._boardGame)
						doEat();
					_tempEatFlag = true;
					return;
				}
			}
		}
		_eatenSet.clear();
		_findEmpty = false;
		//check right:
		//right:
		if(col < 8 && board.getSquare(row, col+1).get_isReal())
		{
			colorOfStone = board.getSquare(row, col+1).get_stoneColor();
			if(oppositeColors.contains(colorOfStone))
			{
				checkEaten(row, col+1,oppositeColors.getFirst(),board);
				if(!_findEmpty) 
				{
					if(board == this._boardGame)
						doEat();
					_tempEatFlag = true;
					return;
				}
			}
		}
		_eatenSet.clear();
		_findEmpty = false;
		//right up:
		if(row > 0 && board.getSquare(row-1, col).get_isReal())
		{
			colorOfStone = board.getSquare(row-1, col).get_stoneColor();
			if(oppositeColors.contains(colorOfStone))	{
				checkEaten(row-1, col,oppositeColors.getFirst(),board);
				if(!_findEmpty) 
				{
					if(board == this._boardGame)
						doEat();
					_tempEatFlag = true;
					return;
				}
			}
		}
		_eatenSet.clear();
		_findEmpty = false;
		//right down:
		if(row < 8 && col < 8 && board.getSquare(row+1, col+1).get_isReal())
		{
			colorOfStone = board.getSquare(row+1, col+1).get_stoneColor();
			if(oppositeColors.contains(colorOfStone))	{
				checkEaten(row+1, col+1,oppositeColors.getFirst(),board);
				if(!_findEmpty) 
				{
					if(board == this._boardGame)
						doEat();
					_tempEatFlag = true;
					return;
				}
			}
		}	
		colorOfStone = board.getSquare(row, col).get_stoneColor();
		if(oppositeColors.contains(colorOfStone))	{
			checkEaten(row, col,oppositeColors.getFirst(),board);
			if(!_findEmpty) 
			{
				if(board == this._boardGame)
					doEat();
				_tempEatFlag = true;
				return;
			}
		}	
		_eatenSet.clear();
	}
	
	public void doEat() 
	{
		/**
		* discrioption:do the eat
		* @return void 
		*/
		int i,j;
		for(i=0;i<9;i++) 
		{
			for(j=0;j<9;j++) 
			{
				if(_eatenSet.contains(_boardGame.getSquare(i, j))) 
				{
					
					if(_boardGame.getSquare  (i, j).get_stoneColor() == BLUE 
							|| _boardGame.getSquare(i, j).get_stoneColor() == LIGHTBLUE) 
						_player2.incPoints();
					else 
						_player1.incPoints();
					_boardGame.getSquare(i, j).set_stoneColor(EMPTY);
					
					BoardFrame.getSquare(i,j).set_stoneColor(graphic.Square._soldierColor.EMPTY); 
					BoardFrame.getSquare(i,j).repaint();
				}
			}
		}
	}
	
	public void checkEaten(int row,int col,graphic.Square._soldierColor color,Logic_Board board) 
	{
		/**
		* discrioption:search for empty
		* @param row the row index of the square
		* @param col the column index of the square
		* @param color the color of the stone
		* @param board, on which board to check
		* @return void
		* @rtype 
		*/
		if(_eatenSet.contains(board.getSquare(row,col)))
		{
			return;
		}
		
		graphic.Square._soldierColor color2 = (color == BLUE)?LIGHTBLUE
				:(color==LIGHTBLUE)?BLUE
				:(color==RED)?LIGHTRED
				:(color==LIGHTRED)?RED:EMPTY;
		if((board.getSquare(row, col).get_stoneColor() == color 
				|| board.getSquare(row, col).get_stoneColor() == color2) && color2 != EMPTY) 
		{
			_eatenSet.add(board.getSquare(row, col));
		}
		else 
			return;
		//check left:
		//left:
			if(col > 0 && board.getSquare(row, col-1).get_isReal())
			{
				if(board.getSquare(row, col-1).get_stoneColor() == EMPTY)
				{
					_findEmpty = true ;
					_emptySet.add(board.getSquare(row, col-1));
				}
				else 
					checkEaten(row, col-1, color,board);
			}
			//left up:
			if(row > 0 && col > 0)
				if(board.getSquare(row-1, col-1).get_stoneColor() == EMPTY) 
				{
					_findEmpty = true ;
					_emptySet.add(board.getSquare(row-1, col-1));
				}
				else
					checkEaten(row-1, col-1, color,board);
			//left down:
			if(row < 8 &&board.getSquare(row+1, col).get_isReal())
				if(board.getSquare(row+1, col).get_stoneColor() == EMPTY) 
				{
					_findEmpty = true ;
					_emptySet.add(board.getSquare(row+1, col));
				}
				else
					checkEaten(row+1, col, color,board);
				
			//check right:
			//right:
			if(col < 8 && board.getSquare(row, col+1).get_isReal())
				if(board.getSquare(row, col+1).get_stoneColor() == EMPTY) 
				{
					_findEmpty = true ;
					_emptySet.add(board.getSquare(row, col+1));
				}
				else
					checkEaten(row, col+1, color,board);
			//right up:
			if(row > 0 && board.getSquare(row-1, col).get_isReal())
				if(board.getSquare(row-1, col).get_stoneColor() == EMPTY) 
				{
					_findEmpty = true ;
					_emptySet.add(board.getSquare(row-1, col));
				}
				else
					checkEaten(row-1, col, color,board);
			//right down:
			if(row < 8 && col<8 && board.getSquare(row+1, col+1).get_isReal())
				if(board.getSquare(row+1, col+1).get_stoneColor() == EMPTY) 
				{
					_findEmpty = true ;
					_emptySet.add(board.getSquare(row+1, col+1));
				}
				else
					checkEaten(row+1, col+1, color,board);		
		return;
	}
	public LinkedList<Logic_Move> allPossibleMoves(Logic_Board board
													,Square._soldierColor color) 
	{
		/**
		* discrioption:the function calculate the possible moves and return it
		* @param board,the board we want to work on.
		* @param color,red or blue
		* @return list of the moves that you can do.
		* @rtype LinkedList<Logic_Move>
		*/
		LinkedList<Logic_Move> possibleMovesLinkedList = new LinkedList<Logic_Move>();
		int i,j,counter=0;
		for(i = 0; i < 9; i++) 
		{
			for(j = 0; j < 9; j++) 
			{
				if(board.getSquare(i, j).get_isReal() &&
						board.getSquare(i, j).get_stoneColor() == EMPTY) 
				{
					if(color == RED) 
					{
						possibleMovesLinkedList.add(new Logic_Move(i, j,
								logic.Rating.regular,RED));
						if(j % 2 == 0) //we want once red once lightRed.
							possibleMovesLinkedList.getLast().set_color(LIGHTRED);
						counter++;
					}
					else if(color == BLUE) 
					{
						possibleMovesLinkedList.add(new Logic_Move(i, j, logic.Rating.regular,BLUE));
						if(j % 2 == 0) 
							possibleMovesLinkedList.getLast().set_color(LIGHTBLUE);
						counter++;
					}
				}
			}
		}	
		return possibleMovesLinkedList;
		
	}
	
	public void evaluate(LinkedList<Logic_Move> possibleMoveList ,
			Square._soldierColor teamColorRegular,Square._soldierColor teamColorLight,
			Square._soldierColor enemyColorRegular,Square._soldierColor enemyColorLight,
			Logic_Board _boardGame) 
	/**
	* discrioption:evaluate the list,change the ratings
	* @param possibleMoveList,the list we will work on.
	* @param teamColorRegular
	* @param teamColorLight
	* @param enemyColorRegular
	* @param enemyColorLight
	* @param _boardGame, the board we will work on
	* @return void
	*/
	{
		if(possibleMoveList.size()<1)
		{
			
			return;
		}
		int i,j,k;
		graphic.Square._soldierColor aiColor;
		int index;
		logic.Rating nikudLight,nikudRegular;
		int maxEatIndex = -1;
		int maxDefIndex = -1;
		_eatenSet.clear();
		_emptySet.clear();
		_overGrowthSet.clear();
		Logic_Board tempBoardGame = new Logic_Board();
		tempBoardGame.set_matrix(_boardGame.get_matrix());
		//check overgrowth:
		for(index = 0; index < possibleMoveList.size(); index++) 
		{	
			if(_lastTurnColorAi == EMPTY) //mean you can choose both colors
			{
				//check overgrowth;
				nikudRegular = evalOverGrowth(possibleMoveList,teamColorRegular, index,tempBoardGame);
				//check if red cause to overgrowth
				nikudLight = evalOverGrowth(possibleMoveList,teamColorLight, index,tempBoardGame);
				//check if light red cause to overgrowth
				if(Logic_Move.cmpBetweenRatings(nikudRegular, nikudLight) == nikudRegular)
					//he is the biger 
				{
					aiColor = teamColorRegular;
					possibleMoveList.get(index).set_color(teamColorRegular);
					possibleMoveList.get(index).set_moveRating(nikudRegular);
				}
				else 
				{
					aiColor = teamColorLight;
					if(Logic_Move.cmpBetweenRatings(nikudRegular, nikudLight) == nikudLight)
						possibleMoveList.get(index).set_color(teamColorLight);
					possibleMoveList.get(index).set_moveRating(nikudLight);
				}
			}
			else if(_lastTurnColorAi == teamColorRegular) 
			{
				aiColor = teamColorLight;
				possibleMoveList.get(index).set_color(teamColorLight);
				possibleMoveList.get(index).set_moveRating(evalOverGrowth(possibleMoveList,teamColorLight, index,tempBoardGame));
			}
			else if(_lastTurnColorAi == teamColorLight) 
			{
				aiColor = teamColorRegular;
				possibleMoveList.get(index).set_color(teamColorRegular);
				possibleMoveList.get(index).set_moveRating(evalOverGrowth(possibleMoveList,teamColorRegular, index,tempBoardGame));
				


			}
			else 
			{
				System.out.println("Problem in evaluate,line 548");
				aiColor = EMPTY;//just to remove problems
			}
			
			//done check overgrowth;
			
			//check eaten:
			tempBoardGame.getSquare(possibleMoveList.get(index).get_i()
					,possibleMoveList.get(index).get_j()).set_stoneColor(aiColor);
			//put the stone on the board
			
			_eatenSet.clear();
			_tempEatFlag = false;//changed to true if Eat
			
			_emptySet.clear();
			callToCheckEaten(possibleMoveList.get(index).get_i(),
					possibleMoveList.get(index).get_j(), aiColor,tempBoardGame);
			
			if(_tempEatFlag) 
			{
				
				if(possibleMoveList.get(index).get_moveRating() == logic.Rating.regular) 
				{
					possibleMoveList.get(index).set_moveRating(logic.Rating.eat);//eat
				}
				else if(_eatenSet.size() > 6) 
				{
					possibleMoveList.get(index).set_moveRating(logic.Rating.eat);//eat
				}
				possibleMoveList.get(index).set_eatStones(_eatenSet.size());
			}
			//check suicide:
			_eatenSet.clear();
			_tempEatFlag = false;
			callToCheckEaten(possibleMoveList.get(index).get_i(),
					possibleMoveList.get(index).get_j(),
					enemyColorRegular,tempBoardGame);
			//check eaten on the enemyColors
			if(_tempEatFlag) 
			{
				if(possibleMoveList.get(index).get_moveRating() != Rating.OverGrowth &&
						possibleMoveList.get(index).get_moveRating() != Rating.eat)
				{
					possibleMoveList.get(index).set_moveRating(logic.Rating.Suicide);
				}
				
			}
			//done check suicide
			
			//check defence and max defence:
			
			tempBoardGame.getSquare(possibleMoveList.get(index).get_i()
					,possibleMoveList.get(index).get_j()).set_stoneColor(enemyColorRegular);//opposite color
			_eatenSet.clear();
			_tempEatFlag = false;	
			callToCheckEaten(possibleMoveList.get(index).get_i()
					,possibleMoveList.get(index).get_j(), enemyColorRegular,tempBoardGame);
			if(_tempEatFlag) 
			{
				possibleMoveList.get(index).set_defStones(_eatenSet.size());
				if(possibleMoveList.get(index).get_moveRating() == logic.Rating.regular) 
				{ 
					possibleMoveList.get(index).set_moveRating(logic.Rating.defence);//eat
				}
				else if(possibleMoveList.get(index).get_defStones() > possibleMoveList.get(index).get_eatStones() 
						&& possibleMoveList.get(index).get_moveRating() != logic.Rating.Suicide
						&& possibleMoveList.get(index).get_moveRating() != logic.Rating.OverGrowth) 
				{
					possibleMoveList.get(index).set_moveRating(logic.Rating.defence);
				}
				
			}
			tempBoardGame.get_matrix()[possibleMoveList.get(index).get_i()][possibleMoveList.get(index).get_j()]
					.set_stoneColor(EMPTY);
		 }
		
		maxDefIndex = 0;
		maxEatIndex = 0;
		for(index=1;index<possibleMoveList.size();index++) 
		{
			if(possibleMoveList.get(index).get_eatStones() > possibleMoveList.get(maxEatIndex).get_eatStones()) 
			{
				maxEatIndex = index;
			}
			if(possibleMoveList.get(index).get_defStones() > possibleMoveList.get(maxDefIndex).get_defStones()) 
			{
				maxEatIndex = index;
			}
		}
		//eat the most
		if(maxEatIndex != -1 &&possibleMoveList.get(maxEatIndex).get_moveRating() == logic.Rating.eat) 
		{
			possibleMoveList.get(maxEatIndex).set_moveRating(logic.Rating.eatTheMost);//eat
		}
		//max defence
		if(maxDefIndex!= -1 && possibleMoveList.get(maxDefIndex).get_moveRating() == logic.Rating.defence) 
		{
			possibleMoveList.get(maxDefIndex).set_moveRating(logic.Rating.defenceTheMost);
		}
		//check howMuchFrom eat
		for(i=0;i<9;i++) 
		{
			for(j=0;j<9;j++) 
			{
				if(_boardGame.getSquare(i, j).get_isReal()) 
				{
					if(_boardGame.getSquare(i, j).get_stoneColor() == enemyColorRegular 
							||_boardGame.getSquare(i, j).get_stoneColor() == enemyColorLight) 
					{
						_emptySet.clear();
						callToCheckEaten(i,j,teamColorRegular,_boardGame);
						Logic_Square[] emptyArray = _emptySet.toArray(
				        		new Logic_Square[_emptySet.size()]);
							
					        for(k=0;k<emptyArray.length;k++) 
					        {
					        	//run on the empty array/
					        	
					        	_emptySetIndex = getPossibleMovesRowACol
					        			(possibleMoveList,emptyArray[k].get_row(), emptyArray[k].get_col());
					        	
					        	if(_emptySetIndex == -1) 
				        		{
				        			
					        		System.out.println("problem evaluate line 667");
				        		}
					        	else if(possibleMoveList.get(_emptySetIndex).get_moveRating()==
					        			logic.Rating.regular ||possibleMoveList.get(_emptySetIndex).get_moveRating()==
					        			logic.Rating.nerbyTeam )
					        	{
					        		if(_emptySet.size() > 1 && _emptySet.size()<10) 
					        		{
						        		if(_emptySet.size()==2) 
						        		{
						        			possibleMoveList.get(_emptySetIndex).set_moveRating(Rating.oneFromEat);
						        		}
						        		else if(_emptySet.size()==3) 
						        		{
						        			possibleMoveList.get(_emptySetIndex).set_moveRating(Rating.twoFromEat);
						        		}
						        		else if(_emptySet.size()==4) 
						        		{
						        			possibleMoveList.get(_emptySetIndex).set_moveRating(Rating.threeFromEat);
						        		}
						        		else if(_emptySet.size()==5) 
						        		{
						        			possibleMoveList.get(_emptySetIndex).set_moveRating(Rating.fourFromEat);
						        		}
						        		else if(_emptySet.size()==6) 
						        		{
						        			possibleMoveList.get(_emptySetIndex).set_moveRating(Rating.fiveFromEat);
						        		}
						        		else if(_emptySet.size()==7) 
						        		{
						        		
						        			possibleMoveList.get(_emptySetIndex).set_moveRating(Rating.sixFromEat);
						        		}
						        		else if(_emptySet.size()==8) 
						        		{
						    
						        			possibleMoveList.get(_emptySetIndex).set_moveRating(Rating.sevenFromEat);
						        		}
						        		else if(_emptySet.size()==9) 
						        		{
						        			
						        			possibleMoveList.get(_emptySetIndex).set_moveRating(Rating.eightFromEat);
						        		}
					        		}
					        		else 
									{
					 
										possibleMoveList.get(_emptySetIndex).set_moveRating(Rating.nerbyenemy);
									}
								}
					        	
					        }
					        
					}
					//checkoneFromDefence
					else if(_boardGame.getSquare(i, j).get_stoneColor() == teamColorRegular 
							||_boardGame.getSquare(i, j).get_stoneColor() == teamColorLight)
					{
						_emptySet.clear();
						callToCheckEaten(i,j,enemyColorRegular,_boardGame);
						Logic_Square[] emptyArray = _emptySet.toArray(
				        		new Logic_Square[_emptySet.size()]);
						 for(k=0;k<emptyArray.length;k++) 
						 {
							 _emptySetIndex = getPossibleMovesRowACol
					        			(possibleMoveList,emptyArray[k].get_row(), emptyArray[k].get_col());
					        	if(_emptySetIndex == -1) 
				        		{
				        			System.out.println("problem evaluate line 735");
				        		}
					        	else if(_emptySet.size() == 2) 
					        	{
					        		possibleMoveList.get(_emptySetIndex).set_moveRating(Rating.oneFromDefence);
					        	}
						 }
						 //check nerbyTeam
						 nerbyTeamChangeRating(possibleMoveList,getPossibleMovesRowACol(possibleMoveList,i-1, j-1));
						 nerbyTeamChangeRating(possibleMoveList,getPossibleMovesRowACol(possibleMoveList,i-1, j));
						 nerbyTeamChangeRating(possibleMoveList,getPossibleMovesRowACol(possibleMoveList,i, j+1));
						 nerbyTeamChangeRating(possibleMoveList,getPossibleMovesRowACol(possibleMoveList,i+1, j+1));
						 nerbyTeamChangeRating(possibleMoveList,getPossibleMovesRowACol(possibleMoveList,i+1, j));
						 nerbyTeamChangeRating(possibleMoveList,getPossibleMovesRowACol(possibleMoveList,i, j-1));
						 
					}
				}
			}
		}
	}
	public Logic_Move getBestMove(LinkedList<Logic_Move> possibleList) 
	{
		/**
		* discrioption:returns the best move from the list by the rating
		* @param possibleList the list we will work on.
		* @return bestMove
		* @rtype Logic_Move
		*/
		LinkedList<Logic_Move> bestMoves = new LinkedList<>();
		if(possibleList.size()<1) 
		{
			return null;
		}
		Logic_Move bestMove = possibleList.getFirst();
		int index,i,j;
		Square._soldierColor color,teamColor;
		for(index=1;index<possibleList.size();index++) 
		{
			if(Logic_Move.cmpBetweenRatings(possibleList.get(index).get_moveRating(),
					bestMove.get_moveRating()) == possibleList.get(index).get_moveRating()) 
			{
				bestMove = possibleList.get(index);
			}
		}
		
		if(bestMove.get_defStones() > bestMove.get_eatStones()) 
		{
			bestMove.set_moveRating(Rating.defenceTheMost);
		}//calculate bestMove Rating
		//add to list everyone who is like bestMove
		
		
		return bestMove;
	}
	public logic.Rating evalOverGrowth(LinkedList<Logic_Move> _possibleMoves
			,graphic.Square._soldierColor aiColor,int index,Logic_Board tempBoardGame) 
	{
		/**
		* discrioption:calculate the rating if we put the move and if it will cause 
		* to overGrowth. part from the evaluate
		* @param _possibleMoves, the list
		* @param aiColor, the color
		* @param index, the index in the list
		* @param tempBoardGame, the board
		* @return Rating
		* @rtype logic.Rating
		*/
		tempBoardGame.get_matrix()[_possibleMoves.get(index).get_i()][_possibleMoves.get(index).get_j()]
				.set_stoneColor(aiColor);
		_overGrowthSet.clear();
		checkOverGrowth(_possibleMoves.get(index).get_i()
				,_possibleMoves.get(index).get_j(),aiColor,tempBoardGame);
		tempBoardGame.get_matrix()[_possibleMoves.get(index).get_i()][_possibleMoves.get(index).get_j()]
				.set_stoneColor(EMPTY);
		if(_overGrowthSet.size() > 5) 
		{
			return logic.Rating.OverGrowth;
		}
		return logic.Rating.regular;      
	}
	
	public void buildTree(int depth,Node node,boolean isComp
			,boolean isFirstTurn) 
	{
		/**
		* discrioption:building the tree, than evaluate the sons so the father
		* will get the best son. so the root have the best move
		* @param depth, the depth of the tree
		* @param node, current node to work on
		* @param board, the board to work on
		* @param isComp, if it's computer turn
		* @param isFirstTurn, if It's first turn
		* @return void
		*/
		Square._soldierColor turnColor,lightColor,regularColor;
		
		Node son;
		int index,j;
		int i;
		Logic_Move bestMove;
		if(depth == 0)
			return;
		
		if(isComp) {
			lightColor = LIGHTRED;
			regularColor = RED;
			turnColor = RED;
		}
		else {
			lightColor = LIGHTBLUE;
			regularColor = BLUE;
			turnColor = BLUE;
		}
		if(isFirstTurn)//if it's the first turn
		{
			_lastTurnColorAi = EMPTY;
			_countTurnAi = 2;
		}
		else //if it's the second
		{
			//_lastTurnColorAi stayes as he is.
			_countTurnAi = 1;
		}
		if(node.get_move() != null) 
		{
			_tempBoardGame.getSquare(node.get_move().get_i(),
					node.get_move().get_j())
			.set_stoneColor(node.get_move().get_color());
		}
		_tempMoves = allPossibleMoves(_tempBoardGame,turnColor);
		if(turnColor == RED)
			evaluate(_tempMoves, RED, LIGHTRED, BLUE, LIGHTBLUE,_tempBoardGame);
		else if(turnColor == BLUE)
			evaluate(_tempMoves,BLUE,LIGHTBLUE,RED, LIGHTRED,_tempBoardGame);
		
		_countTurnAi = 1;
		bestMove = getBestMove(_tempMoves);
		//loop add the sons:RED
		for(index = 0;index<_tempMoves.size();index++) 
		{
				if(_tempMoves.get(index).get_moveRating()
						== bestMove.get_moveRating()) 
				{
					node.addChild(new Node(_tempMoves.get(index)));
				}
		}
		
		//loop on the sons:LIGHTRED
		if(isFirstTurn) 
		{
			for(index = 0;index<node.get_possibleMoves().size();index++) 
			{
				son = node.get_possibleMoves().get(index);
				_tempBoardGame.getSquare(son.get_move().get_i(), son.get_move().get_j())
						.set_stoneColor(son.get_move().get_color());
				_lastTurnColorAi = son.get_move().get_color();
				_tempMoves.clear();
				_tempMoves = allPossibleMoves(_tempBoardGame, turnColor);
				if(turnColor == RED)
					evaluate(_tempMoves, RED, LIGHTRED, BLUE, LIGHTBLUE,_tempBoardGame);
				else if(turnColor == BLUE)
					evaluate(_tempMoves,BLUE,LIGHTBLUE,RED, LIGHTRED,_tempBoardGame);
				bestMove = getBestMove(_tempMoves);
				if(bestMove!= null) {
					for(j = 0;j<_tempMoves.size();j++) 
					{
						if(_tempMoves.get(j).get_moveRating()
							== bestMove.get_moveRating()) 
						{
							node.get_possibleMoves().get(index).addChild(new Node(_tempMoves.get(j)));
							buildTree(depth-1,node.get_possibleMoves().get(index).get_possibleMoves().getLast(), !isComp,true);
						}
					}
				}
					_tempBoardGame.getSquare(son.get_move().get_i()
						, son.get_move().get_j())
						.set_stoneColor(EMPTY);
				
			}
		}
		else 
		{
			for(index = 0;index<node.get_possibleMoves().size();index++) 
			{
				buildTree(depth-1, node.get_possibleMoves().get(index), !isComp,true);
			}
		}
		if(node.get_move() != null)
			_tempBoardGame.getSquare(node.get_move().get_i()
					, node.get_move().get_j())
					.set_stoneColor(EMPTY);
		
		_tempMoves.clear();
		for(index = 0;index < node.get_possibleMoves().size();index++) 
		{
			
			_tempMoves.add( node.get_possibleMoves().get(index).get_move());
		}
		if(node.get_move() != null && _tempMoves.size() > 0 )
			node.get_move().set_moveRating(getBestMove(_tempMoves).get_moveRating());
		
		if(node == _tree.get_root() && _tempMoves.size() > 0) 
		{
			_tree.get_root().set_move(getBestMove(_tempMoves));
			
		}
	}
	
	
	public Logic_Move ai(Square._soldierColor aiColor) 
	{
		/**
		* discrioption:call to build tree function and put the best move in board
		* , also return it
		* @param aiColor, the color
		* @param aiLevel, the level of the ai
		* @return the bestMove
		* @rtype Logic_Move
		*/
		_tree = new Tree();
		_soldierColor tempLastTurn;
		int tempCurrent;
		
		//tree:
		
		_tempBoardGame.set_matrix(_boardGame.get_matrix());
		tempLastTurn = _lastTurnColorAi;
		tempCurrent = _countTurnAi;

		if(_countTurnAi == 2)
			buildTree(BoardFrame._aiLevel, _tree.get_root(),true,true);
		else if(_countTurnAi == 1)
			buildTree(BoardFrame._aiLevel, _tree.get_root(),true,false);

		_lastTurnColorAi = tempLastTurn;
		_countTurnAi = tempCurrent;
		
		Logic_Move bestMove = new Logic_Move(0,0,Rating.regular,RED);
		//_possibleMoves.clear();
		//_possibleMoves = allPossibleMoves(_boardGame,aiColor);
		if(_tree.get_root().get_move()!=null)
			bestMove = _tree.get_root().get_move();
		else 
		{
			_possibleMoves.clear();
			_possibleMoves = allPossibleMoves(_boardGame, RED);
			evaluate(_possibleMoves, RED, LIGHTRED, BLUE, LIGHTBLUE, _boardGame);
			bestMove = getBestMove(_possibleMoves);
		}
		_boardGame.	getSquare(bestMove.get_i(), bestMove.get_j()).set_stoneColor(bestMove.get_color());
		doGameRules(bestMove.get_i(), bestMove.get_j(), bestMove.get_color());
		BoardFrame.getSquare(bestMove.get_i(),bestMove.get_j()).set_stoneColor(bestMove.get_color());
		BoardFrame.getSquare(bestMove.get_i(),bestMove.get_j()).repaint();
		_countTurnAi = 3-_countTurnAi;
		_lastTurnColorAi = bestMove.get_color();
		return bestMove;
		
	}
	public void doGameRules(int row,int col,Square._soldierColor currentColor) 
	{
		/**
		* discrioption:do the game rules
		* @param row,the col in the matrix
		* @param col,the row in the matrix
		* @param currentColor
		* @return void
		*/
		Square._soldierColor otherColor = EMPTY;
		_eatenSet.clear();
		set_findEmpty(false);
		callToCheckEaten(row,col,currentColor,_boardGame);
		_possibleMoves.clear();
		_overGrowthSet.clear();
		checkOverGrowth(row, col, currentColor, _boardGame);
		doOverGrowth(_boardGame);
		if(currentColor == BLUE || currentColor == LIGHTBLUE)
			otherColor = RED;
		else if(currentColor == RED || currentColor == LIGHTRED)
			otherColor = BLUE;
		_eatenSet.clear();
		set_findEmpty(false); 
		callToCheckEaten(row,col,otherColor,_boardGame);
	}
	
	public int getPossibleMovesRowACol(LinkedList<Logic_Move> _possibleMoves,int row,int col) 
	{
		/**
		* discrioption:returns the index in the _possibleMoves by the row and the col.
		* @param _possibleMoves
		* @param row in the matrix
		* @param col in the matrix
		* @return the index in the _possibleMoves by the row and the col. 
		* @rtype int
		*/
		int i;
		for(i=0;i<_possibleMoves.size();i++) 
		{
			if(_possibleMoves.get(i).get_i() == row && _possibleMoves.get(i).get_j() == col)
				return i;
		}
		return -1;
	}
	public void nerbyTeamChangeRating(LinkedList<Logic_Move> _possibleMoves,int indexNerbyRed) 
	{
		/**
		* discrioption: check if the move is nerby team,change the rating
		* @param _possibleMoves
		* @param indexNerbyRed
		* @return void
		*/
		 if(indexNerbyRed != -1) 
		 {
			 if(_possibleMoves.get(indexNerbyRed).get_moveRating() == Rating.regular) 
			 {
				 _possibleMoves.get(indexNerbyRed).set_moveRating(Rating.nerbyTeam);
			 }
		 }
	}

	public Logic_Player get_player1() {
		return _player1;
	}
	public Logic_Player get_player2() {
		return _player2;
	}

	public Logic_Board get_boardGame() {
		return _boardGame;
	}

	public HashSet<Logic_Square> get_overGrowthSet() {
		return _overGrowthSet;
	}

	public HashSet<Logic_Square> get_eatenSet() {
		return _eatenSet;
	}

	public boolean get_findEmpty() {
		return _findEmpty;
	}

	public void set_findEmpty(boolean _findEmpty) {
		this._findEmpty = _findEmpty;
	}

	public boolean get_isWin() {
		return _isWin;
	}

	public void set_isWin(boolean _isWin) {
		this._isWin = _isWin;
	}

	public graphic.Square._soldierColor getBLUE() {
		return BLUE;
	}

	public graphic.Square._soldierColor getLIGHTBLUE() {
		return LIGHTBLUE;
	}

	public graphic.Square._soldierColor getRED() {
		return RED;
	}

	public graphic.Square._soldierColor getLIGHTRED() {
		return LIGHTRED;
	}

	public graphic.Square._soldierColor getEMPTY() {
		return EMPTY;
	}

	

	public LinkedList<Logic_Move> get_possibleMoves() {
		return _possibleMoves;
	}

	public void set_possibleMoves(LinkedList<Logic_Move> _possibleMoves) {
		this._possibleMoves = _possibleMoves;
	}
}
