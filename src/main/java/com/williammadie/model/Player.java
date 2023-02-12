package com.williammadie.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public class Player {
	public static List<Player> players;
	public final static int NUMBER_OF_STARTING_PAWNS = 20;
	private String name;
	private Color playingColor;
	private int numberOfRemainingPawns;
	private boolean isPlaying;
	private static final ObservableList<Pawn> CAPTURED_PAWNS_FOR_WHITE = FXCollections.observableArrayList();
	private static final ObservableList<Pawn> CAPTURED_PAWNS_FOR_PURPLE = FXCollections.observableArrayList();
	
	public Player(String name, Color color) {
		this.name = name;
		this.playingColor = color;
		this.numberOfRemainingPawns = NUMBER_OF_STARTING_PAWNS;
		this.isPlaying = false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Color getPlayingColor() {
		return this.playingColor;
	}
	
	public int getNumberOfRemainingPawns() {
		return this.numberOfRemainingPawns;
	}
	
	public boolean isPlaying() {
		return this.isPlaying;
	}
	
	public void declarePawnLoss() {
		this.numberOfRemainingPawns--;
	}
	
	public void playTour() {
		this.isPlaying = true;
	}
	
	public void endTour() {
		this.isPlaying = false;
	}
	
	public void addCapturedPawn(Pawn capturedPawn) {
		if(capturedPawn.getColor().equals(Color.WHITE)){
			CAPTURED_PAWNS_FOR_PURPLE.add(capturedPawn);	
		}else {
			CAPTURED_PAWNS_FOR_WHITE.add(capturedPawn);
		}
			
	}
	
	public static ObservableList<Pawn> getCapturedPawn(Color color) {
		return Color.WHITE.equals(color) ? CAPTURED_PAWNS_FOR_WHITE : CAPTURED_PAWNS_FOR_PURPLE;
	}
	
	public static int getNumberOfCapturedPawns(Color color) {
		return Color.WHITE.equals(color) ? CAPTURED_PAWNS_FOR_WHITE.size() : CAPTURED_PAWNS_FOR_PURPLE.size();
	}
	
	public static void initPlayers(String name1, String name2) {
		players = new ArrayList<>();
		Player p1 = new Player(name1, Color.PURPLE);
		Player p2 = new Player(name2, Color.WHITE);
		players.add(p1);
		players.add(p2);
		
	}
	
	public static void resetPlayers() {
		CAPTURED_PAWNS_FOR_PURPLE.clear();
		CAPTURED_PAWNS_FOR_WHITE.clear();
	}
	
	public static Player getPlayerWithColor(Color color) {
		return players.stream().filter(player -> player.getPlayingColor() == color).collect(Collectors.toList()).get(0);
	}
	
}