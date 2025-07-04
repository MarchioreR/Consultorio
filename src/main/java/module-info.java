module br.com.jvn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.rmi;

    opens br.com.jvn to javafx.fxml;
    opens br.com.jvn.viewControllers to javafx.fxml;
    exports br.com.jvn;
    
    exports br.com.jvn.viewControllers;
}
