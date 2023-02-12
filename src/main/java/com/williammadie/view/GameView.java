package com.williammadie.view;


import com.williammadie.controller.GameController;
import com.williammadie.model.Pawn;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameView {
	private static GameController gameController = new GameController();

	
	public static void initView(BorderPane root, Stage primaryStage) {
		// Center
		GridPane board = new GridPane();
		buildGrid(board);
		initGrid(board, primaryStage);

		root.setCenter(board);

		// Top

		// Left
		ImageView pawnView = new ImageView();
		pawnView.imageProperty().bind(gameController.currentPawnProperty());
		pawnView.setFitWidth(45);
		pawnView.setPreserveRatio(true);
		pawnView.setSmooth(true);
		pawnView.setCache(true);
		
		
		Label currentPlayerLabel = new Label();
		currentPlayerLabel.textProperty().bind(gameController.whoIsPlayingProperty());
		currentPlayerLabel.setPrefWidth(100);
		currentPlayerLabel.setPadding(new Insets(0, 0, 0, 5));
		
		Label infoTour = new Label("Tour n°");
		Label currentTour = new Label();
		currentTour.textProperty().bind(gameController.numberOfTours().asString());
		HBox tourPanel = new HBox(infoTour, currentTour);
		tourPanel.setAlignment(Pos.CENTER);
		
		VBox infoPanel = new VBox(pawnView,currentPlayerLabel, tourPanel);
		infoPanel.setPadding(new Insets(50, 50, 50, 50));
		infoPanel.setAlignment(Pos.CENTER);
		infoPanel.setSpacing(20);
		root.setLeft(infoPanel);
		
		
		
		// Right
		Label gameControls = new Label("Game Controls");
		Button startGameButton = new Button("Start Game");
		startGameButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				gameController.handleStartGameButton(board, primaryStage);
				if (startGameButton.getText().equals("Start Game")) {
					startGameButton.setText("Restart Game");
				}
			}
		});
		startGameButton.setPadding(new Insets(10, 10, 10, 10));
		
		Button quitGameButton = new Button("Quit");
		quitGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(Alert.AlertType.NONE, "Any progress will be lost, do you want to continue?", ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				
				ButtonType result = alert.getResult();
				if (result == ButtonType.YES) {
				    primaryStage.close();
				}
			}
		});
		quitGameButton.setPadding(new Insets(10, 10, 10, 10));
		
		VBox btnPanel = new VBox(gameControls, startGameButton, quitGameButton);
		//btnPanel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		btnPanel.setAlignment(Pos.CENTER);
		btnPanel.setSpacing(20);
		btnPanel.setPadding(new Insets(50, 50, 50, 50));
		root.setRight(btnPanel);
					
		// Bottom
		HBox capturedPawnsHBox = new HBox();
		ObservableList<Pawn> capturedPawnW = gameController.getCapturedSprite(Color.WHITE);
		capturedPawnW.addListener(new ListChangeListener<Pawn>(){

			@Override
			public void onChanged(Change<? extends Pawn> arg0) {
				
				if(capturedPawnW.isEmpty()) {
					capturedPawnsHBox.getChildren().clear();
					return;
				}
				ImageView pawnView =new ImageView(Pawn.PURPLE_PAWN_SPRITE);
				pawnView.setFitWidth(55);
				pawnView.setPreserveRatio(true);
				pawnView.setSmooth(true);
				pawnView.setCache(true);
				capturedPawnsHBox.getChildren().add(pawnView);
			}
			
		});
		capturedPawnsHBox.setAlignment(Pos.CENTER);
		capturedPawnsHBox.setPadding(new Insets(0, 0, 10, 0));
		root.setBottom(capturedPawnsHBox);
		
		//TOP
		HBox capturedPawnsHBoxTop = new HBox();
		ObservableList<Pawn> capturedPawn = gameController.getCapturedSprite(Color.PURPLE);
		capturedPawn.addListener(new ListChangeListener<Pawn>(){

			@Override
			public void onChanged(Change<? extends Pawn> arg0) {
				if(capturedPawn.isEmpty()) {
					capturedPawnsHBoxTop.getChildren().clear();
					return;
				}
				ImageView pawnView =new ImageView(Pawn.WHITE_PAWN_SPRITE);
				pawnView.setFitWidth(55);
				pawnView.setPreserveRatio(true);
				pawnView.setSmooth(true);
				pawnView.setCache(true);
				capturedPawnsHBoxTop.getChildren().add(pawnView);
				
			}
			
		});
		capturedPawnsHBoxTop.setAlignment(Pos.CENTER);
		root.setTop(capturedPawnsHBoxTop);
		
	}
	

	public static void buildGrid(GridPane board) {
		board.setGridLinesVisible(false);
		
		for (int i = 0; i < Pawn.NUM_COLS; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPercentWidth(100 / Pawn.NUM_COLS);
			board.getColumnConstraints().add(colConst);
		}
		
		for (int i = 0; i < Pawn.NUM_COLS; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPercentHeight(100 / Pawn.NUM_COLS);
			board.getRowConstraints().add(rowConst);
		}
		board.setMaxSize(600, 600);
	}

	
	public static void initGrid(GridPane board,Stage primaryStage) {
		gameController.renderBoard(board, true, primaryStage);
	}
	
	public void showHighlightedCells(GridPane board, Stage primaryStage) {
		gameController.showHighlightedCells(board,primaryStage); 
	}
}