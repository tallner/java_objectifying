module fxtest {
	requires javafx.controls;
	requires javafx.base;
	requires javafx.graphics;
	requires json.simple;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens models to javafx.base;
}
