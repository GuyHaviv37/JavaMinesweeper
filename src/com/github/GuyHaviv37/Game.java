package com.github.GuyHaviv37;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Game Class - backend logic for the a minesweeper game.
 * @author GuyHaviv
 *
 */
public class Game {

	JFrame parentFrame;
	int dim;
	List<JButton> buttonList;
	Cell[][] grid;
	int originalBombs;
	int bombsLeft;
	int flagsUp;
	public Game(JFrame parentFrame,int dim, List<JButton> buttonList,int firstCellRow, int firstCellCol) {
		this.parentFrame = parentFrame;
		this.dim = dim;
		this.buttonList = buttonList;
		this.flagsUp = 0;
		grid = new Cell[dim][dim];
		this.initGrid(firstCellRow,firstCellCol);
	}
	
	/**
	 * Initialize Cell grid component of the game:
	 * Initialize the grid with Cells corrosponding to certain GUI buttons.
	 * Randomizes bomb locations and computes for all other cells # of adj. bombs.
	 * @param firstCellRow - row of the first cell clicked (range 0 to dim-1)
	 * @param firstCellCol - column of the first cell clicked (range 0 to dim-1)
	 */
	private void initGrid(int firstCellRow, int firstCellCol) {
		int i,j;
		for(i=0;i<dim;i++) {
			for(j=0;j<dim;j++) {
				grid[i][j] = new Cell(buttonList.get(i*dim + j));
			}
		}
		int bombs = (int) Math.floor(dim*1.5); // can randomize this later
		this.originalBombs = bombs;
		this.bombsLeft = bombs;
		while(bombs > 0) {
			int row = (int)(Math.random() * dim);
			int col = (int)(Math.random() * dim);
			if(grid[row][col].isBomb || (firstCellRow == row && firstCellCol == col)) continue;
			else {
				grid[row][col].isBomb = true;
				bombs--;
			}
		}
		for(i=0;i<dim;i++) {
			for(j=0;j<dim;j++) {
				grid[i][j].adj = getAdjBombs(i,j);
			}
		}
	}
	
	private int getAdjBombs(int row, int col) {
		int adj = 0;
		for(int i=-1;i<=1;i++) {
			for(int j=-1;j<=1;j++) {
				if(row+i < 0 || row+i >= dim || col+j < 0 || col+j >=dim) continue;
				if(grid[row+i][col+j].isBomb) adj++;
			}
		}
		return adj;
	}
	
	// boolean "getter" for isRevealed for GUI.
	public boolean isCellRevealed(int row,int col) {
		return grid[row][col].isRevealed;
	}
	
	// Flag cell in (row,column)
	public void flagCell(int row, int col) {
		grid[row][col].isFlaged = true;
		this.flagsUp++;
		if(grid[row][col].isBomb) this.bombsLeft--;
		if(bombsLeft == 0 && flagsUp == originalBombs) {
			// GAME WON
			this.revealAllCells();
			this.invokeDialog("Congratulations, you won! new game ?",JOptionPane.QUESTION_MESSAGE);
		}
	}

	// Un-flag cell in (row,column)
	public void unflagCell(int row, int col) {
		grid[row][col].isFlaged = false;
		this.flagsUp--;
		if(grid[row][col].isBomb) this.bombsLeft++;
	}
	
	/**
	 * If row and col correspond to a valid cell, mark cell as reveal in GUI.
	 * If cell was a bomb - game over, if it has no adj. bombs , reveal all bombs adj to it - this works recursively. 
	 * @param row - row of cell to reveal
	 * @param col - col of cell to reveal
	 */
	public void revealCell(int row,int col) {
		// check if in bounds and not yet revealed
		if(!isRevealValid(row,col)) return;
		// adjust button view
		JButton btn = buttonList.get(row*dim + col);
		btn.setBackground(Color.white);
		btn.setText(grid[row][col].toString());
		grid[row][col].isRevealed = true;
		// check if bomb - failed , if adj == 0 - rec reveal, else - return
		Cell c = grid[row][col];
		if(c.isBomb) {
			// GAME LOST
			this.revealAllCells();
			this.invokeDialog("You Lose.. new game?",JOptionPane.ERROR_MESSAGE);
		} else if (c.adj == 0) {
			for(int i=-1;i<=1;i++) {
				for(int j=-1;j<=1;j++) {
					revealCell(row+i,col+j);
				}
			}
		} else return;
	}
	
	// a Cell is valid to reveal if the (row,column) dimensions are within (0 to dim-1) and was not previously revealed.
	private boolean isRevealValid(int row, int col) {
		if(row < 0 || row >= dim || col < 0 || col >=dim) return false;
		if(grid[row][col].isRevealed) return false;
		return true;
	}
	
	// After game is over (win or lose) - reveal all cells to the user.
	private void revealAllCells() {
		for(int i=0;i<buttonList.size();i++) {
			JButton btn = buttonList.get(i);
			int row = i / dim;
			int col = i % dim;
			if(btn.getBackground() != Color.red) {
				btn.setBackground(Color.white);
			}
			btn.setText(grid[row][col].toString());
			if(grid[row][col].isBomb) btn.setForeground(Color.blue);
		}
		
	}
	
	/**
	 * Invokes dialog after game is over (win or lose) - to start new game (go to main menu) or exit the app.
	 * @param message - String of message to the user
	 * @param messageType - int representing the type of message for the user - use JOptionPane for this,
	 * e.g, JOptionPane.ERROR_MESSAGE.
	 */
	private void invokeDialog(String message, int messageType) {
		String[] options = {"Yes","No"};
		int response = JOptionPane.showOptionDialog(null, message,
				"Game Over", JOptionPane.YES_NO_OPTION, messageType, null, options, 0);
		switch(response) {
			case 0: // New Game
				parentFrame.dispose();
				new LaunchPage();
				break;
			case 1: // Exit Game
				parentFrame.dispose();
				System.exit(0);
				break;
			default:
				System.exit(0);
		}
	}
	

}
