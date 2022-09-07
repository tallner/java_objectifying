package application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainView {
	
	private Stage stage;
	private ProductView productView;
	
	public MainView() {
		buildUI();
		
	}

	private void buildUI() {
		
		
		stage = new Stage(StageStyle.DECORATED);
	
		BorderPane root = new BorderPane();
		
		productView = new ProductView();
		
		root.setCenter(productView);
		
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setTitle("Display XML file in a table");
		stage.setScene(scene);
		stage.show();
	
		
	}

}
