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

    public static ArrayList<Pessoa> carregarPessoasDoBanco() throws SQLException {
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
                String tel = rsPaciente.getString("telefone");

                Paciente paciente = new Paciente(id, idade, nome, email, tel);
                pessoas.add(paciente);
            }

            // Carregar Dentistas
            while (rsDentista.next()) {
                int id = rsDentista.getInt("id");
                int idade = rsDentista.getInt("idade");
                String nome = rsDentista.getString("nome");
                String email = rsDentista.getString("email");
                String tel = rsDentista.getString("telefone");

                Dentista dentista = new Dentista(id, idade, nome, email, tel);
                pessoas.add(dentista);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar pessoas do banco: " + e.getMessage());
        }

        return pessoas;
    }

    public static void inserirDentista(Dentista d) throws SQLException {
        Connection conn = FactoryConnection.createConnection();
        String sql = "INSERT INTO Dentista (id, nome, idade, email, telefone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, d.getId());
            stmt.setString(2, d.getNome());
            stmt.setInt(3, d.getIdade());
            stmt.setString(4, d.getEmail());
            stmt.setString(5, d.getTel());
            stmt.executeUpdate();
        }
    }
    
    public static void inserirPaciente(Paciente p) throws SQLException {
        Connection conn = FactoryConnection.createConnection();
        String sql = "INSERT INTO Paciente (id, nome, idade, email, telefone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getNome());
            stmt.setInt(3, p.getIdade());
            stmt.setString(4, p.getEmail());
            stmt.setString(5, p.getTel());
            stmt.executeUpdate();
        }
    }

    public static void deletarDentista(Dentista d) throws SQLException {
        Connection conn = FactoryConnection.createConnection();
        String sql = "DELETE FROM Dentista WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, d.getId());
            stmt.executeUpdate();
        }
    }

    public static void deletarPaciente(Paciente p) throws SQLException {
        Connection conn = FactoryConnection.createConnection();
        String sql = "DELETE FROM Paciente WHERE id > ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
        }
    }

    public static void atualizarDentista(Dentista d, String change, int option) throws SQLException {
        Connection conn = FactoryConnection.createConnection();
        String sql = null;

        switch (option) {
            case 1 ->
                sql = "UPDATE Dentista SET nome = ? WHERE id = ?";
            case 2 ->
                sql = "UPDATE Dentista SET idade = ? WHERE id = ?";
            case 3 ->
                sql = "UPDATE Dentista SET email = ? WHERE id = ?";
            case 4 ->
                sql = "UPDATE Dentista SET telefone = ? WHERE id = ?";
            default -> {
                System.out.println("Opção inválida.");
                return;
            }
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (option == 2) {
                stmt.setInt(1, Integer.parseInt(change));
            } else {
                stmt.setString(1, change);
            }

            stmt.setInt(2, d.getId());
            stmt.executeUpdate();
            System.out.println("Atualizacao realizada com sucesso.");
        } catch (NumberFormatException e) {
            System.out.println("Erro: valor inválido para idade.");
        }
    }

    public static void atualizarPaciente(Paciente p, String change, int option) throws SQLException {
        Connection conn = FactoryConnection.createConnection();
        String sql = null;

        switch (option) {
            case 1 ->
                sql = "UPDATE Paciente SET nome = ? WHERE id = ?";
            case 2 ->
                sql = "UPDATE Paciente SET idade = ? WHERE id = ?";
            case 3 ->
                sql = "UPDATE Paciente SET email = ? WHERE id = ?";
            case 4 ->
                sql = "UPDATE Paciente SET telefone = ? WHERE id = ?";
            default -> {
                System.out.println("Opção inválida.");
                return;
            }
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (option == 2) {
                stmt.setInt(1, Integer.parseInt(change));
            } else {
                stmt.setString(1, change);
            }

            stmt.setInt(2, p.getId());
            stmt.executeUpdate();
            System.out.println("Atualizacao realizada com sucesso.");
        } catch (NumberFormatException e) {
            System.out.println("Erro: valor inválido para idade.");
        }
    }
}
