module beds.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens beds.main to javafx.fxml;
    
    exports beds.main;
}
