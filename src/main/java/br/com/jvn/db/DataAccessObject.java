package br.com.jvn.db;

import br.com.jvn.models.Dentista;
import br.com.jvn.models.Paciente;
import br.com.jvn.models.Pessoa;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataAccessObject {

    public ArrayList<Pessoa> carregarPessoasDoBanco() throws SQLException {
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        Connection conn = FactoryConnection.createConnection();

        String sqlPaciente = "SELECT * FROM Paciente";
        String sqlDentista = "SELECT * FROM Dentista";

        try (
                PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente); PreparedStatement stmtDentista = conn.prepareStatement(sqlDentista); ResultSet rsPaciente = stmtPaciente.executeQuery(); ResultSet rsDentista = stmtDentista.executeQuery()) {
            // Carregar Pacientes
            while (rsPaciente.next()) {
                int id = rsPaciente.getInt("id");
                int idade = rsPaciente.getInt("idade");
                String nome = rsPaciente.getString("nome");
                String email = rsPaciente.getString("email");
                String tel = rsPaciente.getString("tel");

                Paciente paciente = new Paciente(id, idade, nome, email, tel);
                pessoas.add(paciente);
            }

            // Carregar Dentistas
            while (rsDentista.next()) {
                int id = rsDentista.getInt("id");
                int idade = rsDentista.getInt("idade");
                String nome = rsDentista.getString("nome");
                String email = rsDentista.getString("email");
                String tel = rsDentista.getString("tel");

                Dentista dentista = new Dentista(id, idade, nome, email, tel);
                pessoas.add(dentista);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar pessoas do banco: " + e.getMessage());
        }

        return pessoas;
    }

}
