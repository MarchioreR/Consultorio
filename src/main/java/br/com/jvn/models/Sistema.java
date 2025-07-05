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
    public ArrayList<Log> logs = new ArrayList<>();

    public Sistema() throws RemoteException {
        super(); // Call the parent class constructor
    }

    public ArrayList<Pessoa> CriarListaPessoa() throws SQLException {
        pessoas = DataAccessObject.carregarPessoasDoBanco();
        return pessoas;
    }

    public void CriarDentista(String nome, int idade, String email, String tel) {
        int id = GetNextIDDentista();
        Dentista d = new Dentista(id, idade, nome, email, tel);
        pessoas.add(d);
        try {
            DataAccessObject.inserirDentista(d);
        } catch (SQLException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean AlterarDentista(int id, String mudanca, int escolha) throws SQLException {
        Dentista d = BuscarDentistaPorId(id);
        switch (escolha) {
            case 1 -> {
                d.setNome(mudanca);
                DataAccessObject.atualizarDentista(d, mudanca, escolha);
                return true;
            }
            case 2 -> {
                d.setIdade(Integer.parseInt(mudanca));
                DataAccessObject.atualizarDentista(d, mudanca, escolha);
                return true;
            }
            case 3 -> {
                d.setEmail(mudanca);
                DataAccessObject.atualizarDentista(d, mudanca, escolha);
                return true;
            }
            case 4 -> {
                d.setTel(mudanca);
                DataAccessObject.atualizarDentista(d, mudanca, escolha);
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
                DataAccessObject.atualizarPaciente(p, mudanca, escolha);
                return true;
            }
            case 2 -> {
                p.setIdade(Integer.parseInt(mudanca));
                DataAccessObject.atualizarPaciente(p, mudanca, escolha);
                return true;
            }
            case 3 -> {
                p.setEmail(mudanca);
                DataAccessObject.atualizarPaciente(p, mudanca, escolha);
                return true;
            }
            case 4 -> {
                p.setTel(mudanca);
                DataAccessObject.atualizarPaciente(p, mudanca, escolha);
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
                    DataAccessObject.deletarDentista((Dentista) pessoa);
                    iterator.remove();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
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
                    DataAccessObject.deletarPaciente((Paciente) pessoa);
                    iterator.remove();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
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

    public Agendamento Agendar(int id, Date data, Dentista dentist, Paciente pacient) {
        return null;
    }

    public Historico GerarHistorico(int id, Dentista dentist, Paciente pacient, Agendamento agenda) {
        return null;
    }

    public int GetNextIDDentista() {
        int maxId = -1;
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Dentista) {
                if (pessoa.getId() > maxId) {
                    maxId = pessoa.getId();
                }
            }
        }
        return maxId + 1;
    }

    public int GetNextIDPaciente() {
        int maxId = -1;
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Paciente) {
                if (pessoa.getId() > maxId) {
                    maxId = pessoa.getId();
                }
            }
        }
        return maxId + 1;
    }

    public int GetAgendamento() {
        return 0;
    }
}
