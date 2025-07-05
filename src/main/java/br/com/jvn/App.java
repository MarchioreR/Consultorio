package br.com.jvn;

import br.com.jvn.db.DataAccessObject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import br.com.jvn.db.FactoryConnection;
import br.com.jvn.models.Dentista;
import br.com.jvn.models.Paciente;
import br.com.jvn.models.Pessoa;
import br.com.jvn.models.Sistema;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false); //Impede redimensionamento
        stage.initStyle(StageStyle.UTILITY); //Remove botão de maximizar

        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws SQLException, RemoteException {

        Sistema s = new Sistema();
        ArrayList<Pessoa> pessoas = DataAccessObject.carregarPessoasDoBanco();
        s.pessoas = pessoas;
        for (Pessoa pessoa : pessoas) {
            System.out.print(pessoa.getId() + pessoa.getNome() + " ");
        }
        s.AlterarDentista(1,"33",2);
        for (Pessoa pessoa : pessoas) {
            System.out.print(pessoa.getId() + pessoa.getNome() + " ");
        }
        System.out.println();
        launch();
    }

}
