/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.jvn.models;

/**
 *
 * @author vitor
 */
public class Prontuario {
    private int id;
    private String relatorio;
    private Agendamento agenda;
    private Dentista dentista;
    private Paciente paciente;

    public Prontuario(int id, String relatorio, Agendamento agenda, Dentista dentista, Paciente paciente) {
        this.id = id;
        this.relatorio = relatorio;
        this.agenda = agenda;
        this.dentista = dentista;
        this.paciente = paciente;
    }

    public Prontuario() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the relatorio
     */
    public String getRelatorio() {
        return relatorio;
    }

    /**
     * @param relatorio the relatorio to set
     */
    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    /**
     * @return the agenda
     */
    public Agendamento getAgenda() {
        return agenda;
    }

    /**
     * @param agenda the agenda to set
     */
    public void setAgenda(Agendamento agenda) {
        this.agenda = agenda;
    }

    /**
     * @return the dentista
     */
    public Dentista getDentista() {
        return dentista;
    }

    /**
     * @param dentista the dentista to set
     */
    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    /**
     * @return the paciente
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

}
