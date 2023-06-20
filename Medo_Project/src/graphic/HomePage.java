package graphic;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class HomePage extends JFrame implements ActionListener
{
	
	private JButton _rules;
	private JButton _settings;

	private JButton _winner;
	private JLabel _title;
	private ImageIcon _bg;
	private JLabel _midGame;
	public HomePage() 
	{
		setLayout(null);
		
		Font bfont = new Font("Arial", Font.BOLD, 48);	
		Font tfont = new Font("Arial", Font.BOLD, 72);	
		Color bC = new Color(211, 169, 122);
		Color button = new Color(235, 213, 189);
		_bg = new ImageIcon("midGame.png");
		Image newImg =_bg.getImage().getScaledInstance(480, 432,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		_bg = new ImageIcon(newImg);
				
		_midGame = new JLabel(); //JLabel Creation
		_midGame.setIcon(_bg); //Sets the image to be displayed as an icon
	    _midGame.setBounds(603, 200, 1000,432);
	    this.getContentPane().add(_midGame);
	    
		_title = new JLabel("Medo Game");
		_title.setBounds(647, 100, 412, 100);
		_title.setFont(tfont);
		_title.setForeground(Color.BLACK);
		add(_title);

		
		_rules = new JButton("RULES");
		_rules.setBackground(button);
		_rules.setBounds(703, 700, 300, 100);
		_rules.setFont(bfont);
		_rules.setForeground(Color.BLACK);
		_rules.addActionListener(this);
		_rules.setFocusPainted(false);
		add(_rules);

		_settings = new JButton("Start Game");
		_settings.setBackground(button);
		_settings.setBounds(653, 800, 400, 100);
		_settings.setFont(bfont);
		_settings.setForeground(Color.BLACK);
		_settings.addActionListener(this);
		_settings.setFocusPainted(false);
		add(_settings);
		
		
		
		getContentPane().setBackground(bC);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == _rules) 
		{
			new Rules().setVisible(true);
			dispose();
		}
		else if(e.getSource() == _settings) 
		{
			new StartGame().setVisible(true);
			dispose();
		}
		
		
	}
}
