package br.com.jvn.db;

import br.com.jvn.models.Agendamento;
import br.com.jvn.models.Dentista;
import br.com.jvn.models.Historico;
import br.com.jvn.models.Paciente;
import br.com.jvn.models.Pessoa;
import br.com.jvn.models.Prontuario;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class DataAccessObject {

    public static ArrayList<Pessoa> carregarPessoasDoBanco(String USER, String PASS) throws SQLException {
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        Connection conn = FactoryConnection.createConnection(USER, PASS);

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

    public static ArrayList<Agendamento> carregarAgendamentosDoBanco(ArrayList<Pessoa> pessoas, String USER, String PASS) throws SQLException {
        ArrayList<Agendamento> agendamentos = new ArrayList<>();
        Connection conn = FactoryConnection.createConnection(USER, PASS);

        String sql = "SELECT * FROM Agendamento";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Date data = rs.getDate("data");
                Time horario = rs.getTime("horario");
                int idDentista = rs.getInt("id_dentista");
                int idPaciente = rs.getInt("id_paciente");

                Dentista dentista = null;
                Paciente paciente = null;

                for (Pessoa p : pessoas) {
                    if (p instanceof Dentista && p.getId() == idDentista) {
                        dentista = (Dentista) p;
                    } else if (p instanceof Paciente && p.getId() == idPaciente) {
                        paciente = (Paciente) p;
                    }
                }

                if (dentista != null && paciente != null) {
                    Agendamento agendamento = new Agendamento(id, data, horario, dentista, paciente);
                    agendamentos.add(agendamento);
                } else {
                    System.out.println("Dentista ou Paciente não encontrado para agendamento ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar agendamentos do banco: " + e.getMessage());
        }
        return agendamentos;
    }

    public static ArrayList<Prontuario> carregarProntuariosDoBanco(ArrayList<Pessoa> pessoas, ArrayList<Agendamento> agenda, String USER, String PASS) throws SQLException {
        ArrayList<Prontuario> prontuarios = new ArrayList<>();
        Connection conn = FactoryConnection.createConnection(USER, PASS);

        String sql = "SELECT * FROM Prontuario";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String relatorio = rs.getString("relatorio");
                int idAgendamento = rs.getInt("id_agendamento");
                int idDentista = rs.getInt("id_dentista");
                int idPaciente = rs.getInt("id_paciente");

                Agendamento agendamento = null;
                Dentista dentista = null;
                Paciente paciente = null;

                for (Pessoa p : pessoas) {
                    if (p instanceof Dentista && p.getId() == idDentista) {
                        dentista = (Dentista) p;
                    } else if (p instanceof Paciente && p.getId() == idPaciente) {
                        paciente = (Paciente) p;
                    }
                }
                for (Agendamento a : agenda) {
                    if (a.getId() == idAgendamento) {
                        agendamento = a;
                    }
                }

                if (dentista != null && paciente != null && agendamento != null) {
                    Prontuario prontuario = new Prontuario(id, relatorio, agendamento, dentista, paciente);
                    prontuarios.add(prontuario);
                } else {
                    System.out.println("Dentista ou Paciente não encontrado para agendamento ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar prontuarios do banco: " + e.getMessage());
        }
        return prontuarios;
    }

    public static ArrayList<Historico> carregarHistoricosDoBanco(ArrayList<Pessoa> pessoas, ArrayList<Agendamento> agenda, String USER, String PASS) throws SQLException {
        ArrayList<Historico> historicos = new ArrayList<>();
        Connection conn = FactoryConnection.createConnection(USER, PASS);

        String sql = "SELECT * FROM Historico";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int idAgendamento = rs.getInt("id_agendamento");
                int idDentista = rs.getInt("id_dentista");
                int idPaciente = rs.getInt("id_paciente");

                Agendamento agendamento = null;
                Dentista dentista = null;
                Paciente paciente = null;

                for (Pessoa p : pessoas) {
                    if (p instanceof Dentista && p.getId() == idDentista) {
                        dentista = (Dentista) p;
                    } else if (p instanceof Paciente && p.getId() == idPaciente) {
                        paciente = (Paciente) p;
                    }
                }
                for (Agendamento a : agenda) {
                    if (a.getId() == idAgendamento) {
                        agendamento = a;
                    }
                }

                if (dentista != null && paciente != null && agendamento != null) {
                    Historico historico = new Historico(id, dentista, paciente, agendamento);
                    historicos.add(historico);
                } else {
                    System.out.println("Dentista ou Paciente não encontrado para agendamento ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar historicos do banco: " + e.getMessage());
        }
        return historicos;
    }

    public static ArrayList<Agendamento> carregarViewAgendamentosHoje(ArrayList<Pessoa> pessoas, String USER, String PASS) throws SQLException {
        ArrayList<Agendamento> agendamentos = new ArrayList<>();
        Connection conn = FactoryConnection.createConnection(USER, PASS);

        String sql = "SELECT * FROM agendamentos_hoje";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Date data = rs.getDate("data");
                Time horario = rs.getTime("horario");
                int idDentista = rs.getInt("id_dentista");
                int idPaciente = rs.getInt("id_paciente");

                Dentista dentista = null;
                Paciente paciente = null;

                for (Pessoa p : pessoas) {
                    if (p instanceof Dentista && p.getId() == idDentista) {
                        dentista = (Dentista) p;
                    } else if (p instanceof Paciente && p.getId() == idPaciente) {
                        paciente = (Paciente) p;
                    }
                }

                if (dentista != null && paciente != null) {
                    Agendamento agendamento = new Agendamento(id, data, horario, dentista, paciente);
                    agendamentos.add(agendamento);
                } else {
                    System.out.println("Dentista ou Paciente não encontrado para agendamento ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar agendamentos do banco: " + e.getMessage());
        }
        return agendamentos;
    }

    public static void inserirDentista(Dentista d, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
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

    public static void inserirPaciente(Paciente p, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
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

    public static void inserirAgendamento(Agendamento a, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "INSERT INTO Agendamento (id, data, horario, id_dentista, id_paciente) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, a.getId());
            stmt.setDate(2, a.getData());
            stmt.setTime(3, a.getHorario());
            stmt.setInt(4, a.getDentist().getId());
            stmt.setInt(5, a.getPacient().getId());
            stmt.executeUpdate();
        }
    }

    public static void inserirProntuario(Prontuario p, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "INSERT INTO Agendamento (id, data, id_agendamento, id_dentista, id_paciente) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getRelatorio());
            stmt.setInt(3, p.getAgenda().getId());
            stmt.setInt(4, p.getDentista().getId());
            stmt.setInt(5, p.getPaciente().getId());
            stmt.executeUpdate();
        }
    }

    public static void inserirHistorico(Historico h, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "INSERT INTO Historico (id, id_agendamento, id_dentista, id_paciente) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, h.getId());
            stmt.setInt(2, h.getAgenda().getId());
            stmt.setInt(3, h.getDentist().getId());
            stmt.setInt(4, h.getPacient().getId());
            stmt.executeUpdate();
        }
    }

    public static void deletarDentista(Dentista d, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "DELETE FROM Dentista WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, d.getId());
            stmt.executeUpdate();
        }
    }

    public static void deletarPaciente(Paciente p, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "DELETE FROM Paciente WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
        }
    }

    public static void deletarAgendamento(Agendamento a, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "DELETE FROM Agendamento WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();
        }
    }

    public static void deletarProntuario(Prontuario p, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "DELETE FROM Prontuario WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
        }
    }

    public static void deletarHistorico(Historico h, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "DELETE FROM Historico WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, h.getId());
            stmt.executeUpdate();
        }
    }

    public static void atualizarDentista(Dentista d, String change, int option, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
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

    public static void atualizarPaciente(Paciente p, String change, int option, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
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
