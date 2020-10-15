package com.github.GuyHaviv37;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 * LaunchPage Class - a GUI main menu frame for selecting a difficulty for a new minesweeper game
 * @author GuyHaviv
 *
 */
public class LaunchPage extends JFrame implements ActionListener{
	
	JMenuBar menuBar;
	JButton easyBtn;
	JButton mediumBtn;
	JButton hardBtn;

	// The constructor builds the Swing UI for the frame.
	LaunchPage(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1,0,0));
		this.setPreferredSize(new Dimension(500,400));
		
		menuBar = new MyMenuBar(this);
		JPanel menu = new JPanel();
		JPanel titles = new JPanel(new GridLayout(2,1,0,0));
		JLabel title = new JLabel();
		JLabel subtitle = new JLabel();

		titles.setSize(600,500);
		menu.setSize(600,100);
		
		title.setText("Welcome to Minesweeper");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Arial",Font.BOLD,30));
		title.setForeground(Color.DARK_GRAY);
		//title.setBounds(200,0,400,80);
		titles.add(title);
		
		subtitle.setText("Please choose a difficulty");
		subtitle.setFont(new Font("MV Boli",Font.PLAIN,20));
		subtitle.setHorizontalAlignment(JLabel.CENTER);
		subtitle.setForeground(Color.DARK_GRAY);
		titles.add(subtitle);
		
		easyBtn = new JButton("Easy");
		easyBtn.setFocusable(false);
		easyBtn.setPreferredSize(new Dimension(100,40));
		easyBtn.addActionListener(this);
		
		mediumBtn = new JButton("Medium");
		mediumBtn.setFocusable(false);
		mediumBtn.setPreferredSize(new Dimension(100,40));
		mediumBtn.addActionListener(this);

		
		hardBtn = new JButton("Hard");
		hardBtn.setFocusable(false);
		hardBtn.setPreferredSize(new Dimension(100,40));
		hardBtn.addActionListener(this);

		
		menu.add(easyBtn); menu.add(mediumBtn); menu.add(hardBtn);
		
		this.add(titles);
		this.add(menu);
		this.setJMenuBar(menuBar);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	// Action listener on the difficulty JButtons to init new GameView frame with certain difficulty as int.
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == easyBtn) {
			//EASY MODE
			this.dispose();
			new GameView(10);
		} else if (e.getSource() == mediumBtn) {
			//MEDIUM MODE
			this.dispose();
			new GameView(15);
		} else if (e.getSource() == hardBtn) {
			//HARD MODE
			this.dispose();
			new GameView(20);
		}
		
	}

}
