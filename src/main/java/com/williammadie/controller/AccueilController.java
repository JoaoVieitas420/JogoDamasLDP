package com.williammadie.controller;
import com.williammadie.model.Player;
import javafx.scene.control.Alert;
public class AccueilController {
	
	public void initNamePlayer(String name1, String name2) {
		Player.initPlayers(name1, name2);
	}
	
	public void isEmpty() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Input Error");
	    alert.setContentText("Some players don't have a name!");
	    alert.showAndWait();
	}
}
