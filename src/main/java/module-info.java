module errorists {
    exports errorists;
    exports errorists.controllers;
    exports errorists.models;

    opens errorists.controllers to javafx.fxml;
    opens errorists.models to javafx.base;

    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires transitive  javafx.graphics;
    
}
