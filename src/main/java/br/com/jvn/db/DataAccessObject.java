package br.com.jvn.db;

import br.com.jvn.models.Agendamento;
import br.com.jvn.models.Dentista;
import br.com.jvn.models.Historico;
import br.com.jvn.models.Log;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DataAccessObject {

    public static ArrayList<Log> logs = new ArrayList<>();

    public DataAccessObject() throws SQLException {
        logs = carregarLogDoBanco("admin_Geral", "");
    }

    public static ArrayList<Pessoa> carregarPessoasDoBanco(String USER, String PASS) throws SQLException {
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sqlPaciente = "SELECT * FROM Paciente";
        String sqlDentista = "SELECT * FROM Dentista";

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sqlPaciente);
        Log log2 = new Log(idlog, USER, data_hora, sqlDentista);
        inserirLog(log1, USER, PASS);
        inserirLog(log2, USER, PASS);

        try (
                PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente); PreparedStatement stmtDentista = conn.prepareStatement(sqlDentista); ResultSet rsPaciente = stmtPaciente.executeQuery(); ResultSet rsDentista = stmtDentista.executeQuery()) {
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

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

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

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

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

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

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

    public static ArrayList<Log> carregarLogDoBanco(String USER, String PASS) throws SQLException {
        ArrayList<Log> logs_ = new ArrayList<>();
        Connection conn = FactoryConnection.createConnection(USER, PASS);

        String sql = "SELECT * FROM log_transacoes";

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

        try (
                PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String usuario = rs.getString("usuario");
                Timestamp data_hora_ = rs.getTimestamp("data_hora");
                String comando_sql = rs.getString("comando_sql");

                Log log = new Log(id, usuario, data_hora_, comando_sql);
                logs_.add(log);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar historicos do banco: " + e.getMessage());
        }
        return logs_;
    }

    public static ArrayList<Agendamento> carregarViewAgendamentosHoje(ArrayList<Pessoa> pessoas, String USER, String PASS) throws SQLException {
        ArrayList<Agendamento> agendamentos = new ArrayList<>();
        Connection conn = FactoryConnection.createConnection(USER, PASS);

        String sql = "SELECT * FROM agendamentos_hoje";

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

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

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

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

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

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

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

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

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

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

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, h.getId());
            stmt.setInt(2, h.getAgenda().getId());
            stmt.setInt(3, h.getDentist().getId());
            stmt.setInt(4, h.getPacient().getId());
            stmt.executeUpdate();
        }
    }

    public static void inserirLog(Log l, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "INSERT INTO log_transacoes (id, usuario, data_hora, comando_sql) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, l.getId());
            stmt.setString(2, l.getUsuario());
            stmt.setTimestamp(3, l.getData_hora());
            stmt.setString(4, l.getComando_sql());
            stmt.executeUpdate();
        }
    }

    public static void deletarDentista(Dentista d, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "DELETE FROM Dentista WHERE id = ?";

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, d.getId());
            stmt.executeUpdate();
        }
    }

    public static void deletarPaciente(Paciente p, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "DELETE FROM Paciente WHERE id = ?";

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
        }
    }

    public static void deletarAgendamento(Agendamento a, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "DELETE FROM Agendamento WHERE id = ?";

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();
        }
    }

    public static void deletarProntuario(Prontuario p, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "DELETE FROM Prontuario WHERE id = ?";

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
        }
    }

    public static void deletarHistorico(Historico h, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "DELETE FROM Historico WHERE id = ?";

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, h.getId());
            stmt.executeUpdate();
        }
    }

    public static void deletarLog(Log l, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = "DELETE FROM log_transacoes WHERE id = ?";

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = new Log(idlog, USER, data_hora, sql);
        inserirLog(log1, USER, PASS);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, l.getId());
            stmt.executeUpdate();
        }
    }

    public static void atualizarDentista(Dentista d, String change, int option, String USER, String PASS) throws SQLException {
        Connection conn = FactoryConnection.createConnection(USER, PASS);
        String sql = null;

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = null;

        switch (option) {
            case 1 -> {
                sql = "UPDATE Dentista SET nome = ? WHERE id = ?";
                log1 = new Log(idlog, USER, data_hora, sql);
                inserirLog(log1, USER, PASS);
                break;
            }
            case 2 -> {
                sql = "UPDATE Dentista SET idade = ? WHERE id = ?";
                log1 = new Log(idlog, USER, data_hora, sql);
                inserirLog(log1, USER, PASS);
                break;
            }
            case 3 -> {
                sql = "UPDATE Dentista SET email = ? WHERE id = ?";
                log1 = new Log(idlog, USER, data_hora, sql);
                inserirLog(log1, USER, PASS);
                break;
            }
            case 4 -> {
                sql = "UPDATE Dentista SET telefone = ? WHERE id = ?";
                log1 = new Log(idlog, USER, data_hora, sql);
                inserirLog(log1, USER, PASS);
                break;
            }
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

        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        int idlog = 0;
        if (logs.isEmpty()) {
            idlog = 0;
        } else {
            idlog = logs.getLast().getId() + 1;
        }
        Log log1 = null;

        switch (option) {
            case 1 -> {
                sql = "UPDATE Paciente SET nome = ? WHERE id = ?";
                log1 = new Log(idlog, USER, data_hora, sql);
                inserirLog(log1, USER, PASS);
                break;
            }
            case 2 -> {
                sql = "UPDATE Paciente SET idade = ? WHERE id = ?";
                log1 = new Log(idlog, USER, data_hora, sql);
                inserirLog(log1, USER, PASS);
                break;
            }
            case 3 -> {
                sql = "UPDATE Paciente SET email = ? WHERE id = ?";
                log1 = new Log(idlog, USER, data_hora, sql);
                inserirLog(log1, USER, PASS);
                break;
            }
            case 4 -> {
                sql = "UPDATE Paciente SET telefone = ? WHERE id = ?";
                log1 = new Log(idlog, USER, data_hora, sql);
                inserirLog(log1, USER, PASS);
                break;
            }
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

    public static void atualizarAgendamento(Agendamento a, String change, int option, String USER, String PASS) throws SQLException {
        if (option < 1 || option > 4) {
            System.out.println("Opção inválida.");
            return;
        }

        String[] campos = {"data", "horario", "id_dentista", "id_paciente"};
        String campo = campos[option - 1];
        String sql = "UPDATE Agendamento SET " + campo + " = ? WHERE id = ?";

        int idlog = logs.isEmpty() ? 0 : logs.getLast().getId() + 1;
        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        Log log = new Log(idlog, USER, data_hora, sql);
        inserirLog(log, USER, PASS);

        try (Connection conn = FactoryConnection.createConnection(USER, PASS); PreparedStatement stmt = conn.prepareStatement(sql)) {

            switch (option) {
                case 1 ->
                    stmt.setDate(1, convertStringToSqlDate(change));
                case 2 ->
                    stmt.setTime(1, convertStringToSqlTime(change));
                case 3, 4 ->
                    stmt.setInt(1, Integer.parseInt(change));
            }

            stmt.setInt(2, a.getId());
            stmt.executeUpdate();
            System.out.println("Atualizacao realizada com sucesso.");

        } catch (NumberFormatException e) {
            System.out.println("Erro: valor inválido para número.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar agendamento: " + e.getMessage());
        }
    }

    public static void atualizarProntuario(Prontuario pront, String change, int option, String USER, String PASS) throws SQLException {
        if (option < 1 || option > 4) {
            System.out.println("Opção inválida.");
            return;
        }

        String[] campos = {"relatorio", "id_agendamento", "id_dentista", "id_paciente"};
        String campo = campos[option - 1];
        String sql = "UPDATE Prontuario SET " + campo + " = ? WHERE id = ?";

        int idlog = logs.isEmpty() ? 0 : logs.getLast().getId() + 1;
        Timestamp data_hora = new Timestamp(System.currentTimeMillis());
        Log log = new Log(idlog, USER, data_hora, sql);
        inserirLog(log, USER, PASS);

        try (Connection conn = FactoryConnection.createConnection(USER, PASS); PreparedStatement stmt = conn.prepareStatement(sql)) {

            switch (option) {
                case 1 ->
                    stmt.setString(1, change);
                case 2, 3, 4 ->
                    stmt.setInt(1, Integer.parseInt(change));
            }

            stmt.setInt(2, pront.getId());
            stmt.executeUpdate();
            System.out.println("Atualizacao realizada com sucesso.");

        } catch (NumberFormatException e) {
            System.out.println("Erro: valor inválido para número.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar Prontuario: " + e.getMessage());
        }
    }

    public static Time convertStringToSqlTime(String timeStr) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setLenient(false);
        java.util.Date utilDate = format.parse(timeStr);
        return new Time(utilDate.getTime());
    }

    public static Date convertStringToSqlDate(String dateStr) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        java.util.Date utilDate = format.parse(dateStr);
        return new Date(utilDate.getTime());
    }

}
