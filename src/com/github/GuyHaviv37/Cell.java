package com.github.GuyHaviv37;

import javax.swing.JButton;

/**
 * Cell Class - implementation of a Cell in the minesweeper grid.
 * @author GuyHaviv
 *
 */
public class Cell {

	boolean isBomb;
	boolean isFlaged;
	boolean isRevealed;
	int adj;
	JButton button;
	
	Cell(JButton button){
		this.isBomb = false;
		this.isFlaged = false;
		this.isRevealed = false;
		this.adj = 0;
		this.button = button;
	}

	@Override
	public String toString() {
		if(isBomb) {
			return "B";
		} else if (adj == 0){
			return " ";
		} else {
			return String.valueOf(adj);
		}
	}
	
	

}
