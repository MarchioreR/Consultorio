/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.jvn.models;

import java.util.Date;

/**
 *
 * @author vitor
 */
public class Agendamento {
    
    private int id;
    private Date data;
    private Dentista dentist;
    private Paciente pacient;

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
}
