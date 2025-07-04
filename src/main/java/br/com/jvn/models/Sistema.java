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
import java.util.Iterator;

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

    public Pessoa CriarPessoa(int idade, String nome, String email, String tel) {
        int id = GetNextPessoaID();
        Pessoa novo = new Pessoa(id, idade, nome, email, tel);
        pessoas.add(novo);
        /* DAO*/
        return novo;
    }

    public boolean AlterarPessoa(int id, String mudanca, int escolha) {
        Pessoa p = GetPessoaOnPOS(id);
        switch (escolha) {
            case 1 -> {
                p.setNome(mudanca);
                /*IF DAO Funciona retorna true*/            }
            case 2 -> {
                p.setIdade(Integer.parseInt(mudanca));
                /*IF DAO Funciona retorna true*/            }
            case 3 -> {
                p.setEmail(mudanca);
                /*IF DAO Funciona retorna true*/            }
            case 4 -> {
                p.setTel(mudanca);
                /*IF DAO Funciona retorna true*/            }
            default -> {
                return false;
            }
        }
        return false;
    }
//  Consegue o objeto Pessoa em uma posição escolhida da tabela na view

    public Pessoa GetPessoaOnPOS(int pos) {
        Pessoa pessoa = pessoas.get(pos);
        return pessoa;
    }

    public int GetNextPessoaID() {
        return pessoas.size();
    }

    public boolean RemoverPessoa(int id, int tipoPessoa) {
        Iterator<Pessoa> iterator = pessoas.iterator();
        switch (tipoPessoa) {
            case 1 -> {
                while (iterator.hasNext()) {
                    Pessoa pessoa = iterator.next();
                    if (pessoa instanceof Paciente && pessoa.getId() == id) {
                        iterator.remove(); // Safe removal during iteration
                    }
                }
            }
            case 2 -> {

                while (iterator.hasNext()) {
                    Pessoa pessoa = iterator.next();
                    if (pessoa instanceof Dentista && pessoa.getId() == id) {
                        iterator.remove(); // Safe removal during iteration
                    }
                }
                return true;
            }
            default -> {
                return false;
            }
        }
        return false;
    }

    public Pessoa BuscarPessoa(String name, String tel, int option) {
        for (Pessoa pessoa : pessoas) {
            switch (option) {
                case 1 -> {
                    if (pessoa.getNome().equalsIgnoreCase(name)) {
                        return pessoa;
                    }
                }
                case 2 -> {
                    if (pessoa.getTel().equals(tel)) {
                        return pessoa;
                    }
                }
                default -> {
                    System.out.println("Opção inválida.");
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
