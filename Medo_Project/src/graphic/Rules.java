package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Rules extends JFrame implements ActionListener
{
	private JLabel _title;
	private JTextArea _rules;
	private JButton _back;
	public Rules()
	{
		
		setLayout(null);
		
		Color bC = new Color(211, 169, 122);
		Color tB = new Color(235, 213, 189);
		Font tfont = new Font("Arial", Font.BOLD, 72);	
		Font rfont = new Font("Arial", Font.BOLD, 22);	
		Font bfont = new Font("Arial", Font.BOLD, 48);	

		_title = new JLabel("RULES");
		_title.setBounds(728, 100, 250, 100);
		_title.setFont(tfont);
		_title.setForeground(Color.black);
		add(_title);
		
		_rules = new JTextArea();
		_rules.setText("Medo is a spatial strategy game designed by Nick Bentley,"
				+ "\ninspired by the idea of flowers competing for space in a Meadow.\n"
				+ "\nThe players take turns placing stones on the board, trying to"
				+ "\ncapture groups of opponent stones.You capture opponent groups"
				+ "\neither by smothering them (surrounding them) or when they're "
				+ "\novergrown.The first player to capture 20 stones wins.\n" 
				+ "\nThe tricky bit:"
				+ "\neach player owns two different stone colors, which they must play."
				+ "\nIf you're not careful, your own pieces may end up in competition"
				+ "\nwith one another, sinking your chances.\n");
		_rules.setEditable(false);
		_rules.setBounds(503, 300, 700, 330);
		_rules.setFont(rfont);
		_rules.setBackground(tB);
		_rules.setForeground(Color.black);
		_rules.setForeground(Color.black);
		add(_rules);
		
		_back = new JButton("Home");
		_back.setFont(bfont);
		_back.setBounds(728, 800, 250, 100);
		_back.setBackground(tB);
		_back.setForeground(Color.black);
		_back.addActionListener(this);
		add(_back);
		
		getContentPane().setBackground(bC);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == _back)
		{
			new HomePage().setVisible(true);
			dispose();
		}
	}
}