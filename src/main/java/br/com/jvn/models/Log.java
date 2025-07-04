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
public class Log {
    private int id;
    private String usuario;
    private Date data_hora;
    private String comando_sql;

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
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the data_hora
     */
    public Date getData_hora() {
        return data_hora;
    }

    /**
     * @param data_hora the data_hora to set
     */
    public void setData_hora(Date data_hora) {
        this.data_hora = data_hora;
    }

    /**
     * @return the comando_sql
     */
    public String getComando_sql() {
        return comando_sql;
    }

    /**
     * @param comando_sql the comando_sql to set
     */
    public void setComando_sql(String comando_sql) {
        this.comando_sql = comando_sql;
    }
}
