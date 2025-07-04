/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.jvn.interfaces;

import java.sql.Date;

import br.com.jvn.models.*;

/**
 *
 * @author vitor
 */
public interface InterfaceSistema {

    public Pessoa CriarPessoa(int id, int idade, String nome, String email, String tel);

    public boolean AlterarPessoa(int id, String mudanca, int escolha);

    public boolean RemoverPessoa(int id, int tipoPessoa);

    public Pessoa BuscarPessoa(String name, String tel, int option);

    public Dentista BuscarDentistaPorId(int id);

    public Paciente BuscarPacientePorId(int id);

    public Pessoa GetPessoaOnPOS(int pos);

    public int GetNextPessoaID();

    public Agendamento Agendar(int id, Date data, Dentista dentist, Paciente pacient);

    public Historico GerarHistorico(int id, Dentista dentist, Paciente pacient, Agendamento agenda);

    public int GetIDDentista();

    public int GetIDPaciente();

    public int GetAgendamento();
}
