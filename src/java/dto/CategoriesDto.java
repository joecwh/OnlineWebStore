/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Lenovo
 */
public class CategoriesDto 
{
    private String categoriesid;
    private String categoryname;
    private String categorydesc;
    private Serializable productimg;

    public CategoriesDto() {
    }

    public CategoriesDto(String categoriesid, String categoryname, String categorydesc, Serializable productimg) 
    {
        this.categoriesid = categoriesid;
        this.categoryname = categoryname;
        this.categorydesc = categorydesc;
        this.productimg = productimg;
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
    
    public Serializable getProductimg() {
        return productimg;
    }

    public void setProductimg(Serializable productimg) {
        this.productimg = productimg;
    }
}
