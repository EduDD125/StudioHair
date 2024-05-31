module com.example.sistemacabeleleiro {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;


    opens com.example.sistemacabeleleiro to javafx.fxml;
    exports com.example.sistemacabeleleiro;
}