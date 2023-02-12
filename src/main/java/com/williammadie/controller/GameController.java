package com.williammadie.controller;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.williammadie.model.Cell;
import com.williammadie.model.Game;
import com.williammadie.model.Pawn;
import com.williammadie.model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.ButtonBar.ButtonData;

public class GameController {
	private static boolean canHighlightCells = true;
	private static Pawn currentlySelectedPawn = null;
	private static Game currentGame = new Game();
	private static final ButtonType RESTART = new ButtonType("Restart",ButtonData.APPLY );
	private static final ButtonType QUIT = new ButtonType("Quit", ButtonData.APPLY);
	
	public void handleStartGameButton(GridPane board, Stage primaryStage) {
		if (currentGame.hasStarted()) {
			restartGame(board, primaryStage);
		}
		currentGame.setHasStarted(true);
		currentGame.newTour();
	}
	
	public IntegerProperty numberOfTours() {
		return currentGame.numberOfTours();
	}
	
	public StringProperty whoIsPlayingProperty() {
		return currentGame.whoIsPlayingProperty();
	}
	
	public ObjectProperty<Image> currentPawnProperty(){
		return currentGame.currentPawnProperty();
	}
	
	public void updateCellReferential(int i, int j) {
		Pawn pawn = Pawn.pawnsPosition[i][j];
		if(pawn != null) {
			
			if (!pawn.getColor().equals(currentGame.getWhoIsPlaying().getPlayingColor()))
				return;
			
			currentlySelectedPawn = pawn;
			System.out.println("Je viens de set le pion actuel !!");
			List<Cell> cellsToHighlight = pawn.getNextCells();
			cellsToHighlight.stream().forEach(cell -> Cell.highlightedCells[cell.getRow()][cell.getCol()]= true);					
		}
	}
	
	public void showHighlightedCells(GridPane board, Stage primaryStage){
		if(!canHighlightCells || allFalse(Cell.highlightedCells)) {		
			return;
		}
		canHighlightCells=false;
		for (int i = 0; i < Pawn.NUM_ROWS; i++) {
			for (int j = 0; j < Pawn.NUM_COLS; j++) {
				if(Cell.highlightedCells[i][j]){
					StackPane cell = new StackPane();
					cell.setStyle("-fx-background-color: GREEN");
					final AtomicInteger row = new AtomicInteger(i);
					final AtomicInteger col= new AtomicInteger(j);
					cell.addEventFilter(MouseEvent.MOUSE_CLICKED,e-> {
						currentlySelectedPawn.moveTo(row.get(), col.get());
						renderBoard(board, false, primaryStage);
						currentlySelectedPawn = null;
						canHighlightCells = true;
						Player winner = Game.getWinner();			
						if(winner == null) {
							currentGame.newTour();
						}
						else {
							showWinnerDialog(winner,primaryStage, board);
						}
						
				});
					board.add(cell,j,i);
					Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event-> {						
						board.getChildren().remove(cell);
						canHighlightCells = true;
						Cell.darkenAllCells();
					}));
					timeline.play();
				}			
			}
		}
		Cell.darkenAllCells();
	}
	
	public void renderBoard(GridPane board, boolean firstInit, Stage primaryStage) {
		for (int i = 0; i < Pawn.NUM_ROWS; i++) {
			for (int j = 0; j < Pawn.NUM_COLS; j++) {
				StackPane cell = new StackPane();
				final AtomicInteger row = new AtomicInteger(i);
				final AtomicInteger call= new AtomicInteger(j);
				cell.addEventFilter(MouseEvent.MOUSE_CLICKED,e-> {
						if (!currentGame.hasStarted())
							return;
						updateCellReferential(row.get(),call.get());
						showHighlightedCells(board, primaryStage);						
				});
				Pawn pawn = Pawn.pawnsPosition[i][j];
				if (pawn != null) {
					ImageView pawnView = new ImageView(pawn.getSprite(pawn.getColor()));
					pawnView.setFitWidth(55);
					pawnView.setPreserveRatio(true);
					pawnView.setSmooth(true);
					pawnView.setCache(true);
					cell.getChildren().add(pawnView);
				}
				if ((i + j) % 2 == 0) {
					cell.setStyle("-fx-background-color: #FFCE9E");
				} else {
					cell.setStyle("-fx-background-color: #D18B47");
				}
				Node nodeToModify = getNodeByCoordinate(j, i, board);

				if (nodeToModify == null) {
					board.add(cell, j, i);
				} else if (nodeToModify instanceof StackPane){
					if (!firstInit)
						board.getChildren().remove(nodeToModify);
					board.add(cell, j, i);
				}
			}
		}
	}
	
	Node getNodeByCoordinate(Integer row, Integer column, GridPane board) {
	    for (Node node : board.getChildren()) {
	        if(GridPane.getColumnIndex(node) == row && GridPane.getColumnIndex(node) == column){
	            return node;
	        }
	    }
	    return null;
	}
	
	boolean allFalse(boolean[][] array) {
	    for (boolean[] subArray : array) {
	        for (boolean b : subArray) {
	            if (b) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
	
	
	public ObservableList<Pawn> getCapturedSprite(Color color) {
		return Player.getCapturedPawn(color); 
	}
	
	public void showWinnerDialog(Player player, Stage primaryStage, GridPane board) {
		Alert alert = new Alert(Alert.AlertType.NONE, player.getName()+" won !", RESTART, QUIT);
		alert.showAndWait();
		
		ButtonType result = alert.getResult();
		if (result == QUIT) {
		    primaryStage.close();
		}
		else {
			restartGame(board, primaryStage);
		}
	}
	
	public void restartGame(GridPane board, Stage primaryStage) {	
		Pawn.initBoard();
		renderBoard(board, false, primaryStage);
		currentGame.restartGame();
		
	}
	
}