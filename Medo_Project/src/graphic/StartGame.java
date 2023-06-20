package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class StartGame extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6243135499507673009L;
	private JLabel _title;
	private JButton _playB;
	private JButton _homeB;
	private static JComboBox _chooseColors;
	private static JComboBox _chooseLevel;
	private JTextArea _levelText;
	private JLabel _choose;
	private JRadioButton _pvc;
	private JRadioButton _pvp;
	private ButtonGroup _plays;
	private JTextArea _colorsText;
	private boolean _isAi;
	public StartGame() 
	{
		Font tfont = new Font("Arial", Font.BOLD, 72);
		Font bfont = new Font("Arial", Font.BOLD, 48);
		Font lfont = new Font("Arial", Font.BOLD, 20);	

		Color bC = new Color(211, 169, 122);
		Color tB = new Color(235, 213, 189);
		
	
		setLayout(null);
		
		
		_choose = new JLabel("Choose to play:");
		_choose.setBounds(711, 180, 284, 200);
		_choose.setForeground(Color.BLACK);
		_choose.setFont(lfont);
		add(_choose);
		
		_title = new JLabel("Start Game");
		_title.setBounds(675, 100, 400, 100);
		_title.setForeground(Color.BLACK);
		_title.setFont(tfont);
		add(_title);
		
		_playB = new JButton("PLAY");
		_playB.setFont(bfont);
		_playB.setBounds(728, 700, 250, 100);
		_playB.setBackground(tB);
		_playB.setForeground(Color.black);
		_playB.addActionListener(this);
		add(_playB);
		
		_homeB = new JButton("Home");
		_homeB.setFont(bfont);
		_homeB.setBounds(653, 800, 400, 100);
		_homeB.setBackground(tB);
		_homeB.setForeground(Color.black);
		_homeB.addActionListener(this);
		add(_homeB);
		
		
		
		_pvc = new JRadioButton("Player VS Computer");
		_pvc.setBounds(703, 300, 300, 50);
		_pvc.setOpaque(false);
		_pvc.setForeground(Color.black);
		_pvc.setFont(new Font("david", Font.PLAIN, 30));
		_pvc.addActionListener(this);
		_pvc.addActionListener(this);
		add(_pvc);
		
		_pvp = new JRadioButton("Player VS Player");
		_pvp.setBounds(703, 350, 300, 50);
		_pvp.setOpaque(false);
		_pvp.setForeground(Color.black);
		_pvp.setFont(new Font("david", Font.PLAIN, 30));
		_pvp.addActionListener(this);
		_pvp.addActionListener(this);
		add(_pvp);
		
		
		_plays = new ButtonGroup();
		_plays.add(_pvc);
		_plays.add(_pvp);

		
		_chooseColors = new JComboBox<String>(new String[] {"Blue VS Red", "Brown VS Gray"});
		_chooseColors.setFont(lfont);
		_chooseColors.setBackground(tB);
		_chooseColors.setForeground(Color.black);
		_chooseColors.setUI(new StyledComboUI());
		_chooseColors.setBounds(728, 500, 250, 50);
		
		
		_colorsText = new JTextArea();
		_colorsText.setBounds(570, 512, 155, 50);
		_colorsText.setEditable(false);
		_colorsText.setBackground(bC);
		_colorsText.setFont(lfont);
		_colorsText.setForeground(Color.black);
		_colorsText.setText("Choose colors:");
		
		
		_chooseLevel = new JComboBox<String>(new String[] {"beginner", "professional","legendary"});
		_chooseLevel.setFont(lfont);
		_chooseLevel.setBackground(tB);
		_chooseLevel.setForeground(Color.black);
		_chooseLevel.setUI(new StyledComboUI());
		_chooseLevel.setBounds(728, 400, 250, 50);
		
		
		_levelText = new JTextArea();
		_levelText.setBounds(570, 412, 155, 50);
		_levelText.setEditable(false);
		_levelText.setBackground(bC);
		_levelText.setFont(lfont);
		_levelText.setForeground(Color.black);
		_levelText.setText("Choose Ai level:");
		
		
		add(_levelText);
		add(_chooseLevel);
		add(_chooseColors);
		add(_colorsText);
		
		_levelText.setVisible(false);
		_chooseLevel.setVisible(false);
		_chooseColors.setVisible(false);
		_colorsText.setVisible(false);
		_playB.setVisible(false);
		
		getContentPane().setBackground(bC);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == _playB) 
		{
			new BoardFrame(_isAi).setVisible(true);
			dispose();
		}
		else if(e.getSource() == _pvc) 
		{
			_levelText.setVisible(true);
			_chooseLevel.setVisible(true);
			_chooseColors.setVisible(true);
			_colorsText.setVisible(true);
			_playB.setVisible(true);
			_isAi = true;
		}
		else if(e.getSource() == _pvp) 
		{
			_levelText.setVisible(false);
			_chooseLevel.setVisible(false);
			_chooseColors.setVisible(true);
			_colorsText.setVisible(true);
			_playB.setVisible(true);

			_isAi = false;
		}
		else if(e.getSource() == _homeB) 
		{
			new HomePage().setVisible(true);
			dispose();
		}
	}
	public static int getComboIndexC() 
	{
		if(_chooseColors!=null)
			return _chooseColors.getSelectedIndex();
		else
			return 0;
	}
	public static int getComboIndexB() 
	{
		if(_chooseLevel!=null)
			return _chooseLevel.getSelectedIndex();
		else
			return 0;
	}
}
