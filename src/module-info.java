module helo {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	requires java.sql;
	requires json.simple;
	requires Java.WebSocket;
	requires javafx.media;
	requires java.rmi;
	requires jdk.httpserver;
	requires java.net.http;

	opens application to javafx.graphics, javafx.fxml;
	opens Controller to javafx.fxml; // This line opens the Controller package to FXMLLoader

	exports View;
	exports Controller; // Add this line
	exports Model;

}
