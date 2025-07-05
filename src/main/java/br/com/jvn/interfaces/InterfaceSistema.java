/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.jvn.interfaces;

import java.sql.Date;

import br.com.jvn.models.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author vitor
 */
public interface InterfaceSistema {

    public ArrayList<Pessoa> CriarListaPessoa() throws SQLException;

    public void CriarDentista(String nome, int idade, String email, String tel);

    public void CriarPaciente(String nome, int idade, String email, String tel);

    public boolean AlterarDentista(int id, String mudanca, int escolha) throws SQLException;

    public boolean AlterarPaciente(int id, String mudanca, int escolha) throws SQLException;

    public boolean RemoverDentista(int id);

    public boolean RemoverPaciente(int id);

    public Paciente BuscarPacientePorId(int id);

    public Dentista BuscarDentistaPorId(int id);

    public Pessoa GetPessoaOnPOS(int pos);

    public int GetNextPacienteID();

    public int GetNextDentistaID();

    public Pessoa BuscarPessoa(String busca, int option);

    public Agendamento Agendar(int id, Date data, Dentista dentist, Paciente pacient);

    public Historico GerarHistorico(int id, Dentista dentist, Paciente pacient, Agendamento agenda);

    public int GetNextIDDentista();

    public int GetNextIDPaciente();

    public int GetAgendamento();

}
