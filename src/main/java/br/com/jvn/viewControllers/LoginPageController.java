package br.com.jvn.viewControllers;

import java.io.IOException;
import java.rmi.Naming;
import java.util.List;
import java.util.function.UnaryOperator;

import br.com.jvn.App;
import br.com.jvn.util.UtilityClient;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

public class LoginPageController {

    @FXML
    private TextField tf_usuario;

    @FXML
    private TextField tf_senha;
    private String user;
    private String senha;
    
    @FXML
    public void initialize(){
        try {
            
            TextFormatter<String> textFormatter = new TextFormatter<>(filter);
            tf_usuario.setTextFormatter(textFormatter);
            
            //CONEXAO BD

        } catch (Exception e) {
            System.err.println("Erro de conexão com o servidor");
            e.printStackTrace();
        }
    }

    // Criando um filtro para aceitar apenas números
    UnaryOperator<Change> filter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("\\d*")) { 
            return change;
        }
        return null;
    };

    @FXML
    private void login() throws IOException {
        
        user = tf_usuario.getText();
        senha = tf_senha.getText();
        /*if(!user.isBlank() && !senha.isBlank()){
            int id = controller.loginFuncionario(tf_usuario.getText(), tf_senha.getText());
            Utility.setFuncionarioId(id);
            if (id>0) {
                char type = controller.getFuncaoFuncionario(user);
                if (type == 'A') {
                    App.setRoot("adm");
                } else if(type == 'B'){
                    App.setRoot("barManager");
                }else if (type == 'C'){
                    App.setRoot("salesBooth");
                } else{
                    System.err.println("Erro ao obter função");
                }
            } else{
                UtilityClient.showError("Usuário ou senha incorretos!");
            }
        }else{
            UtilityClient.showError("Não é permitido campos vazios!");
        }
        */
        
        
    }
}
