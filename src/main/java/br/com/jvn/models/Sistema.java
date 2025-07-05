/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.jvn.models;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import br.com.jvn.interfaces.InterfaceSistema;
import br.com.jvn.models.Dentista;
import br.com.jvn.models.Pessoa;
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

    private ArrayList<Pessoa> pessoas = new ArrayList<>();
    private ArrayList<Agendamento> agendas = new ArrayList<>();
    private ArrayList<Historico> historicos = new ArrayList<>();
    private ArrayList<Prontuario> prontuarios = new ArrayList<>();
    private ArrayList<Log> logs = new ArrayList<>();

    public Sistema() throws RemoteException {
        super(); // Call the parent class constructor
    }

    public ArrayList<Pessoa> CriarListaPessoa() throws SQLException {
        pessoas = DataAccessObject.carregarPessoasDoBanco();
        return pessoas;
    }

    public void CriarDentista(ArrayList<Pessoa> pessoas, String nome, int idade, String email, String tel) {
        int id = GetNextIDDentista(pessoas);
        Dentista d = new Dentista(id, idade, nome, email, tel);
        pessoas.add(d);
        try {
            DataAccessObject.inserirDentista(d);
        } catch (SQLException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean AlterarPessoa(ArrayList<Pessoa> pessoas, int id, String mudanca, int escolha) {
        Pessoa p = GetPessoaOnPOS(pessoas, id);
        switch (escolha) {
            case 1 -> {
                p.setNome(mudanca);
                /*IF DAO Funciona retorna true*/ return true;
            }
            case 2 -> {
                p.setIdade(Integer.parseInt(mudanca));
                /*IF DAO Funciona retorna true*/ return true;
            }
            case 3 -> {
                p.setEmail(mudanca);
                /*IF DAO Funciona retorna true*/ return true;
            }
            case 4 -> {
                p.setTel(mudanca);
                /*IF DAO Funciona retorna true*/
                return true;
            }
            default -> {
                return false;
            }
        }
    }

//  Consegue o objeto Pessoa em uma posição escolhida da tabela na view
    public Pessoa GetPessoaOnPOS(ArrayList<Pessoa> pessoas, int pos) {
        Pessoa pessoa = pessoas.get(pos);
        return pessoa;
    }

    public int GetNextPacienteID(ArrayList<Pessoa> pessoas) {
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

    public int GetNextDentistaID(ArrayList<Pessoa> pessoas) {
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

    public boolean RemoverDentista(ArrayList<Pessoa> pessoas, int id) {
        Iterator<Pessoa> iterator = pessoas.iterator();
        while (iterator.hasNext()) {
            System.out.println("Entrou no while");
            Pessoa pessoa = iterator.next();
            System.out.println("ID = " + id);
            if (pessoa instanceof Dentista && pessoa.getId() == id) {
                try {
                    System.out.println("FOUND");
                    DataAccessObject.deletarDentista((Dentista) pessoa);
                    iterator.remove(); // only remove if DB deletion succeeds
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        }
        return false;
    }

    /*
            // Paciente
            if (tipoPessoa == 1 && pessoa instanceof Paciente && pessoa.getId() == id) {
                try {
                    System.out.println("FOUND");
                    DataAccessObject.deletarPaciente((Paciente) pessoa);
                    iterator.remove();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }

            // Dentista*/
    public Pessoa BuscarPessoa(ArrayList<Pessoa> pessoas, String busca, int option) {
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

    public Paciente BuscarPacientePorId(ArrayList<Pessoa> pessoas, int id) {
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Paciente && pessoa.getId() == id) {
                return (Paciente) pessoa;
            }
        }
        return null;
    }

    public Dentista BuscarDentistaPorId(ArrayList<Pessoa> pessoas, int id) {
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

    public int GetNextIDDentista(ArrayList<Pessoa> pessoas) {
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

    public int GetNextIDPaciente(ArrayList<Pessoa> pessoas) {
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
