/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.jvn.models;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author vitor
 */
public class Agendamento {

    private int id;
    private Date data;
    private Time horario;
    private Dentista dentist;
    private Paciente pacient;

    public Agendamento() {
    }

    public Agendamento(int id, Date data, Time horario, Dentista dentist, Paciente pacient) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.dentist = dentist;
        this.pacient = pacient;
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
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
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
     * @return the horario
     */
    public Time getHorario() {
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(Time horario) {
        this.horario = horario;
    }
}
