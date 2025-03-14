module beds.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    opens beds.main to javafx.fxml;
	opens beds.main.controllers to javafx.fxml;

	requires beds.backend;

    exports beds.main;
}
