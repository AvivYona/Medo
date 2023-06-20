package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import graphic.Square._soldierColor;
import logic.Logic_GameManagement;


public class BoardFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;//remove the error
	private static boolean turn = true;//when it's true it's the player1 turn
	private int countPlays = 1;//can be 1 or it's how much plays you got left
	private boolean light = false;//if the current color is light or not
	private _soldierColor lastTurn;//the last turn
	private static JLabel tTurn;//player 1 or 2
	private Logic_GameManagement logicGame = new Logic_GameManagement();
	private static Square[][] _board;
	private JButton _resetGame;
	private JButton _homePage;
	private JButton _startMusic;
	private JButton _nextSong;
	private JButton _previousSong;
	private JButton _volumeUp;
	private JButton _volumeDown;
	private FloatControl _gainControl;
	private JLabel _title;
	private JLabel _musicLabel;
	private JLabel _musicSoundLabel;
	private JTextArea _playersAT;
	private JTextArea _playersBT;
	public JTextArea _pointsAText;
	public JTextArea _pointsBText;
	public JTextArea _lastTurns;
	public JTextArea _theLastTurn;
	public JTextArea _beforeTheLastTurn;
	public JLabel _turnText;
	public LinkedList<File> _songs;
	private boolean _isAi;
	public static int _aiLevel;
	public int _songsIndex;
	public Clip _songClip;
	Color blue;
	Color red;
	private int _pointsA;
	private int _pointsB;
	public BoardFrame(boolean ai)
	{
		_isAi = ai;
		Color backGround = new Color(211, 169, 122);
		Color button = new Color(235, 213, 189);
		Color pointsC = new Color(235, 213, 189);
		Font pfont = new Font("Arial", Font.BOLD, 24);	
		Font bfont = new Font("Arial", Font.BOLD, 36);	
		int aiindIcator;
		aiindIcator =StartGame.getComboIndexB();
		if(aiindIcator == 0)
			_aiLevel = 1;
		if(aiindIcator == 1)
			_aiLevel = 3;
		if(aiindIcator == 2)
			_aiLevel = 5;
		
		_songs = new LinkedList<>();
		_songs.add(new File("Avatar_AgniKai.wav"));
		_songs.add(new File("Fluffing-a-Duck.wav"));
		_songs.add(new File("Grass Skirt Chase.wav"));
		_songs.add(new File("Monkeys-Spinning-Monkeys.wav"));
		_songs.add(new File("Powerful-Trap-.wav"));
		AudioInputStream audioStream = null;
		_songsIndex = 0;
		try {
			audioStream = AudioSystem.getAudioInputStream(_songs.get(_songsIndex));
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			_songClip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		try {
			_songClip.open(audioStream);
		} catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_gainControl = (FloatControl) _songClip.getControl(FloatControl.Type.MASTER_GAIN);
		
		if(StartGame.getComboIndexC() == 0) 
		{
			blue = Color.blue;
			red = Color.red;
		}
		else if(StartGame.getComboIndexC() == 1) 
		{
			blue =new Color(92, 64, 51);
			red = Color.gray;
		}
		
		_board = new Square[9][9];
		_pointsA = 0;
		_pointsB = 0;
		setLayout(null);
		
		_turnText = new JLabel("Turn:");
		_turnText.setForeground(Color.black);
		_turnText.setBounds(00, 100, 300, 200);
		_turnText.setFont(new Font("Arial",Font.PLAIN,48));
		add(_turnText);
		
		tTurn = new JLabel("Player 1");
		tTurn.setForeground(blue);
		tTurn.setBounds(00, 150, 300, 200);
		tTurn.setFont(new Font("Serif", Font.PLAIN, 48));
		
		add(tTurn);
		
		_title = new JLabel("Medo Game");
		_title.setBounds(610,50,500,100);
		_title.setForeground(Color.black);
		_title.setFont(new Font("Arial",Font.BOLD,72));
		add(_title);
		
		_resetGame = new JButton("RESET");
		_resetGame.setBackground(button);
		_resetGame.setForeground(Color.black);
		_resetGame.setBounds(0, 500, 250,100);
		_resetGame.setFont(bfont);
		_resetGame.addActionListener(this);
		add(_resetGame);
		
		_homePage = new JButton("Home");
		_homePage.setBackground(button);
		_homePage.setForeground(Color.black);
		_homePage.setBounds(0, 600, 250, 100);
		_homePage.setFont(bfont);
		_homePage.addActionListener(this);
		add(_homePage);
		
		_musicLabel = new JLabel("MUSIC");
		_musicLabel.setBounds(1440,600,200,50);
		_musicLabel.setForeground(Color.black);
		_musicLabel.setFont(new Font("Arial",Font.BOLD,36));
		add(_musicLabel);
		
		_musicSoundLabel = new JLabel("Sound:");
		_musicSoundLabel.setBounds(1300,800,400,50);
		_musicSoundLabel.setForeground(Color.black);
		_musicSoundLabel.setFont(new Font("Arial",Font.BOLD,24));
		add(_musicSoundLabel);
		
		_startMusic = new JButton("Start Music");
		_startMusic.setBackground(button);
		_startMusic.setForeground(Color.black);
		_startMusic.setBounds(1300,650,400,50);
		_startMusic.setFont(pfont);
		_startMusic.addActionListener(this);
		add(_startMusic);
		
		_nextSong = new JButton("Next");
		_nextSong.setBackground(button);
		_nextSong.setForeground(Color.black);
		_nextSong.setBounds(1500,700,200,50);
		_nextSong.setFont(pfont);
		_nextSong.addActionListener(this);
		add(_nextSong);
		
		
		_previousSong = new JButton("Previous");
		_previousSong.setBackground(button);
		_previousSong.setForeground(Color.black);
		_previousSong.setBounds(1300,700,200,50);
		_previousSong.setFont(pfont);
		_previousSong.addActionListener(this);
		add(_previousSong);
		
		_volumeUp = new JButton("Volume Up");
		_volumeUp.setBackground(button);
		_volumeUp.setForeground(Color.black);
		_volumeUp.setBounds(1500,750,200,50);
		_volumeUp.setFont(pfont);
		_volumeUp.addActionListener(this);
		add(_volumeUp);
		
		_volumeDown = new JButton("Volume Down");
		_volumeDown.setBackground(button);
		_volumeDown.setForeground(Color.black);
		_volumeDown.setBounds(1300,750,200,50);
		_volumeDown.setFont(pfont);
		_volumeDown.addActionListener(this);
		add(_volumeDown);
		
		_nextSong.setVisible(false);
		_previousSong.setVisible(false);
		_volumeDown.setVisible(false);
		_volumeUp.setVisible(false);
		_musicSoundLabel.setVisible(false);
		
		
		_playersAT = new JTextArea();
		_playersAT.setBounds(1400, 100, 300, 50);
		_playersAT.setEditable(false);
		_playersAT.setBackground(pointsC);
		_playersAT.setFont(pfont);
		_playersAT.setForeground(blue);
		_playersAT.setText("Player 1:");
		add(_playersAT);
		
		_pointsAText = new JTextArea();
		_pointsAText.setBounds(1400, 150, 300, 50);
		_pointsAText.setEditable(false);
		_pointsAText.setBackground(pointsC);
		_pointsAText.setFont(pfont);
		_pointsAText.setForeground(Color.black);
		_pointsAText.setText(_pointsA + "/20");
		add(_pointsAText);
		
		
		_playersBT = new JTextArea();
		_playersBT.setBounds(1400, 250, 300, 50);
		_playersBT.setEditable(false);
		_playersBT.setBackground(pointsC);
		_playersBT.setFont(pfont);
		_playersBT.setForeground(red);
		_playersBT.setText("Player 2:");
		add(_playersBT);
		
		_pointsBText = new JTextArea();
		_pointsBText.setBounds(1400, 300, 300, 50);
		_pointsBText.setEditable(false);
		_pointsBText.setBackground(pointsC);
		_pointsBText.setFont(pfont);
		_pointsBText.setForeground(Color.black);
		_pointsBText.setText(_pointsB + "/20");
		add(_pointsBText);
		
		_lastTurns = new JTextArea();
		_lastTurns.setBounds(1400,400,300,50);
		_lastTurns.setBackground(pointsC);
		_lastTurns.setFont(pfont);
		_lastTurns.setForeground(Color.black);
		_lastTurns.setEditable(false);
		_lastTurns.setText("Last Turns:");
		add(_lastTurns);
		
		_theLastTurn = new JTextArea();
		_theLastTurn.setBounds(1400,450,300,50);
		_theLastTurn.setBackground(pointsC);
		_theLastTurn.setFont(pfont);
		_theLastTurn.setForeground(Color.black);
		_theLastTurn.setEditable(false);
		_theLastTurn.setText("");
		add(_theLastTurn);
		
		_beforeTheLastTurn = new JTextArea();
		_beforeTheLastTurn.setBounds(1400,500,300,50);
		_beforeTheLastTurn.setBackground(pointsC);
		_beforeTheLastTurn.setFont(pfont);
		_beforeTheLastTurn.setForeground(Color.black);
		_beforeTheLastTurn.setEditable(false);
		_beforeTheLastTurn.setText("");
		add(_beforeTheLastTurn);
		
		
		for(int i=0;i<9;i++) 
		{
			for(int j=0;j<9;j++) 
			{
				
				if((i == 0 && j>4) || (i == 8 && j < 4)
						|| (i == 1 && j>5) || (i == 7 && j < 3) 
						|| (i == 2 && j>6) || (i == 6 && j < 2)
						|| (i == 3 && j>7) || (i == 5 && j < 1))
					_board[i][j] = new Square(i, j, _soldierColor.EMPTY,false,false,this);//not real
				else
					_board[i][j] = new Square(i, j, _soldierColor.EMPTY,false,true,this);//real
				int width = _board[i][j].get_rightX() - _board[i][j].get_leftx();
				int height = _board[i][j].get_midBotY() - _board[i][j].get_topY();
				_board[i][j].setBounds(j*width+600 - width/2 *(i), i*height + 150, 100, 110);
				//DrawLine
				
				add(_board[i][j]);
			}
		}
		reset();

		getContentPane().setBackground(backGround);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	}
	
	public void setText() 
    {
    	
    	if(BoardFrame.turn) 
    	{
    		tTurn.setForeground(blue);
    		tTurn.setText("Player 1");
    	}
    	else 
    	{
    		tTurn.setForeground(red);
    		tTurn.setText("Player 2");
    	}
    }
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == _resetGame) 
		{
			reset();
			
		}
		else if (e.getSource() == _homePage)
		{
			_songClip.close();
			new HomePage().setVisible(true);
			dispose();
		}
		else if(e.getSource() == _startMusic)
		{
			if(_startMusic.getText() == "Start Music")
			{
				_songClip.start();
				_songClip.loop(Clip.LOOP_CONTINUOUSLY);
				_nextSong.setVisible(true);
				_previousSong.setVisible(true);
				_volumeDown.setVisible(true);
				_volumeUp.setVisible(true);
				_musicSoundLabel.setVisible(true);
				_gainControl.setValue(-15f);
				_musicSoundLabel.setText("Sound:" + (int)(_gainControl.getValue()+80f) + "/85");
				//_songClip.loop(Clip.LOOP_CONTINUOUSLY);
				_startMusic.setText("Stop Music");
			}
			else if(_startMusic.getText() == "Stop Music")
			{
				_songClip.stop();
				_songClip.setMicrosecondPosition(0);
				_nextSong.setVisible(false);
				_previousSong.setVisible(false);
				_volumeDown.setVisible(false);
				_volumeUp.setVisible(false);
				_musicSoundLabel.setVisible(false);
				_startMusic.setText("Start Music");
			}
		}
		else if(e.getSource() == _previousSong) 
		{
			if(_songClip.getMicrosecondPosition() < 5000000)
			{
				
				AudioInputStream audioStream = null;
				if(_songsIndex == 0) 
					_songsIndex = _songs.size()-1;
				else
					_songsIndex--;
				
				try {
					audioStream = AudioSystem.getAudioInputStream(_songs.get(_songsIndex));
				} catch (UnsupportedAudioFileException | IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				_songClip.close();
				try {
					_songClip.open(audioStream);
				} catch (LineUnavailableException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				_songClip.start();
				_songClip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			else {
				_songClip.setMicrosecondPosition(0);
			}
		}
		else if(e.getSource() == _nextSong) 
		{
			AudioInputStream audioStream = null;
			if(_songsIndex == _songs.size()-1) 
				_songsIndex = 0;
			else
				_songsIndex++;
			try {
				audioStream = AudioSystem.getAudioInputStream(_songs.get(_songsIndex));
			} catch (UnsupportedAudioFileException | IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			_songClip.close();
			try {
				_songClip.open(audioStream);
			} catch (LineUnavailableException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_songClip.start();
			_songClip.loop(Clip.LOOP_CONTINUOUSLY);
			
		}
		else if(e.getSource() == _volumeDown) 
		{
			if(_gainControl.getValue() - 5 > _gainControl.getMinimum())
			{
				_gainControl.setValue(_gainControl.getValue()-5f);
				_musicSoundLabel.setText("Sound:" + (int)(_gainControl.getValue()+80f)+ "/85");
				
			}
			else 
			{
				_musicSoundLabel.setText("Sound: MIN");
			}
		}
		else if(e.getSource() == _volumeUp) 
		{
			if(_gainControl.getValue() + 5 < _gainControl.getMaximum())
			{
				_gainControl.setValue(_gainControl.getValue()+5f);
				_musicSoundLabel.setText("Sound:" + (int)(_gainControl.getValue()+80f)+ "/85");
			}
			else 
			{
				_musicSoundLabel.setText("Sound: MAX");
				
			}
			
		}
		
		
	}
	public void reset() {
		// reset all the varibles to start a new game
		logicGame.get_player1().set_points(0);
		logicGame.get_player2().set_points(0);
		logicGame.get_boardGame().clearMat();
		_pointsAText.setText(_pointsA + "/20");
		_pointsBText.setText(_pointsB + "/20");
		int i,j;// TODO Auto-generated method stub
		tTurn.setText("Player 1");
		_beforeTheLastTurn.setText("");
		_theLastTurn.setText("");
		turn = true;
		setCountTurn(1);
		setLight(false);
		setLastTurn(null);
		tTurn.setForeground(blue);
		
		_songClip.stop();
		_songClip.setMicrosecondPosition(0);
		_nextSong.setVisible(false);
		_previousSong.setVisible(false);
		_volumeDown.setVisible(false);
		_volumeUp.setVisible(false);
		_musicSoundLabel.setVisible(false);
		_startMusic.setText("Start Music");
		
		for(i=0;i<9;i++) 
		{
			for(j=0;j<9;j++) 
			{
				if(_board[i][j].get_stoneColor()!=Square._soldierColor.EMPTY)//cancel the if for the feature
				{
					_board[i][j].set_stoneColor(Square._soldierColor.EMPTY);
					_board[i][j].repaint();
				}
			}
		}
	}
	public void checkWin() 
	{
		//check if there is a win
		logicGame.checkWin();
		if(logicGame.get_isWin()) 
		{
			_songClip.close();
			this.dispose();
		}
	}
	public int get_pointsA() {
		/*returns pointA
		   :return: _pointsA
		   :rtype: int
		*/
		return _pointsA;
	}
	public void set_pointsA(int pointsA)   {
		/*change _pointsA to the param pointsA
		   :param pointsA: the points to change
		   :type pointsA: int
		   :return: void
		*/
		this._pointsA = pointsA;
	}
	public int get_pointsB() {
		/*returns pointB
		   :return: _pointsB
		   :rtype: int
		*/
		return _pointsB;
	}
	public void set_pointsB(int _pointsB) {
		/*change _pointsB to the param pointsB
		   :param pointsB: the points to change
		   :type pointsB: int
		   :return: void
		*/
		this._pointsB = _pointsB;
	}
	public static Square getSquare(int row,int col) 
	{
		/*returns BoardFrame._board[row][col]
		   :param row: the index of the row of the square
		   :param col: the index of the col of the square
		   :return: BoardFrame._board[row][col]
		   :rtype: Square
		*/
		return BoardFrame._board[row][col];
	}
	public Logic_GameManagement getLogicGame() {
		/**
		 * Returns the `Logic_GameManagement` instance used by this board frame.
		 *
		 * @return The `Logic_GameManagement` instance used by this board frame.
		 */
		return logicGame;
	}
	public void setLogicGame(Logic_GameManagement logicGame) {
		/**
		 * Sets the `Logic_GameManagement` instance used by this board frame.
		 *
		 * @param logicGame The `Logic_GameManagement` instance to set.
		 */
		this.logicGame = logicGame;
	}
	public _soldierColor getLastTurn() {
		/**
		 * Returns the color of the last turn.
		 *
		 * @return The color of the last turn.
		 */
		return lastTurn;
	}
	public void setLastTurn(_soldierColor lastTurn) {
		/**
		 * Sets the color of the last turn.
		 *
		 * @param lastTurn The color of the last turn to set.
		 */
		this.lastTurn = lastTurn;
	}
	public int getCountTurn() {
		/**
		 * Returns the count of turns played.
		 *
		 * @return The count of turns played.
		 */
		return countPlays;
	}
	public void setCountTurn(int countTurn) {
		/**
		 * Sets the count of turns played.
		 *
		 * @param countTurn The count of turns played to set.
		 */
		this.countPlays = countTurn;
	}
	public boolean isLight() {
		/**
		 * check if the color is the light or the regular
		 */
		return light;
	}
	public void setLight(boolean light) {
		//set if the color is the light or the regular
		this.light = light;
	}
	public static boolean getTurn() {
		/**
		 * Returns the current turn.
		 *
		 * @return `true` if it is the light player's turn, `false` otherwise.
		 */
		return turn;
	}
	public static void setTurn(boolean turn) {
		/**
		 * Sets the current turn.
		 *
		 * @param turn `true` if it is the light player's turn, `false` otherwise.
		 */
		BoardFrame.turn = turn;
	}
	public JTextArea get_theLastTurn() {
		/*returns _theLastTurn
		   :return: _theLastTurn
		   :rtype: JTextArea
		*/
		return _theLastTurn;
	}
	public void set_theLastTurn(JTextArea _theLastTurn) {
		/*change theLastTurn
		   :param _theLastTurn: JTextArea to change to.
		   :return: void
		*/
		this._theLastTurn = _theLastTurn;
	}
	public JTextArea get_beforeTheLastTurn() {
		/*returns _beforeTheLastTurn
		   :return: beforeTheLastTurn
		   :rtype: JTextArea
		*/
		return _beforeTheLastTurn;
	}
	public void set_beforeTheLastTurn(JTextArea _beforeTheLastTurn) {
		/*change beforeTheLastTurn
		   :param beforeTheLastTurn: JTextArea to change to.
		   :return: void
		*/
		this._beforeTheLastTurn = _beforeTheLastTurn;
	}
	public boolean get_isAi() {
		return _isAi;
	}
	public void set_isAi(boolean _isAi) {
		this._isAi = _isAi;
	}

}
