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

    public boolean AlterarPessoa(ArrayList<Pessoa> pessoas, int id, String mudanca, int escolha);

    public boolean RemoverDentista(ArrayList<Pessoa> pessoas, int id);

    public Pessoa BuscarPessoa(ArrayList<Pessoa> pessoas, String busca, int option);

    public Dentista BuscarDentistaPorId(ArrayList<Pessoa> pessoas, int id);

    public Paciente BuscarPacientePorId(ArrayList<Pessoa> pessoas, int id);

    public Pessoa GetPessoaOnPOS(ArrayList<Pessoa> pessoas, int pos);

    public int GetNextDentistaID(ArrayList<Pessoa> pessoas);

    public int GetNextPacienteID(ArrayList<Pessoa> pessoas);

    public void CriarDentista(ArrayList<Pessoa> pessoas, String nome, int idade, String email, String tel);

    public Agendamento Agendar(int id, Date data, Dentista dentist, Paciente pacient);

    public Historico GerarHistorico(int id, Dentista dentist, Paciente pacient, Agendamento agenda);

    public int GetNextIDDentista(ArrayList<Pessoa> pessoas);

    public int GetNextIDPaciente(ArrayList<Pessoa> pessoas);

    public int GetAgendamento();

    public ArrayList<Pessoa> CriarListaPessoa() throws SQLException;
}
