module com.example.accountingofgoods {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;
    requires org.apache.httpcomponents.httpmime;


    opens com.example.accountingofgoods to javafx.fxml;
    exports com.example.accountingofgoods;
    exports com.example.accountingofgoods.ui;
    opens com.example.accountingofgoods.ui to javafx.fxml;

}