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
import javax.persistence.Lob;
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
@Table(name = "BANNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Banner.findAll", query = "SELECT b FROM Banner b")
    , @NamedQuery(name = "Banner.findByBannerid", query = "SELECT b FROM Banner b WHERE b.bannerid = :bannerid")
    , @NamedQuery(name = "Banner.findByBannername", query = "SELECT b FROM Banner b WHERE b.bannername = :bannername")
    , @NamedQuery(name = "Banner.findByBannerdesc", query = "SELECT b FROM Banner b WHERE b.bannerdesc = :bannerdesc")})
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "BANNERID")
    private String bannerid;
    @Size(max = 255)
    @Column(name = "BANNERNAME")
    private String bannername;
    @Size(max = 255)
    @Column(name = "BANNERDESC")
    private String bannerdesc;
    @Lob
    @Column(name = "BANNERIMG")
    private Serializable bannerimg;

    public Banner() {
    }
    
    public Banner(String bannername, String bannerdesc, Serializable bannerimg) 
    {
        UUID bid = UUID.randomUUID();
        String bannerid = bid.toString();
        
        this.bannerid = bannerid;
        this.bannername = bannername;
        this.bannerdesc = bannerdesc;
        this.bannerimg = bannerimg;
    }

    public Banner(String bannerid) {
        this.bannerid = bannerid;
    }

    public String getBannerid() {
        return bannerid;
    }

    public void setBannerid(String bannerid) {
        this.bannerid = bannerid;
    }

    public String getBannername() {
        return bannername;
    }

    public void setBannername(String bannername) {
        this.bannername = bannername;
    }

    public String getBannerdesc() {
        return bannerdesc;
    }

    public void setBannerdesc(String bannerdesc) {
        this.bannerdesc = bannerdesc;
    }

    public Serializable getBannerimg() {
        return bannerimg;
    }

    public void setBannerimg(Serializable bannerimg) {
        this.bannerimg = bannerimg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bannerid != null ? bannerid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Banner)) {
            return false;
        }
        Banner other = (Banner) object;
        if ((this.bannerid == null && other.bannerid != null) || (this.bannerid != null && !this.bannerid.equals(other.bannerid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Banner[ bannerid=" + bannerid + " ]";
    }
    
}
