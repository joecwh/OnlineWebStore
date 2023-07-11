/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Repository.ICartRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Cart;

/**
 *
 * @author Lenovo
 */
public class CartService implements ICartRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    public CartService(UserTransaction utx, EntityManager em)
    {
        this.utx = utx;
        this.em = em;
    }
    
    @Override
    public boolean AddCart(Cart cart) {
        try
        {
            utx.begin();
            em.persist(cart);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("add cart failed : " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Cart> GetCartByUserId(String userid) {
        try
        {
            List<Cart> carts = em.createQuery("SELECT c FROM Cart c WHERE c.userid = :userid AND c.ispaid = false")
                    .setParameter("userid", userid)
                    .getResultList();
            
            if(!carts.isEmpty())
                return carts;
        }
        catch(Exception ex)
        {
            System.out.println("get cart failed by user id : " + ex.getMessage());
        }
        return new ArrayList();
    }
    
    @Override
    public List<Cart> GetCartByProductId(String productid) {
        try
        {
            List<Cart> carts = em.createNamedQuery("Cart.findByProductid")
                    .setParameter("productid", productid)
                    .getResultList();
            
            if(!carts.isEmpty())
                return carts;
        }
        catch(Exception ex)
        {
            System.out.println("get cart failed by product id : " + ex.getMessage());
        }
        return new ArrayList();
    }

    @Override
    public boolean UpdateCart(Cart cart) {
        try
        {
            utx.begin();
            em.merge(cart);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("update cart failed : " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean DeleteCart(String cartid) {
        try
        {
            Cart cart = em.find(Cart.class, cartid);
            
            utx.begin();
            Cart removeCart = em.merge(cart);
            em.remove(removeCart);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("delete cart failed : " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Cart> GetCarts() {
        List<Cart> carts = em.createNamedQuery("Cart.findAll").getResultList();
        if(carts.isEmpty())
            return new ArrayList();
        
        return carts;
    }

    @Override
    public Cart GetCartByCartId(String cartid) {
        try
        {
            Cart cart = em.find(Cart.class, cartid);
            if(cart != null)
                return cart;
        }
        catch(Exception ex)
        {
            System.out.println("get cart failed : " + ex.getMessage());
        }
        return null;
    }

    @Override
    public Cart GetCartByUserProductId(String userid, String productid) {
        try
        {
            List<Cart> carts = em.createNamedQuery("Cart.findByUserid")
                    .setParameter("userid", userid)
                    .getResultList();
            
            if(!carts.isEmpty())
            {
                for(Cart c : carts)
                {
                    if(c.getProductid().equals(productid))
                    {
                        if(!c.getIspaid())
                        {
                            return c;
                        }
                    }
                }
            }
            
            return null;
        }
        catch(Exception ex)
        {
            System.out.println("get cart failed by user product ID : " + ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean CartExist(String cartid) {
        Cart cart = em.find(Cart.class, cartid);
        return cart != null;
    }

    @Override
    public boolean AddCartList(List<Cart> carts) {
        try
        {
            utx.begin();
            for(Cart c: carts)
            {
                em.persist(c);
            }
            utx.commit();
        }
        catch(Exception ex)
        {
            System.out.println("add cart list fail : " + ex.getMessage());
        }
        return false;
    }
}
