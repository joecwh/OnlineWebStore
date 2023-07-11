/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "ROLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r")
    , @NamedQuery(name = "Roles.findByRolesid", query = "SELECT r FROM Roles r WHERE r.rolesid = :rolesid")
    , @NamedQuery(name = "Roles.findByRolename", query = "SELECT r FROM Roles r WHERE r.rolename = :rolename")})
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ROLESID")
    private String rolesid;
    @Size(max = 255)
    @Column(name = "ROLENAME")
    private String rolename;

    public Roles() {
    }
    
    public Roles(String rolesid, String rolename) 
    {
        this.rolesid = rolesid;
        this.rolename = rolename;
    }

    public Roles(String rolename) 
    {
        UUID rid = UUID.randomUUID();
        String rolesid = rid.toString();
        
        this.rolesid = rolesid;
        this.rolename = rolename;
    }

    public String getRolesid() {
        return rolesid;
    }

    public void setRolesid(String rolesid) {
        this.rolesid = rolesid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolesid != null ? rolesid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.rolesid == null && other.rolesid != null) || (this.rolesid != null && !this.rolesid.equals(other.rolesid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Roles[ rolesid=" + rolesid + " ]";
    }
    
}
