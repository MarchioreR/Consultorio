package br.com.jvn;

import br.com.jvn.db.DataAccessObject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import br.com.jvn.db.FactoryConnection;
import br.com.jvn.models.Agendamento;
import br.com.jvn.models.Dentista;
import br.com.jvn.models.Paciente;
import br.com.jvn.models.Pessoa;
import br.com.jvn.models.Prontuario;
import br.com.jvn.models.Sistema;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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

    public static void main(String[] args) throws SQLException, RemoteException, Exception {

        Sistema s = new Sistema(/*LOGIN */"admin_Geral",/*Senha */ "");
        /*
        EXEMPLO DE COMO ALTERAR UM AGENDAMENTO
        s.CriarListaPessoa();
        s.CriarListaAgendamento();

        for (Agendamento agenda : s.agendas) {
            System.out.print(agenda.getPacient().getNome() + " ");
            System.out.println();
        }
        s.AlterarAgendamento(1, "2", 4);
        for (Agendamento agenda : s.agendas) {
            System.out.print(agenda.getPacient().getNome()+ " ");
            System.out.println();
        }
        
        
        /////\
        SEQUENCIA PARA VER A VIEW AGENDAMENTOS HOJE
        
        PRA UMA PESSOA QUE SÓ TEM PERMISSÃO DE LEITURA, PRA ELA PODER VER UMA VIEW TEM QUE 
        PRIMEIRO CRIAR TODAS AS LISTAS QUE ESTÃO ENVOLVIDAS NA VIEW E DEPOIS 
        DAR SELECT NA VIEW
        
        s.CriarListaPessoa();
        s.CriarListaAgendamento();
        s.CriarListaViewAgendamentosHoje();
               
        
        ///////////
        s.CriarListaPessoa();
        s.CriarListaAgendamento();
        s.CriarListaViewAgendamentoPaciente();
        for (Agendamento agenda : s.agendas) {
            System.out.print(agenda.getDentist().getNome() + " " + agenda.getData() + " " + agenda.getHorario() + " ");
            System.out.println();
        }
        
        
        SEQUENCIA PARA VER A VIEW AGENDAMENTO PACIENTE
        
        PRA UMA PESSOA QUE SÓ TEM PERMISSÃO DE LEITURA, PRA ELA PODER VER UMA VIEW TEM QUE 
        PRIMEIRO CRIAR TODAS AS LISTAS QUE ESTÃO ENVOLVIDAS NA VIEW E DEPOIS 
        DAR SELECT NA VIEW
        
        

        s.CriarListaPessoa();
        s.CriarListaAgendamento();
        s.CriarListaHistorico();

        for (Prontuario pront : s.prontuarios) {
            System.out.print(pront.getDentista().getId() + " " + pront.getRelatorio() + " " + pront.getPaciente().getId() + " ");
            System.out.println();
        }
         */
        launch();
    }

}
