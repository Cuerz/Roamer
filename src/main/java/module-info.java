module com.example.infosearch {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.yaml.snakeyaml;
    requires java.desktop;
    requires fastjson;


    opens com.example.infosearch to javafx.fxml;
    exports com.example.infosearch;
    exports com.example.infosearch.config;
}
