package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane tabPane = (TabPane)FXMLLoader.load(this.getClass().getResource("Main.fxml"));
			Scene scene = new Scene(tabPane);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Exemplo Procedure JavaFX");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
