/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.List;
import model.*;

/**
 *
 * @author Lenovo
 */
public class OrderDto 
{
    private Orders order;
    private Shipping shipping;
    private Transactions transaction;
    private List<CartDto> carts;

    public OrderDto(){}
    
    public OrderDto(Orders order, Shipping shipping, Transactions transaction, List<CartDto> carts)
    {
        this.order = order;
        this.shipping = shipping;
        this.transaction = transaction;
        this.carts = carts;
    }

    public Orders getOrder()
    {
        return order;
    }
    
    public void setOrder(Orders order)
    {
        this.order = order;
    }
    
    public Shipping getShipping()
    {
        return shipping;
    }
    
    public void setShipping(Shipping shipping)
    {
        this.shipping = shipping;
    }
    
    public Transactions getTransactions()
    {
        return transaction;
    }
    
    public void setTransactions(Transactions transaction)
    {
        this.transaction = transaction;
    }
        
    public List<CartDto> getCart()
    {
        return carts;
    }
    
    public void setCart(List<CartDto> carts)
    {
        this.carts = carts;
    }
}
