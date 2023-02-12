package main.java.com.williammadie;

	
import com.williammadie.view.AccueilView;
import com.williammadie.view.GameView;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



public class Main extends Application {
	public static final int WIN_WIDTH_GAME = 1550;
	public static final int WIN_HEIGHT_GAME = 790;
	public static final int WIN_WIDTH_ACCUEIL = 300;
	public static final int WIN_HEIGHT_ACCUEIL = 270;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			// Game Scene
			BorderPane gameRoot = new BorderPane();
			GameView.initView(gameRoot, primaryStage);
			Scene gameScene = new Scene(gameRoot, WIN_WIDTH_GAME, WIN_HEIGHT_GAME);
			
			// Configure Game Settings Scene
			StackPane gameSettingsRoot = new StackPane();
			AccueilView.initView(gameSettingsRoot, primaryStage, gameScene);
			Scene gameSettingsScene = new Scene(gameSettingsRoot, WIN_WIDTH_ACCUEIL, WIN_HEIGHT_ACCUEIL);
			
			// Show the first scene
			primaryStage.setScene(gameSettingsScene);
			primaryStage.show();
			
			
			
			//icon
			Image icon = new Image(getClass().getResource("/oui.png").toExternalForm());
		    primaryStage.getIcons().add(icon);
		    primaryStage.setTitle("Checkers");
		    

		    Media music = new Media(getClass().getResource("/audio2.mp3").toExternalForm());
		    MediaPlayer mediaPlayer = new MediaPlayer(music);
		    mediaPlayer.setAutoPlay(false);

		    primaryStage.addEventFilter(ScrollEvent.SCROLL, event -> {
		        if (event.getDeltaY() > 0) {
		            mediaPlayer.play();
		        } else if (event.getDeltaY() < 0) {
		            mediaPlayer.stop();
		        }
		    });
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
