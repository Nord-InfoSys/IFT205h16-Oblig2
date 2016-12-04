/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig3;

import java.io.Serializable;

/**
 *
 * @author mortenj
 */
public class ModelBase implements Serializable {
    
    private Integer dbid = null;

    public Integer getDbid() {
        return dbid;
    }

    public void setDbid(Integer dbid) {
        this.dbid = dbid;
    }
    
}
