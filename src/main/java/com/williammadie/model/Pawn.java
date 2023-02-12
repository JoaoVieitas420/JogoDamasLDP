package com.williammadie.model;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Pawn {
	public static final int NUM_COLS = 10;
	public static final int NUM_ROWS = 10;
	public static final Image WHITE_PAWN_SPRITE = loadImage(Color.WHITE, false);
	public static final Image PURPLE_PAWN_SPRITE = loadImage(Color.PURPLE, false);
	private static final String assetsFolder = "/";
	public static Pawn[][] pawnsPosition;
	static {
		initBoard();
	}
	private String name;
	protected Color color;
	protected int row;
	protected int col;
	
	
	public Pawn(Color color, int rowInit, int colInit) {
		this.color = color;
		this.row = rowInit;
		this.col = colInit;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public void moveTo(int newRow, int newCol) {
		
		// If a capture took place, this will be triggered
		if (Math.abs(newRow - this.row) > 1 && Math.abs(newCol - this.col) > 1) {
			System.out.println("Je capture les pions suivants: " + this.findAllCapturedPawns(newRow, newCol));
			this.findAllCapturedPawns(newRow, newCol).forEach(pawn -> {
				pawn.getCaptured();
			});
			
		}
		
		pawnsPosition[this.row][this.col] = null;
		pawnsPosition[newRow][newCol] = this;
		this.row = newRow;
		this.col = newCol;
		
		// Promote pawn to king
		if (canBePromoted()) {
			this.getPromoted();
		}
	}
	

	public boolean canMoveTo(int newRow, int newCol) {
		return pawnsPosition[newRow][newCol] == null;
	}
	
	public void getCaptured() {
		Pawn capturedPawn = Pawn.pawnsPosition[this.row][this.col];
		Player hostilePlayer = Player.getPlayerWithColor(this.getColor().equals(Color.WHITE) ? Color.PURPLE : Color.WHITE);
		hostilePlayer.addCapturedPawn(capturedPawn);
		Pawn.pawnsPosition[this.row][this.col] = null;
	}
	
	public List<Cell> getNextCells() {
		return scanNeighbors();
	}
	
	protected List<Cell> scanNeighbors() {
		List<Cell> nextCells = new LinkedList<>();
		int[] closeLocationsRow = new int[] {-1, 1, 1, -1};
		int[] closeLocationsCol = new int[] {-1, -1, 1, 1};
		boolean hasToCaptureAPawn = false;
		
		for (int i = 0; i < 4; i++) {
			int neighborRow = this.row + closeLocationsRow[i];
			int neighborCol = this.col + closeLocationsCol[i];
			
			// Verify that Evaluated Cell is in the Grid
			if (Pawn.isOffRange(neighborRow, neighborCol))
				continue;

			Pawn neighbor = pawnsPosition[neighborRow][neighborCol];
			
			// Empty and Hostile Neighbor
			if (neighbor == null) {
				if (hasToCaptureAPawn)
					continue;
				
				if (this.color == Color.WHITE && neighborRow < this.row) {
					nextCells.add(new Cell(neighborRow, neighborCol));
				} else if (this.color == Color.PURPLE && neighborRow > this.row) {
					nextCells.add(new Cell(neighborRow, neighborCol));
				}
			} else if (neighbor.getColor() != this.getColor()) {
				if (neighbor.canBeCapturedFrom(this.row, this.col)) {
					if (!hasToCaptureAPawn)
						nextCells.clear();
					nextCells.add(new Cell(neighborRow + closeLocationsRow[i], neighborCol + closeLocationsCol[i]));
					hasToCaptureAPawn = true;
				}
			}
		}
		return nextCells;
	}
	
	protected int calculateDistanceCoefficient(int hostileRow, int hostileCol) {
		int distanceCoefficient = 0;
		
		int rowAfterCapture = this.row;
		int colAfterCapture = this.col;
		Pawn cellAfterCapture;

		do {
			rowAfterCapture = hostileRow < this.row ? rowAfterCapture + 1 : rowAfterCapture - 1;
			colAfterCapture = hostileCol < this.col ? colAfterCapture + 1 : colAfterCapture - 1;
			
			if (isOffRange(rowAfterCapture, colAfterCapture)) {
				return 0;
			}

			cellAfterCapture = pawnsPosition[rowAfterCapture][colAfterCapture];
			
			if (cellAfterCapture != null && !cellAfterCapture.getColor().equals(this.color)) {
				return 0;
			}
			distanceCoefficient++;
		} while (cellAfterCapture != null);
		return distanceCoefficient;
	}
	
	public boolean canBeCapturedFrom(int hostileRow, int hostileCol) {
		int rowAfterCapture = hostileRow < this.row ? this.row + 1 : this.row - 1;
		int colAfterCapture = hostileCol < this.col ? this.col + 1 : this.col - 1;

		if (isOffRange(rowAfterCapture, colAfterCapture)) {
			return false;
		}

		Pawn cellAfterCapture = pawnsPosition[rowAfterCapture][colAfterCapture];
		return cellAfterCapture == null;
	}
	
	public List<Pawn> findAllCapturedPawns(int newRow, int newCol) {
		List<Pawn> capturedPawns = new LinkedList<>();
		
		int inspectedRow = this.row < newRow ? this.row + 1 : this.row - 1;
		int inspectedCol = this.col < newCol ? this.col + 1 : this.col - 1;
		while (inspectedRow != newRow && inspectedCol != newCol) {
			Pawn inspectedPawn = Pawn.pawnsPosition[inspectedRow][inspectedCol];
			
			// Test case only
			if (inspectedPawn == null) {
				inspectedRow = inspectedRow < newRow ? inspectedRow + 1 : inspectedRow - 1;
				inspectedCol = inspectedCol < newCol ? inspectedCol + 1 : inspectedCol - 1;
				if (isOffRange(inspectedRow, inspectedCol)) {
					break;
				} else {
					continue;
				}
			}
			
			if (inspectedPawn.getColor() != this.getColor()) {
				capturedPawns.add(inspectedPawn);
			}
			inspectedRow = inspectedRow < newRow ? inspectedRow + 1 : inspectedRow - 1;
			inspectedCol = inspectedCol < newCol ? inspectedCol + 1 : inspectedCol - 1;
		}
		return capturedPawns;
	}
	
	public boolean canBePromoted() {
		if (this.row == NUM_ROWS - 1 && this.color.equals(Color.PURPLE)) {
			return true;
		} else if (this.row == 0 && this.color.equals(Color.WHITE)) {
			return true;
		}
		return false;
	}
	
	public void getPromoted() {
		Pawn.pawnsPosition[this.row][this.col] = new King(this.color, this.row, this.col);
	}
	
	public Image getSprite(Color wantedColor) {
		return wantedColor.equals(Color.WHITE) ? Pawn.WHITE_PAWN_SPRITE : Pawn.PURPLE_PAWN_SPRITE; 
	}
	
	protected static Image loadImage(Color wantedColor, boolean isKing) {
		String imgColor = wantedColor.equals(Color.WHITE) ? "white" : "purple"; 
		String imgPath = isKing ? assetsFolder + imgColor + "-king.png" : assetsFolder + imgColor + "-pawn.png";
		return new Image(Pawn.class.getResource(imgPath).toExternalForm());
	}
	
	public static boolean isOffRange(int row, int col) {
		return row < 0 || row >= NUM_ROWS || col < 0 || col >= NUM_COLS;
	}
	
	public static void initBoard() {
		// Initialize the position of white and purple pawns
		pawnsPosition = new Pawn[NUM_ROWS][NUM_COLS];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				if ((i + j) % 2 != 0) {
					pawnsPosition[i][j] = new Pawn(Color.PURPLE, i, j);
				} else {
					pawnsPosition[NUM_ROWS - (i + 1)][j] = new Pawn(Color.WHITE, NUM_ROWS - (i + 1), j);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return this.color + " [row=" + this.row + ",col=" + this.col;
	}
}