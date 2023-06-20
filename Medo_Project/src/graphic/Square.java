package graphic;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import logic.*;
public class Square extends JPanel
{
	public enum _soldierColor
	{
		RED,
		LIGHTRED,
		BLUE,
		LIGHTBLUE,
		HOVER,
		EMPTY
	};
	private int _row;//index of row in the matrix
	private int _col;//index of the col in the matrix
	private _soldierColor _stoneColor;//current stonecolor 
	private boolean _isReal;//if the stone is real or not
	private int _midX;//x cordinate of the middle
	private int _leftx;//x cordinate of the left
	private int _rightX;//x cordinate of the right
	private int _midTopY;//y cordinate of the top y middle
	private int _midBotY;//y cordinate of the bottom y middle
	private int _topY;//y cordinate of the top 
	private int _botY;//y cordinate of the bottom
	private BoardFrame _bF;//connect to the boardframe
	private graphic.Square._soldierColor BLUE;//colors from the logic enum
	private graphic.Square._soldierColor LIGHTBLUE;
	private graphic.Square._soldierColor RED;
	private graphic.Square._soldierColor LIGHTRED;
	private graphic.Square._soldierColor _currentColor;
	
	//private static logic.GameManagement _gameM = new logic.GameManagement();
	
	public Square(int row, int col,_soldierColor sColor, boolean isEmpty,
			boolean isReal,BoardFrame bF) 
	{
		int aiindIcator;
		setLayout(null);
		_row = row;
		_col = col;
		_bF = bF;
		
		_currentColor = _bF.getLogicGame().getEMPTY();
		_stoneColor = sColor;
		BLUE = _bF.getLogicGame().getBLUE();
		LIGHTBLUE = _bF.getLogicGame().getLIGHTBLUE();
		RED = _bF.getLogicGame().getRED();
		LIGHTRED = _bF.getLogicGame().getLIGHTRED();
		//_isEmpty = isEmpty;
		_isReal = isReal;
		_midX = 60;
		_leftx = 20;
		_rightX = 100;
		_topY = 20;
		_midTopY = 40;
		_midBotY = 90;
		_botY = 110;
		setOpaque(false);
		setSize(100,100);
		
		setBackground(new Color(0,0,0,0));
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e)//this is the moused pressed
			{
				if(_stoneColor == _soldierColor.HOVER )//if stand already on the stone
				{
					if(_bF.getCountTurn() == 1)//if you got only 1 turn to do
	        		{
	            			if (BoardFrame.getTurn()) //when turn is  true it's blue turn
	            			{
	            				if(_bF.getLastTurn() == _soldierColor.BLUE) {
	            					//if the last turn was blue it must be now light blue
	            					_stoneColor = _soldierColor.LIGHTBLUE;
	            					_currentColor = LIGHTBLUE;
	            					
	            					_bF.getLogicGame().get_boardGame()
	            						.getSquare(_row, _col).set_stoneColor(LIGHTBLUE);
	            					//change the logic board
	            				}
	            				else {
	            					//the last color was light blue so current
	            					//color must be regular blue
	            					_stoneColor = _soldierColor.BLUE;
	            					_currentColor = BLUE;
	            					_bF.getLogicGame().get_boardGame()
	            						.getSquare(_row, _col).set_stoneColor(BLUE);
	            				}
	            			}
	            			else 
	            			{
	            				if(_bF.getLastTurn() == _soldierColor.RED)
	            					//if last turn was red current color must be light red
	            				{ 
	            					_currentColor = LIGHTRED;
	            					_stoneColor = _soldierColor.LIGHTRED;
	            					_bF.getLogicGame().get_boardGame()
	            						.getSquare(_row, _col).set_stoneColor(LIGHTRED);

	            				}
	            				else {//if last color was lightred current color must be red
	            					_stoneColor = _soldierColor.RED;
	            					_currentColor = RED;
	            					_bF.getLogicGame().get_boardGame()
	            						.getSquare(_row, _col).set_stoneColor(RED);

	            				}
	            			}
	            			BoardFrame.setTurn(!BoardFrame.getTurn());
						//BoardFrame.logicGame._turn = !BoardFrame.logicGame._turn;
	            			_bF.setCountTurn(2);
	            			//after this turn the other player got 2 plays to made
					}
					else 
					{
						if(BoardFrame.getTurn())//check if it's the blue turn or red turn
						{
							if(e.getX()<60 )//check if the player clicked on the left side
								//left side is regular blue
								//right side is light blue
							{
								_stoneColor = _soldierColor.BLUE;
								_currentColor = BLUE;
								_bF.getLogicGame().get_boardGame()
									.getSquare(_row, _col).set_stoneColor(BLUE);
								_bF.setLight(true);
							}
							else
							{
								_stoneColor = _soldierColor.LIGHTBLUE;
								_currentColor = LIGHTBLUE;
								_bF.getLogicGame().get_boardGame()
									.getSquare(_row, _col).set_stoneColor(LIGHTBLUE);
								_bF.setLight(false);
							}
						}
						else 
						{ 
							if(e.getX()<60 ) 
							{
								//same as blue turn, left for regular red,left for light red.
								_stoneColor = _soldierColor.RED;
								_currentColor = RED;
								_bF.getLogicGame().get_boardGame()
									.getSquare(_row, _col).set_stoneColor(RED);
								_bF.setLight(true);
							}
							else {
								_stoneColor = _soldierColor.LIGHTRED;
								_currentColor = LIGHTRED;
								_bF.getLogicGame().get_boardGame()
									.getSquare(_row, _col).set_stoneColor(LIGHTRED);
								_bF.setLight(false);
							}
						}
						_bF.setCountTurn(1);// after that you got only 1 play to made
					}
					_bF.setText();//change the turns color
					_bF.setLastTurn(_stoneColor);
					repaint();
				}
				_bF.get_beforeTheLastTurn().setText(_bF.get_theLastTurn().getText());
				_bF.get_theLastTurn().setText(_currentColor+":"+" "+_row+","+_col);
				if(_bF.get_isAi() && BoardFrame.getTurn() == false) 
				{
					//change to board
					//change the if to true if you want to check it on blue
					//always play 2 times
					logic.Logic_Move aiMove,aiSecondMove;
					aiMove = _bF.getLogicGame().ai(RED);//changeto blue if you want to check;
					aiSecondMove = _bF.getLogicGame().ai(RED);//changeto blue if you want to check;
					
					_bF.get_theLastTurn().setText(aiSecondMove.get_color()//last turns
							+":"+" "+aiSecondMove.get_i()+","+aiSecondMove.get_j());
					_bF.get_beforeTheLastTurn().setText(aiMove.get_color()
							+":"+" "+aiMove.get_i()+","+aiMove.get_j());
					
					_bF.setLastTurn(aiSecondMove.get_color());
					_bF.setCountTurn(2);
					BoardFrame.setTurn(!BoardFrame.getTurn());
					_bF.setText();
					
					repaint();
					
				}
				
				
				_bF.getLogicGame().doGameRules(_row, _col, _currentColor);
				//repaint();b 
				_bF._pointsAText.setText(_bF.getLogicGame().get_player1().get_points() + "/20");
				_bF._pointsBText.setText(_bF.getLogicGame().get_player2().get_points() + "/20");
				
				_bF.checkWin();
				

		}});
		addMouseMotionListener(new MouseAdapter() 
		{
			public void mouseMoved(MouseEvent e) {
				if((e.getY() < -0.5*e.getX() + 140 && e.getY() < 0.5*e.getX() + 80) &&
						e.getY() > 0.5*e.getX() -10 && e.getY() > 0.5*e.getX() -50)
				{//change the color to Hover
					if(_stoneColor == _soldierColor.EMPTY) {
						_stoneColor = _soldierColor.HOVER;
						setOpaque(false);
					}
				}
				else {//change to Empty from hover if not in the indexes
					if(_stoneColor == _soldierColor.HOVER)
					{
						_stoneColor = _soldierColor.EMPTY;
						setOpaque(false);
					}
				}
				repaint();

			}
		});
		addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) 
			{//change from 
				if(_stoneColor == _soldierColor.HOVER)
				{
					_stoneColor = _soldierColor.EMPTY;
					setOpaque(false);
					repaint();
				}
			}
		});
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		if(_isReal) 
		{
			super.paintComponent(g);
			Color blue = Color.blue;
			Color lightBlue = new Color(51,204,255);
			Color red = Color.red;
			Color lightRed = new Color(255,102,102);
			if(StartGame.getComboIndexC() == 1) 
			{
				blue =new Color(92, 64, 51);
				lightBlue = new Color(160,82,45);
				red = Color.gray;
				lightRed = red.brighter();
			}
			
			Color empty = new Color(230, 193, 148);
			int[] xPoints = {_leftx  ,_midX,_rightX ,_rightX ,_midX,_leftx  };
			int[] yPoints = {_midBotY,_botY,_midBotY,_midTopY,_topY,_midTopY};
			int[] xPointsHalfA = {_midX,_leftx,_leftx,_midX};
			int[] yPointsHalfA = {_topY,_midTopY,_midBotY,_botY};
			int[] xPointsHalfB = {_midX,_rightX,_rightX,_midX};
			int[] yPointsHalfB = {_topY,_midTopY,_midBotY,_botY};
        	if(_stoneColor == _soldierColor.EMPTY) 
        	{
        		g.setColor(empty);
        		g.fillPolygon(xPoints, yPoints, xPoints.length);
        		g.setColor(Color.black);
    			g.drawPolygon(xPoints, yPoints, xPoints.length);
    			String index = _row+","+_col;
            	g.drawString(index,50 ,_midTopY+30);//draw the indexes on each square
        	}
        	else if(_stoneColor == _soldierColor.HOVER )
        	{
        		if(_bF.getCountTurn() == 1)//if you got only 1 play left
        		{
            			if (BoardFrame.getTurn()) //if it's player 1 turn
            			{
            				if(_bF.getLastTurn() == _soldierColor.BLUE)
            					//if last color was blue the next color must be light blue
            					g.setColor(lightBlue);
            				else
            					g.setColor(blue);
            			}
            			else 
            			{
            				if(_bF.getLastTurn() == _soldierColor.RED)
            					//if last color was red the next color must be lightred
            					g.setColor(lightRed);
            				else
            					g.setColor(red);
            			}
            			g.fillPolygon(xPoints, yPoints, xPoints.length);
            			g.setColor(Color.black);
            			g.drawPolygon(xPoints, yPoints, xPoints.length);
        		}
        		else {// you got 2 plays to made, if turn is player1 half square will
        			//be blue and half light blue
        			//if it's player2 turn the square will be half red and helf lightred.
        			if (BoardFrame.getTurn()) 
        				g.setColor(blue);
        			else 
        				g.setColor(red);
        			g.fillPolygon(xPointsHalfA, yPointsHalfA, xPointsHalfA.length);
        			if (BoardFrame.getTurn()) 
        				g.setColor(lightBlue);	
        			else
        				g.setColor(lightRed);
        			g.fillPolygon(xPointsHalfB, yPointsHalfB, xPointsHalfB.length);
        			g.setColor(Color.black);
        			g.drawPolygon(xPoints, yPoints, xPoints.length);
        			
        		}
        	}
        	else 
        	{
	        	 if(_stoneColor == _soldierColor.BLUE)
	        	{
	        		g.setColor(blue);
		        }
		        else if(_stoneColor == _soldierColor.RED) 
		        {		
		        	g.setColor(red);		
		        }
		        else if(_stoneColor == _soldierColor.LIGHTBLUE) 
		        {
		        	g.setColor(lightBlue);
		        	
		        }
		        else if(_stoneColor == _soldierColor.LIGHTRED) 
		        {
		        	g.setColor(lightRed);
		        }
		        g.fillPolygon(xPoints, yPoints, xPoints.length);
		    	g.setColor(Color.black);
		    	g.drawPolygon(xPoints, yPoints, xPoints.length);
        	}
        	
        }	
	}
	public _soldierColor get_stoneColor() {
		return _stoneColor;
	}
	public void set_stoneColor(_soldierColor _stoneColor) {
		this._stoneColor = _stoneColor;
	}
	public int get_leftx() {
		return _leftx;
	}
	public void set_leftx(int _leftx) {
		this._leftx = _leftx;
	}
	public int get_rightX() {
		return _rightX;
	}
	public void set_rightX(int _rightX) {
		this._rightX = _rightX;
	}
	public int get_midBotY() {
		return _midBotY;
	}
	public void set_midBotY(int _midBotY) {
		this._midBotY = _midBotY;
	}
	public int get_topY() {
		return _topY;
	}
	public void set_topY(int _topY) {
		this._topY = _topY;
	}
	@Override
	public String toString() {
		return "Square [_row=" + _row + ", _col=" + _col + ", _stoneColor=" + _stoneColor + "]";
	}
	
	
}
