module AulaProcedureJFX {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.controller to javafx.fxml;
}
