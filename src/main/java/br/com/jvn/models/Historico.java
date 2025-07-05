/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.jvn.models;

/**
 *
 * @author vitor
 */
public class Historico {

    private int id;
    private Dentista dentist;
    private Paciente pacient;
    private Agendamento agenda;

    public Historico() {
    }

    public Historico(int id, Dentista dentist, Paciente pacient, Agendamento agenda) {
        this.id = id;
        this.dentist = dentist;
        this.pacient = pacient;
        this.agenda = agenda;
    }

    /**
     * @return the dentist
     */
    public Dentista getDentist() {
        return dentist;
    }

    /**
     * @param dentist the dentist to set
     */
    public void setDentist(Dentista dentist) {
        this.dentist = dentist;
    }

    /**
     * @return the pacient
     */
    public Paciente getPacient() {
        return pacient;
    }

    /**
     * @param pacient the pacient to set
     */
    public void setPacient(Paciente pacient) {
        this.pacient = pacient;
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

}
