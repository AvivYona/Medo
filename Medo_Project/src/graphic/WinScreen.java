package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WinScreen extends JFrame implements ActionListener
{
	private JLabel _title;
	private JLabel _title2;
	private JButton _back;
	public WinScreen(boolean winner) //true for blue.
	{
		setLayout(null);
		Font tfont = new Font("Arial", Font.BOLD, 64);
		Color bB = new Color(235, 213, 189);
		Font bfont = new Font("Arial", Font.BOLD, 36);
		Color bC = new Color(211, 169, 122);

		_title = new JLabel("is the Winner!");
		_title.setBounds(500, 50, 800, 100);
		_title.setFont(tfont);
		_title.setForeground(Color.black);
		add(_title);
		
		
		if(winner) 
		{
			_title.setText("Player 1 is the winner!");
		}
		else 
		{
			_title.setText("Player 2 is the winner!");
		}
		ImageIcon trophyIcon = new ImageIcon("trophy.png");
        JLabel trophyLabel = new JLabel(trophyIcon);
        trophyLabel.setBounds(575, 200, 512, 512);
        trophyLabel.setOpaque(false);
        add(trophyLabel);

		_back = new JButton("Home");
		_back.setFont(bfont);
		_back.setBounds(735, 750, 200, 100);
		_back.setBackground(bB);
		_back.addActionListener(this);
		_back.setForeground(Color.black);
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
