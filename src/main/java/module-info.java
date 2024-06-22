module com.example.sistemacabeleleiro {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires java.sql;
    requires sqlite.jdbc;


    opens com.example.sistemacabeleleiro.application.controller to javafx.fxml;
    opens com.example.sistemacabeleleiro.application.view to javafx.fxml;

    exports com.example.sistemacabeleleiro;
}