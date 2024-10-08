module com.example.cab302project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Include this if you're using JDBC for database connection
    requires org.kordamp.bootstrapfx.core;

    // Opening package for JavaFX to access the controllers
    opens com.example.cab302project to javafx.fxml;

    // Exporting the package for other modules to access
    exports com.example.cab302project;
}
