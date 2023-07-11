/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
@Table(name = "TEMPORARYPASSWORD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Temporarypassword.findAll", query = "SELECT t FROM Temporarypassword t")
    , @NamedQuery(name = "Temporarypassword.findById", query = "SELECT t FROM Temporarypassword t WHERE t.id = :id")
    , @NamedQuery(name = "Temporarypassword.findByEmail", query = "SELECT t FROM Temporarypassword t WHERE t.email = :email")
    , @NamedQuery(name = "Temporarypassword.findByTempass", query = "SELECT t FROM Temporarypassword t WHERE t.tempass = :tempass")
    , @NamedQuery(name = "Temporarypassword.findByExpiredat", query = "SELECT t FROM Temporarypassword t WHERE t.expiredat = :expiredat")})
public class Temporarypassword implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ID")
    private String id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 255)
    @Column(name = "TEMPASS")
    private String tempass;
    @Column(name = "EXPIREDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredat;

    public Temporarypassword() {
    }

    public Temporarypassword(String email) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTimestamp = LocalDateTime.now();
        LocalDateTime expirationDateTime = currentTimestamp.plus(15, ChronoUnit.MINUTES);
        
        this.id = id;
        this.email = email;
        this.tempass = id.substring(0, 8);
        this.expiredat = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTempass() {
        return tempass;
    }

    public void setTempass(String tempass) {
        this.tempass = tempass;
    }

    public Date getExpiredat() {
        return expiredat;
    }

    public void setExpiredat(Date expiredat) {
        this.expiredat = expiredat;
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
        if (!(object instanceof Temporarypassword)) {
            return false;
        }
        Temporarypassword other = (Temporarypassword) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Temporarypassword[ id=" + id + " ]";
    }
    
}
