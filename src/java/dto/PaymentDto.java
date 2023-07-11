/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author Lenovo
 */
public class PaymentDto 
{
    private String cartid;
    private Serializable productimg;
    private String productname;
    private int quantity;
    private double total;
    private double shippingfee;
    private double taxes;
    private double finaltotal;

    public PaymentDto() {
    }
    
    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    public Serializable getProductImg() {
        return productimg;
    }

    public void setProductImg(Serializable productimg) {
        this.productimg = productimg;
    }

    public String getProductName() {
        return productname;
    }

    public void setProductName(String productname) {
        this.productname = productname;
    }
    
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    
    public Double getShippingfee() {
        return shippingfee;
    }

    public void setShippingfee(Double shippingfee) {
        this.shippingfee = shippingfee;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }
    
    public Double getFinaltotal() {
        return finaltotal;
    }

    public void setFinaltotal(Double finaltotal) {
        this.finaltotal = finaltotal;
    }


}
