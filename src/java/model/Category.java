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
@Table(name = "CATEGORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
    , @NamedQuery(name = "Category.findByCategoryid", query = "SELECT c FROM Category c WHERE c.categoryid = :categoryid")
    , @NamedQuery(name = "Category.findByCategoriesid", query = "SELECT c FROM Category c WHERE c.categoriesid = :categoriesid")
    , @NamedQuery(name = "Category.findByProductid", query = "SELECT c FROM Category c WHERE c.productid = :productid")})
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CATEGORYID")
    private String categoryid;
    @Size(max = 255)
    @Column(name = "CATEGORIESID")
    private String categoriesid;
    @Size(max = 255)
    @Column(name = "PRODUCTID")
    private String productid;

    public Category() {
    }
    
    public Category(String categoriesid, String productid) 
    {
        UUID uuid = UUID.randomUUID();
        String categoryid = uuid.toString();
        
        this.categoryid = categoryid;
        this.categoriesid = categoriesid;
        this.productid = productid;
    }

    public Category(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoriesid() {
        return categoriesid;
    }

    public void setCategoriesid(String categoriesid) {
        this.categoriesid = categoriesid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryid != null ? categoryid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.categoryid == null && other.categoryid != null) || (this.categoryid != null && !this.categoryid.equals(other.categoryid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Category[ categoryid=" + categoryid + " ]";
    }
    
}
