package com.github.GuyHaviv37;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * GameView Class - the main game GUI frame. 
 * @author GuyHaviv
 *
 */
public class GameView extends JFrame{

		JMenuBar menuBar;
		JPanel gridContainer;
		Game game;
		List<JButton> grid;
		int gridSize;
		int dim;
		boolean firstClick = true;
		JFrame frame;
		
		GameView(int dim){
			frame = this;
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.dim = dim;
			this.gridSize = dim*dim;
			grid = new ArrayList<JButton>(gridSize);
			
			menuBar = new MyMenuBar(this);
			JPanel container = new JPanel(null);
			container.setPreferredSize(new Dimension(dim*50,dim*50));
			
			gridContainer = new JPanel();
			gridContainer.setBounds(3*dim,3*dim,44*dim,44*dim);
			gridContainer.setBackground(Color.black);
			gridContainer.setOpaque(true);
			gridContainer.setLayout(new GridLayout(dim,dim));
			for(int i=0;i<gridSize;i++) {
				JButton btn = new JButton();
				btn.addMouseListener(new MouseAdapter(){
					/**
					 * Anon. class to implement MouseAdapter with Override for mouseClicked.
					 * When right-clicking - set / unset flag + GUI change of color.
					 * When left-clicking - reveal cell.
					 */
					public void mouseClicked(MouseEvent e) {
						int cellID = grid.indexOf(e.getComponent());
						int row = cellID / dim;
						int column = cellID % dim;
						if(!firstClick && game.isCellRevealed(row,column)) return;
						if(SwingUtilities.isRightMouseButton(e)) {
							if(e.getComponent().getBackground() == Color.red) {
								e.getComponent().setBackground(UIManager.getColor("Button.background")); 
								game.unflagCell(row,column);
							}
							else {
								e.getComponent().setBackground(Color.red);
								game.flagCell(row,column);
							}
						} else if(SwingUtilities.isLeftMouseButton(e)) {
							if(firstClick) {
								game = new Game(frame,dim,grid,row,column);
								firstClick = false;
							}
							game.revealCell(row,column);
						}
					}
				});
				btn.setFocusable(false);
				grid.add(btn);
				gridContainer.add(btn);
			}
			
			container.add(gridContainer);
			this.add(container);
			this.setJMenuBar(menuBar);
			this.pack();
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			
		}
		
}
