package com.williammadie.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Game {
	private boolean hasStarted;
	private IntegerProperty numberOfPlayedTour;
	private StringProperty whoIsPlayingProperty;
	private ObjectProperty<Image> currentPawnProperty; 
	private Player whoIsPlaying;
	
	public Game() {
		this.hasStarted = false;
		this.numberOfPlayedTour = new SimpleIntegerProperty(0);
		this.whoIsPlayingProperty = new SimpleStringProperty("");
		this.currentPawnProperty = new SimpleObjectProperty<>();
		this.whoIsPlaying = null;
		if (Player.players != null)
			Player.resetPlayers();
	}
	
	public boolean hasStarted() {
		return this.hasStarted;
	}
	
	public void setHasStarted(boolean newValue) {
		this.hasStarted = newValue;
	}
	
	public Player getWhoIsPlaying() {
		return this.whoIsPlaying;
	}
	
	public StringProperty whoIsPlayingProperty() {
		return whoIsPlayingProperty;
	}
	
	public String getWhoIsPlayingPropertyValue() {
		return whoIsPlayingProperty.get();
	}
	
	public void setWhoIsPlaying(String value) {
		whoIsPlayingProperty.set(value);
	}
	
	
	public IntegerProperty numberOfTours() {
		return numberOfPlayedTour;
	}
	
	public int getNumberOfPlayedTour() {
		return numberOfPlayedTour.get();
	}
	
	public void incrementNumberOfPlayedTour() {
		numberOfPlayedTour.set(getNumberOfPlayedTour() + 1);
	}
	
	public ObjectProperty<Image> currentPawnProperty(){
		return this.currentPawnProperty;
	}
	
	public Image getCurrentPawnPropertyValue() {
		return this.currentPawnProperty.get();
	}
	
	public void setCurrentPawnPropertyValue(Image newImage) {
		currentPawnProperty.set(newImage);
	}
	
	public void restartGame() {
		this.hasStarted = false;
		this.numberOfPlayedTour.set(0);
		this.whoIsPlayingProperty.set("");
		this.whoIsPlaying = null;
		if (Player.players != null)
			Player.resetPlayers();
	}
	
	public void newTour() {
		this.incrementNumberOfPlayedTour();
		if (this.whoIsPlaying == null) {
			this.whoIsPlaying = Player.getPlayerWithColor(Color.WHITE);
			this.whoIsPlaying.playTour();
			this.setCurrentPawnPropertyValue(Pawn.WHITE_PAWN_SPRITE);
		} else {
			Player previousPlayer = this.whoIsPlaying;
			Player nextPlayer;
			
			if (this.whoIsPlaying.getPlayingColor().equals(Color.WHITE)) {
				 nextPlayer = Player.getPlayerWithColor(Color.PURPLE);
				 this.setCurrentPawnPropertyValue(Pawn.PURPLE_PAWN_SPRITE);
			} else {
				nextPlayer = Player.getPlayerWithColor(Color.WHITE);
				this.setCurrentPawnPropertyValue(Pawn.WHITE_PAWN_SPRITE);
			}
			this.whoIsPlaying = nextPlayer;
			previousPlayer.endTour();
			nextPlayer.playTour();
		}
		setWhoIsPlaying(this.whoIsPlaying.getName() + " is playing!");
		
	}
	
	public static Player getWinner() {
		if(Player.getNumberOfCapturedPawns(Color.WHITE)== 20) {
			return Player.getPlayerWithColor(Color.WHITE);
		}
		else if(Player.getNumberOfCapturedPawns(Color.PURPLE)== 20) {
			return Player.getPlayerWithColor(Color.PURPLE);				
		}
		else {
			return null;
		}
	}
}
