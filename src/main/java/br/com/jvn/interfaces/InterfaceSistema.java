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

    public boolean AlterarPessoa(int id);

    public boolean RemoverPessoa(int id);

    public void BuscarPessoa();

    public Pessoa GetPessoaOnPOS(int pos);

    public Agendamento Agendar(int id, Date data, Dentista dentist, Paciente pacient);

    public Historico GerarHistorico(int id, Dentista dentist, Paciente pacient, Agendamento agenda);

    public int GetIDDentista();

    public int GetIDPaciente();

    public int GetAgendamento();

    public int GetIDPessoa(int id);
}
