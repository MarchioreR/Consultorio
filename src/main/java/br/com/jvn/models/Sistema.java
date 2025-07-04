/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.jvn.models;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import br.com.jvn.interfaces.InterfaceSistema;

/**
 *
 * @author vitor
 */
public class Sistema implements InterfaceSistema {

    private ArrayList<Pessoa> pessoas = new ArrayList<>();

    public Sistema() throws RemoteException {
        super(); // Call the parent class constructor
    }

    public Pessoa CriarPessoa(int id, int idade, String nome, String email, String tel) {
        Pessoa novo = new Pessoa();
        novo.setEmail(email);
        novo.setIdade(idade);
        novo.setNome(nome);
        novo.setTel(tel);
        return novo;
    }

    public boolean AlterarPessoa(int id, String mudanca, int escolha) {
        Pessoa p = GetPessoaOnPOS(id);
        switch (escolha) {
            case 1 -> {
                p.setNome(mudanca);
                break;
            }
            case 2 -> {
                p.setIdade(Integer.parseInt(mudanca));
            }
            case 3 -> {
                p.setEmail(mudanca);
            }
            case 4 -> {
                p.setTel(mudanca);
            }
        }
        return false;
    }

    public Pessoa GetPessoaOnPOS(int pos) {
        Pessoa pessoa = pessoas.get(pos);
        return pessoa;
    }

    public boolean RemoverPessoa(int id) {
        if (pessoas.get(id) != null) {
            return true;
        }
        return false;
    }

    public void BuscarPessoa() {
    }

    public Agendamento Agendar(int id, Date data, Dentista dentist, Paciente pacient) {
        return null;
    }

    public Historico GerarHistorico(int id, Dentista dentist, Paciente pacient, Agendamento agenda) {
        return null;
    }

    public int GetIDDentista() {
        return 0;
    }

    public int GetIDPaciente() {
        return 0;
    }

    public int GetAgendamento() {
        return 0;
    }

    public int GetIDPessoa(int id) {
        return id;
    }

    @Override
    public boolean AlterarPessoa(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
