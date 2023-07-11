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
@Table(name = "ROLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
    , @NamedQuery(name = "Role.findByRoleid", query = "SELECT r FROM Role r WHERE r.roleid = :roleid")
    , @NamedQuery(name = "Role.findByRolesid", query = "SELECT r FROM Role r WHERE r.rolesid = :rolesid")
    , @NamedQuery(name = "Role.findByUserid", query = "SELECT r FROM Role r WHERE r.userid = :userid")})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ROLEID")
    private String roleid;
    @Size(max = 255)
    @Column(name = "ROLESID")
    private String rolesid;
    @Size(max = 255)
    @Column(name = "USERID")

    private String userid;

    
    public Role(String rolesid, String userid) 
    {
        UUID rid = UUID.randomUUID();
        String roleid = rid.toString();
        
        this.roleid = roleid;
        this.rolesid = rolesid;
        this.userid = userid;
    }
    
    public Role() {
    }

    public Role(String roleid) {
        this.roleid = roleid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolesid() {
        return rolesid;
    }

    public void setRolesid(String rolesid) {
        this.rolesid = rolesid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleid != null ? roleid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.roleid == null && other.roleid != null) || (this.roleid != null && !this.roleid.equals(other.roleid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Role[ roleid=" + roleid + " ]";
    }
    
}
