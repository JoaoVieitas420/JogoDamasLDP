module TheCheckers {
	requires javax.fxml;
	requires javafx.controls;
	requires javafx.media;
	opens com.williammadie to javafx.graphics;
	exports com.williammadie;
}