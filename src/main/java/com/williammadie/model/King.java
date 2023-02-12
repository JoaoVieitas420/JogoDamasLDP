package com.williammadie.model;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class King extends Pawn {
	private static final Image WHITE_KING_SPRITE = loadImage(Color.WHITE, true);
	private static final Image PURPLE_KING_SPRITE = loadImage(Color.PURPLE, true);
	
	public King(Color color, int rowInit, int colInit) {
		super(color, rowInit, colInit);
	}
	
	@Override
	public Image getSprite(Color wantedColor) {
		return wantedColor.equals(Color.WHITE) ? King.WHITE_KING_SPRITE : King.PURPLE_KING_SPRITE; 
	}
	
	@Override
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
				do {
					if (neighbor == null) {
						nextCells.add(new Cell(neighborRow, neighborCol));
					}
					neighborRow += closeLocationsRow[i];
					neighborCol += closeLocationsCol[i];
					if (Pawn.isOffRange(neighborRow, neighborCol))
						break;
					neighbor = pawnsPosition[neighborRow][neighborCol];
				} while (neighbor == null || !neighbor.getColor().equals(this.color));

			} else if (neighbor.getColor() != this.getColor()) {
				int distanceCoefficient = neighbor.calculateDistanceCoefficient(this.row, this.col);
				if (distanceCoefficient > 0) {
					if (!hasToCaptureAPawn)
						nextCells.clear();
					Cell c = new Cell(neighborRow + distanceCoefficient * closeLocationsRow[i], neighborCol + distanceCoefficient * closeLocationsCol[i]);
					nextCells.add(c);
					hasToCaptureAPawn = true;
				}
			}
		}
		return nextCells;
	}
}