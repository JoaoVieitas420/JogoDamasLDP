package com.williammadie.view;

import com.williammadie.controller.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import com.williammadie.model.Pawn;


public class AccueilView {
	private static AccueilController accueilController = new AccueilController();
	
	public static void initView(StackPane gameSettingsRoot, Stage primaryStage, Scene gameScene) {
		gameSettingsRoot.setStyle("-fx-background-color: white;");
		
		// Create the Play button
		Button button1 = new Button("PLAY !");

		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(20, 0, 0, 0));
		vbox.setAlignment(Pos.TOP_CENTER);

		// Title
		Label label = new Label("CHECKERS");
		label.setFont(Font.font("Arial", FontWeight.BOLD, 26));
		label.setStyle("-fx-text-fill: #A569BD;");
		vbox.getChildren().add(label);
		vbox.getChildren().add(new Label(" "));


		
		// Player 1 Info
		Label labelPlayer1 = new Label("Player 1: ");
		TextField tfPlayer1 = new TextField();
		labelPlayer1.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		
		
		// Player 1 Pawn Image
		Image image = Pawn.PURPLE_PAWN_SPRITE;
		
		ImageView imageView1 = new ImageView(image);
		imageView1.setFitHeight(20);
		imageView1.setFitWidth(20);
		HBox hboxPlayer1 = new HBox(labelPlayer1 ,tfPlayer1,imageView1);
		hboxPlayer1.setSpacing(20);
		hboxPlayer1.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hboxPlayer1);
		
		
		// Player 2 Info
		Label labelPlayer2 = new Label("Player 2: ");
		TextField tfPlayer2 = new TextField();
		labelPlayer2.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		
		
		// Player 2 Pawn Image
		Image image2 = Pawn.WHITE_PAWN_SPRITE;
		ImageView imageView2 = new ImageView(image2);
		imageView2.setFitHeight(20);
		imageView2.setFitWidth(20);
		HBox hboxPlayer2 = new HBox(labelPlayer2 ,tfPlayer2,imageView2);
		hboxPlayer2.setSpacing(20);
		hboxPlayer2.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hboxPlayer2);
		vbox.getChildren().add(new Label(" "));
		
		vbox.getChildren().add(button1);
 
		gameSettingsRoot.getChildren().addAll(vbox);

		// Play Button Style
		button1.setStyle("-fx-background-color: #F6DDCC;-fx-text-fill: #A569BD;");
		button1.setPrefWidth(150);
		button1.setPrefHeight(50);
		button1.setFont(Font.font("Arial", FontWeight.BOLD, 15));

		button1.setOnAction(e -> {
			if (tfPlayer1.getText().isEmpty() || tfPlayer2.getText().isEmpty()) {
				accueilController.isEmpty();
			  } else {
			    accueilController.initNamePlayer(tfPlayer1.getText(), tfPlayer2.getText());
			    primaryStage.setScene(gameScene);
			  }
		});
		
		
	}
}
