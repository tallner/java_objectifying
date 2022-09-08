package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import views.MainView;


public class ObjectifyingAssignment extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		new MainView();
	}

}
