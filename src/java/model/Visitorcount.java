/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "VISITORCOUNT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visitorcount.findAll", query = "SELECT v FROM Visitorcount v")
    , @NamedQuery(name = "Visitorcount.findById", query = "SELECT v FROM Visitorcount v WHERE v.id = :id")
    , @NamedQuery(name = "Visitorcount.findByTime", query = "SELECT v FROM Visitorcount v WHERE v.time = :time")})
public class Visitorcount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ID")
    private String id;
    @Column(name = "TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Visitorcount() 
    {
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.id = UUID.randomUUID().toString();
        this.time = Timestamp.valueOf(currentDateTime);
    }

    public Visitorcount(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visitorcount)) {
            return false;
        }
        Visitorcount other = (Visitorcount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Visitorcount[ id=" + id + " ]";
    }
    
}
