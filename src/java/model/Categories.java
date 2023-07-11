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
@Table(name = "CATEGORIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categories.findAll", query = "SELECT c FROM Categories c")
    , @NamedQuery(name = "Categories.findByCategoriesid", query = "SELECT c FROM Categories c WHERE c.categoriesid = :categoriesid")
    , @NamedQuery(name = "Categories.findByCategoryname", query = "SELECT c FROM Categories c WHERE c.categoryname = :categoryname")
    , @NamedQuery(name = "Categories.findByCategorydesc", query = "SELECT c FROM Categories c WHERE c.categorydesc = :categorydesc")})
public class Categories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CATEGORIESID")
    private String categoriesid;
    @Size(max = 255)
    @Column(name = "CATEGORYNAME")
    private String categoryname;
    @Size(max = 255)
    @Column(name = "CATEGORYDESC")
    private String categorydesc;

    public Categories() {
    }
    
    public Categories(String categoriesid, String categoryname, String categorydesc) 
    {
        this.categoriesid = categoriesid;
        this.categoryname = categoryname;
        this.categorydesc = categorydesc;
    }

    public Categories(String categoryname, String categorydesc) 
    {
        UUID uuid = UUID.randomUUID();
        String categoriesid = uuid.toString();
        
        this.categoriesid = categoriesid;
        this.categoryname = categoryname;
        this.categorydesc = categorydesc;
    }

    public String getCategoriesid() {
        return categoriesid;
    }

    public void setCategoriesid(String categoriesid) {
        this.categoriesid = categoriesid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCategorydesc() {
        return categorydesc;
    }

    public void setCategorydesc(String categorydesc) {
        this.categorydesc = categorydesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoriesid != null ? categoriesid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categories)) {
            return false;
        }
        Categories other = (Categories) object;
        if ((this.categoriesid == null && other.categoriesid != null) || (this.categoriesid != null && !this.categoriesid.equals(other.categoriesid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Categories[ categoriesid=" + categoriesid + " ]";
    }
    
}
