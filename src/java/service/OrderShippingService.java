/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Repository.IOrderShippingRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Orders;
import model.Shipping;

/**
 *
 * @author Lenovo
 */
public class OrderShippingService implements IOrderShippingRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    public OrderShippingService(
        UserTransaction utx,
        EntityManager em)
    {
        this.utx = utx;
        this.em = em;
    }

    @Override
    public List<Orders> GetAllOrder() {
        List<Orders> orders = em.createNamedQuery("Orders.findAll")
                .getResultList();
        
        return orders.isEmpty() ? new ArrayList() : orders;
    }

    @Override
    public List<Shipping> GetAllShipping() {
        List<Shipping> shippings = em.createNamedQuery("Shipping.findAll")
                .getResultList();
        
        return shippings.isEmpty() ? new ArrayList() : shippings;
    }

    @Override
    public Orders GetOrder(String orderid) {
        Orders order = em.find(Orders.class, orderid);
        return order == null ? null : order;
    }

    @Override
    public Shipping GetShipping(String shippingid) {
        Shipping shipping = em.find(Shipping.class, shippingid);
        return shipping == null ? null : shipping;
    }

    @Override
    public boolean AddOrder(Orders order) {
        try
        {
            utx.begin();
            em.persist(order);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("add order failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean AddShipping(Shipping shipping) {
        try
        {
            utx.begin();
            em.persist(shipping);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("add shipping failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean UpdateOrder(Orders order) {
        try
        {
            utx.begin();
            em.merge(order);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("add order failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean UpdateShipping(Shipping shipping) {
        try
        {
            utx.begin();
            em.merge(shipping);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("add shipping failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean DeleteOrder(String orderid) {
        Orders order = GetOrder(orderid);
        if(order != null)
        {
            try
            {
                utx.begin();
                Orders removeOrder = em.merge(order);
                em.remove(removeOrder);
                utx.commit();
                
                return true;
            }
            catch(Exception ex)
            {
                System.out.println("delete order failed : " + ex.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean DeleteShipping(String shippingid) {
        Shipping shipping = GetShipping(shippingid);
        if(shipping != null)
        {
            try
            {
                utx.begin();
                Shipping removeShipping = em.merge(shipping);
                em.remove(removeShipping);
                utx.commit();
                
                return true;
            }
            catch(Exception ex)
            {
                System.out.println("delete shipping failed : " + ex.getMessage());
            }
        }
        return false;
    }

    @Override
    public List<Orders> GetOrderByUserId(String userid) {
        List<Orders> orders = em.createNamedQuery("Orders.findByUserid")
                .setParameter("userid", userid)
                .getResultList();
        
        return orders.isEmpty() ? new ArrayList() : orders;
    }

}
