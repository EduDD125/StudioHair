module com.example.sistemacabeleleiro {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sistemacabeleleiro to javafx.fxml;
    exports com.example.sistemacabeleleiro;
}