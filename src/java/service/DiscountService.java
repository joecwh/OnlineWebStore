/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Repository.IDiscountRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Discount;

/**
 *
 * @author Lenovo
 */
public class DiscountService implements IDiscountRepository
{
    @Resource UserTransaction utx;
    @PersistenceContext EntityManager em;
    
    public DiscountService(
            UserTransaction utx, 
            EntityManager em)
    {
        this.utx = utx;
        this.em = em;
    }
    
    @Override
    public Discount GetDiscount(String discountid) {
        Discount discount = em.find(Discount.class, discountid);
        return discount;
    }

    @Override
    public List<Discount> GetAllDiscounts() {
        List<Discount> discounts = em.createNamedQuery("Discount.findAll").getResultList();
        return discounts;
    }

    @Override
    public boolean UpSertDiscount(Discount discount) {
        try
        {
            boolean discountExistByCode = DiscountExistByCode(discount.getCode());
            boolean discountExistById = DiscountExistById(discount.getDiscountid());
            if(discountExistById)
            {
                //insert discount
                utx.begin();
                em.merge(discount);
                utx.commit();

                return true;
            }
            else
            {
                if(!discountExistByCode)
                {
                    //update discount
                    utx.begin();
                    em.persist(discount);
                    utx.commit();

                    return true;
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println("Upsert discount fail : " + ex.getMessage());
        }
        return false;
    }


    @Override
    public boolean DeleteDiscount(String discountid) {
        try
        {
            Discount discount = GetDiscount(discountid);
            if(discount != null)
            {
                utx.begin();
                Discount deleteDiscount = em.merge(discount);
                em.remove(deleteDiscount);
                utx.commit();

                return true;
            }
        }
        catch(Exception ex)
        {
            System.out.println("delete discount fail : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean DiscountExistByCode(String code) {
        List<Discount> discounts = em.createNamedQuery("Discount.findByCode").setParameter("code", code).getResultList();
        if(discounts.isEmpty())
            return false;
        
        return true;
    }

    @Override
    public boolean DiscountExistById(String discountid) {
        Discount discount = em.find(Discount.class, discountid);
        if(discount == null)
            return false;
        
        return true;
    }

    @Override
    public Discount GetDiscountByCode(String code) {
        List<Discount> discounts = new ArrayList();
        if(DiscountExistByCode(code))
            discounts = em.createNamedQuery("Discount.findByCode")
                    .setParameter("code", code)
                    .getResultList();
        
        return discounts.isEmpty() ? null : discounts.get(0);
    }
    
}
