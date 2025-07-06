/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.jvn.models;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import br.com.jvn.interfaces.InterfaceSistema;
import br.com.jvn.db.DataAccessObject;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vitor
 */
public class Sistema implements InterfaceSistema {

    public ArrayList<Pessoa> pessoas = new ArrayList<>();
    public ArrayList<Agendamento> agendas = new ArrayList<>();
    public ArrayList<Historico> historicos = new ArrayList<>();
    public ArrayList<Prontuario> prontuarios = new ArrayList<>();
    public String USER = "";
    public String PASS = "";

    public Sistema(String USER, String PASS) throws RemoteException, SQLException {
        super();
        this.USER = USER;
        this.PASS = PASS;
    }

    public Sistema() {
    }

    public void CriarListaPessoa() throws SQLException {
        try {
            pessoas = DataAccessObject.carregarPessoasDoBanco(USER, PASS);
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
    }

    public void CriarListaAgendamento() throws SQLException {
        try {
            agendas = DataAccessObject.carregarAgendamentosDoBanco(pessoas, USER, PASS);
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
    }

    public void CriarListaProntuario() throws SQLException {
        try {
            prontuarios = DataAccessObject.carregarProntuariosDoBanco(pessoas, agendas, USER, PASS);
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
    }

    public void CriarListaHistorico() throws SQLException {
        try {
            historicos = DataAccessObject.carregarHistoricosDoBanco(pessoas, agendas, USER, PASS);
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
    }

    public void CriarListaViewAgendamentosHoje() throws SQLException {
        try {
            agendas = DataAccessObject.carregarViewAgendamentosHoje(pessoas, USER, PASS);
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
    }

    public void CriarListaViewAgendamentoPaciente() throws SQLException {
        try {
            agendas = DataAccessObject.carregarAgendamentosDoBanco(pessoas, USER, PASS);
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
        /*
        ESSA VIEW TEM QUE MOSTRAR O ID, DATA e HORARIO DO AGENDAMENTO + TAMBÉM MOSTRAR O ID, NOME, EMAIL e TELEFONE DO PACIENTE
         */
    }

    public void CriarDentista(String nome, int idade, String email, String tel) {
        int id = GetNextDentistaID();
        Dentista d = new Dentista(id, idade, nome, email, tel);
        pessoas.add(d);
        try {
            DataAccessObject.inserirDentista(d, USER, PASS);
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
    }

    public void CriarPaciente(String nome, int idade, String email, String tel) {
        int id = GetNextPacienteID();
        Paciente p = new Paciente(id, idade, nome, email, tel);
        pessoas.add(p);
        try {
            DataAccessObject.inserirPaciente(p, USER, PASS);
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
    }

    public void CriarAgendamento(Date data, Time horario, Dentista dentist, Paciente pacient) {
        int id;
        if (agendas.isEmpty()) {
            id = 0;
        } else {
            id = agendas.getLast().getId() + 1;
        }
        Agendamento a = new Agendamento(id, data, horario, dentist, pacient);
        agendas.add(a);
        try {
            DataAccessObject.inserirAgendamento(a, USER, PASS);
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
    }

    public void CriarProntuario(String relatorio, Agendamento agenda, Dentista dentist, Paciente pacient) {
        int id;
        if (prontuarios.isEmpty()) {
            id = 0;
        } else {
            id = prontuarios.getLast().getId() + 1;
        }
        Prontuario pr = new Prontuario(id, relatorio, agenda, dentist, pacient);
        prontuarios.add(pr);
        try {
            DataAccessObject.inserirProntuario(pr, USER, PASS);
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
    }

    public void CriarHistorico(Agendamento agenda, Dentista dentist, Paciente pacient) {
        int id;
        if (prontuarios.isEmpty()) {
            id = 0;
        } else {
            id = prontuarios.getLast().getId() + 1;
        }
        Historico h = new Historico(id, dentist, pacient, agenda);
        historicos.add(h);
        try {
            DataAccessObject.inserirHistorico(h, USER, PASS);
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
    }

    public boolean AlterarDentista(int id, String mudanca, int escolha) throws SQLException {
        Dentista d = BuscarDentistaPorId(id);
        switch (escolha) {
            case 1 -> {
                d.setNome(mudanca);
                try {
                    DataAccessObject.atualizarDentista(d, mudanca, escolha, USER, PASS);
                } catch (SQLException e) {
                    if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                        System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
                    } else {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                }
                return true;
            }
            case 2 -> {
                d.setIdade(Integer.parseInt(mudanca));
                try {
                    DataAccessObject.atualizarDentista(d, mudanca, escolha, USER, PASS);
                } catch (SQLException e) {
                    if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                        System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
                    } else {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                }
                return true;
            }
            case 3 -> {
                d.setEmail(mudanca);
                try {
                    DataAccessObject.atualizarDentista(d, mudanca, escolha, USER, PASS);
                } catch (SQLException e) {
                    if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                        System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
                    } else {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                }
                return true;
            }
            case 4 -> {
                d.setTel(mudanca);
                try {
                    DataAccessObject.atualizarDentista(d, mudanca, escolha, USER, PASS);
                } catch (SQLException e) {
                    if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                        System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
                    } else {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                }
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public boolean AlterarPaciente(int id, String mudanca, int escolha) throws SQLException {
        Paciente p = BuscarPacientePorId(id);
        switch (escolha) {
            case 1 -> {
                p.setNome(mudanca);
                try {
                    // example: trying to delete a Dentista
                    DataAccessObject.atualizarPaciente(p, mudanca, escolha, USER, PASS);
                } catch (SQLException e) {
                    if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                        System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
                    } else {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                    // Opcional: log completo para análise
                    e.printStackTrace();
                }
                return true;
            }
            case 2 -> {
                p.setIdade(Integer.parseInt(mudanca));
                try {
                    // example: trying to delete a Dentista
                    DataAccessObject.atualizarPaciente(p, mudanca, escolha, USER, PASS);
                } catch (SQLException e) {
                    if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                        System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
                    } else {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                    // Opcional: log completo para análise
                    e.printStackTrace();
                }
                return true;
            }
            case 3 -> {
                p.setEmail(mudanca);
                try {
                    // example: trying to delete a Dentista
                    DataAccessObject.atualizarPaciente(p, mudanca, escolha, USER, PASS);
                } catch (SQLException e) {
                    if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                        System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
                    } else {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                    // Opcional: log completo para análise
                    e.printStackTrace();
                }
                return true;
            }
            case 4 -> {
                p.setTel(mudanca);
                try {
                    // example: trying to delete a Dentista
                    DataAccessObject.atualizarPaciente(p, mudanca, escolha, USER, PASS);
                } catch (SQLException e) {
                    if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                        System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
                    } else {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                    // Opcional: log completo para análise
                    e.printStackTrace();
                }
                return true;
            }
            default -> {
                return false;
            }
        }
    }

//  Consegue o objeto Pessoa em uma posição escolhida da tabela na view
    public Pessoa GetPessoaOnPOS(int pos) {
        Pessoa pessoa = pessoas.get(pos);
        return pessoa;
    }

    public int GetNextPacienteID() {
        Iterator<Pessoa> iterator = pessoas.iterator();
        int maxid = 0;
        while (iterator.hasNext()) {
            Pessoa pessoa = iterator.next();
            if (pessoa instanceof Paciente) {
                maxid = pessoa.getId();
            }
        }
        return maxid + 1;
    }

    public int GetNextDentistaID() {
        Iterator<Pessoa> iterator = pessoas.iterator();
        int maxid = 0;
        while (iterator.hasNext()) {
            Pessoa pessoa = iterator.next();
            if (pessoa instanceof Dentista) {
                maxid = pessoa.getId();
            }
        }
        return maxid + 1;
    }

    public boolean RemoverDentista(int id) {
        Iterator<Pessoa> iterator = pessoas.iterator();
        while (iterator.hasNext()) {
            Pessoa pessoa = iterator.next();
            if (pessoa instanceof Dentista && pessoa.getId() == id) {
                try {
                    DataAccessObject.deletarDentista((Dentista) pessoa, USER, PASS);
                    iterator.remove();
                    return true;
                } catch (SQLException e) {
                    if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                        System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
                    } else {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                }
            }
        }
        return false;
    }

    public boolean RemoverPaciente(int id) {
        Iterator<Pessoa> iterator = pessoas.iterator();
        while (iterator.hasNext()) {
            Pessoa pessoa = iterator.next();
            if (pessoa instanceof Paciente && pessoa.getId() == id) {
                try {
                    DataAccessObject.deletarPaciente((Paciente) pessoa, USER, PASS);
                    iterator.remove();
                    return true;
                } catch (SQLException e) {
                    if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                        System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
                    } else {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                }
            }
        }
        return false;
    }

    public boolean RemoverAgendamento(int id) {
        Agendamento a = BuscarAgendamentoPorId(id);
        try {
            DataAccessObject.deletarAgendamento(agendas.get(id), USER, PASS);
            agendas.remove(a);
            return true;
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
        return false;
    }

    public boolean RemoverProntuario(int id) {
        Prontuario pr = BuscarProntuarioPorId(id);
        try {
            DataAccessObject.deletarAgendamento(agendas.get(id), USER, PASS);
            prontuarios.remove(pr);
            return true;
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
        return false;
    }
    
    public boolean RemoverHistorico(int id) {
        Historico h = BuscarHistoricoPorId(id);
        try {
            DataAccessObject.deletarHistorico(h, USER, PASS);
            historicos.remove(h);
            return true;
        } catch (SQLException e) {
            if (e.getSQLState().equals("42000") || e.getMessage().toLowerCase().contains("access denied")) {
                System.out.println("Permissão negada: você não tem acesso para executar essa operação.");
            } else {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            }
        }
        return false;
    }

    public Pessoa BuscarPessoa(String busca, int option) {
        for (Pessoa pessoa : pessoas) {
            switch (option) {
                case 1 -> {
                    if (pessoa.getNome().equalsIgnoreCase(busca)) {
                        return pessoa;
                    }
                }
                case 2 -> {
                    if (pessoa.getTel().equals(busca)) {
                        return pessoa;
                    }
                }
                default -> {
                    return null;
                }
            }
        }
        return null;
    }

    public Paciente BuscarPacientePorId(int id) {
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Paciente && pessoa.getId() == id) {
                return (Paciente) pessoa;
            }
        }
        return null;
    }

    public Dentista BuscarDentistaPorId(int id) {
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Dentista && pessoa.getId() == id) {
                return (Dentista) pessoa;
            }
        }
        return null;
    }

    public Agendamento BuscarAgendamentoPorId(int id) {
        for (Agendamento agenda : agendas) {
            if (agenda.getId() == id) {
                return agenda;
            }
        }
        return null;
    }

    public Prontuario BuscarProntuarioPorId(int id) {
        for (Prontuario pront : prontuarios) {
            if (pront.getId() == id) {
                return pront;
            }
        }
        return null;
    }
    
    public Historico BuscarHistoricoPorId(int id) {
        for (Historico hist : historicos) {
            if (hist.getId() == id) {
                return hist;
            }
        }
        return null;
    }
}
