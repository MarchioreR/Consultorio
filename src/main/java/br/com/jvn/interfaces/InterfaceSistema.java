/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.jvn.interfaces;

import java.sql.Date;

import br.com.jvn.models.*;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author vitor
 */
public interface InterfaceSistema {

    public void CriarListaPessoa() throws SQLException;

    public void CriarListaAgendamento() throws SQLException;

    public void CriarListaProntuario() throws SQLException;

    public void CriarListaHistorico() throws SQLException;

    public void CriarDentista(String nome, int idade, String email, String tel);

    public void CriarPaciente(String nome, int idade, String email, String tel);

    public void CriarAgendamento(Date data, Time horario, Dentista dentist, Paciente pacient);

    public void CriarProntuario(String relatorio, Agendamento agenda, Dentista dentist, Paciente pacient);

    public void CriarHistorico(Agendamento agenda, Dentista dentist, Paciente pacient);

    public void CriarListaViewAgendamentosHoje() throws SQLException;

    public void CriarListaViewAgendamentoPaciente() throws SQLException;

    public boolean AlterarDentista(int id, String mudanca, int escolha) throws SQLException;

    public boolean AlterarPaciente(int id, String mudanca, int escolha) throws SQLException;

    public boolean AlterarAgendamento(int id, String mudanca, int escolha) throws SQLException, Exception;
    
    public boolean AlterarProntuario(int id, String mudanca, int escolha) throws SQLException, Exception;

    public Pessoa GetPessoaOnPOS(int pos);

    public int GetNextPacienteID();

    public int GetNextDentistaID();

    public boolean RemoverDentista(int id);

    public boolean RemoverPaciente(int id);

    public boolean RemoverAgendamento(int id);

    public boolean RemoverProntuario(int id);

    public boolean RemoverHistorico(int id);

    public Paciente BuscarPacientePorId(int id);

    public Dentista BuscarDentistaPorId(int id);

    public Agendamento BuscarAgendamentoPorId(int id);

    public Prontuario BuscarProntuarioPorId(int id);

    public Historico BuscarHistoricoPorId(int id);

    public Pessoa BuscarPessoa(String busca, int option);
}
