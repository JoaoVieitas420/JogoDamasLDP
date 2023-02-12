package com.williammadie.model;

import java.util.Arrays;

public class Cell{
	private int row;
	private int col;
	public static boolean[][] highlightedCells = new boolean[Pawn.NUM_ROWS][Pawn.NUM_COLS];
	
	public Cell (int row, int col) {
		this.row=row;
		this.col=col;	
	}
	
	public static void darkenAllCells() {
		Arrays.stream(highlightedCells).forEach(row -> Arrays.fill(row, false));
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		
		if (!(o instanceof Cell)) {
			return false;
		}
		
		Cell c = (Cell) o;
		return Integer.compare(this.row, c.row) == 0 && Integer.compare(this.col, c.col) == 0;
	}
	
	@Override
	public String toString() {
		return "Cell: [row=" + this.row + ",col=" + this.col + "]";
	}
}
