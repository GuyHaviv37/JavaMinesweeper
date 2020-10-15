package com.github.GuyHaviv37;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * MyMenuBar Class - a menu bar that will be used through out the app.
 * Supports New Game and Exit.
 * @author GuyHaviv
 *
 */
public class MyMenuBar extends JMenuBar implements ActionListener{
	
	JFrame parent;
	JMenu gameMenu;
	JMenuItem newGameItem;
	JMenuItem exitItem;
	MyMenuBar(JFrame parentFrame){
		this.parent = parentFrame;
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.black));
		
		gameMenu = new JMenu("Game");
		newGameItem = new JMenuItem("New Game");
		exitItem = new JMenuItem("Exit");
		
		gameMenu.setMnemonic(KeyEvent.VK_G);
		newGameItem.setMnemonic(KeyEvent.VK_N);
		exitItem.setMnemonic(KeyEvent.VK_E);
		
		newGameItem.addActionListener(this);
		exitItem.addActionListener(this);
		
		gameMenu.add(newGameItem);
		gameMenu.add(exitItem);
		this.add(gameMenu);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGameItem) {
			System.out.println("RESTART GAME");
			parent.dispose();
			new LaunchPage();
			
		} else if (e.getSource() == exitItem) {
			System.out.println("Exiting...");
			System.exit(0);
		}
	}

}
