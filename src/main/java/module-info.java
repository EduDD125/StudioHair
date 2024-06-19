module com.example.sistemacabeleleiro {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;


    opens com.example.sistemacabeleleiro.Application.controller to javafx.fxml;
    opens com.example.sistemacabeleleiro.Application.view to javafx.fxml;
    exports com.example.sistemacabeleleiro.Application.controller;
    exports com.example.sistemacabeleleiro.Application.view;
}