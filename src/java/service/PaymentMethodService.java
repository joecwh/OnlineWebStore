/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Repository.IPaymentMethodRepository;
import enumeration.BankCode;
import static enumeration.PaymentCode.*;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.PaymentMethod;

/**
 *
 * @author Lenovo
 */
public class PaymentMethodService implements IPaymentMethodRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    public PaymentMethodService(
            UserTransaction utx, 
            EntityManager em)
    {
        this.utx = utx;
        this.em = em;
    }
    
    @Override
    public List<PaymentMethod> GetAllPaymentMethod() {
        List<PaymentMethod> paymentMethod = em.createNamedQuery("PaymentMethod.findAll")
                .getResultList();
        
        if(!paymentMethod.isEmpty())
            return paymentMethod;
        
        return new ArrayList();
    }

    @Override
    public List<PaymentMethod> GetPaymentMethodsByMethod(String method) {
        List<PaymentMethod> cash = em.createNamedQuery("PaymentMethod.findByMethod")
                .setParameter("method", method)
                .getResultList();
        
        if(!cash.isEmpty())
            return cash;
        
        return new ArrayList();
    }

    @Override
    public List<PaymentMethod> GetAllOnlineBanking() {
        List<PaymentMethod> onlinebanking = em.createNamedQuery("PaymentMethod.findByMethod")
                .setParameter("method", ONLINE_BANKING.name())
                .getResultList();
        if(!onlinebanking.isEmpty())
            return onlinebanking;
        
        return new ArrayList();
    }

    @Override
    public PaymentMethod GetCashPaymentMethod() {
        List<PaymentMethod> cash = (List<PaymentMethod>) em.createNamedQuery("PaymentMethod.findByMethod")
                .setParameter("method", CASH_ON_DELIVERY.name())
                .getResultList();
        
        if(!cash.isEmpty())
            return cash.get(0);
        
        return null;
    }

    @Override
    public PaymentMethod GetCardPaymentMethod() {
        List<PaymentMethod> card = (List<PaymentMethod>) em.createNamedQuery("PaymentMethod.findByMethod")
                .setParameter("method", CREDIT_OR_DEBIT_CARD.name())
                .getResultList();
        
        if(!card.isEmpty())
            return card.get(0);
        
        return null;
    }

    @Override
    public PaymentMethod GetPaymentMethodById(String paymentMethodId) 
    {
        PaymentMethod paymentMethod = em.find(PaymentMethod.class, paymentMethodId);
        if(paymentMethod != null)
            return paymentMethod;
        
        return null;
    }

    @Override
    public boolean AddPayment(PaymentMethod payment) {
        try
        {
            utx.begin();
            em.persist(payment);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("Add payment failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean DeletePayment(String bankid) {
        PaymentMethod paymentExists = GetPaymentMethodById(bankid);
        if(paymentExists != null)
        {
            try
            {
                utx.begin();
                PaymentMethod deletePayment = em.merge(paymentExists);
                em.remove(deletePayment);
                utx.commit();

                return true;
            }
            catch(Exception ex)
            {
                System.out.println("delete payment failed : " + ex.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean InitialPayment() {
        try
        {
            List<PaymentMethod> allPayment = GetAllPaymentMethod();
            if(!allPayment.isEmpty())
            {
                utx.begin();
                for(PaymentMethod p : allPayment)
                {
                    PaymentMethod deleteBuffer = em.merge(p);
                    em.remove(deleteBuffer);
                }
                utx.commit();
            }
            if(AddAllPayment())
                return true;
        }
        catch(Exception ex)
        {
            System.out.println("delete payment failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean AddAllPayment() {
        try
        {
            PaymentMethod cash = new PaymentMethod(CASH_ON_DELIVERY.name(), BankCode.CASH_ON_DELIVERY.getMessage());
            PaymentMethod card = new PaymentMethod(CREDIT_OR_DEBIT_CARD.name(), BankCode.CREDIT_OR_DEBIT_CARD.getMessage());
            
            utx.begin();
            em.persist(cash);
            em.persist(card);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("add all payment failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean CheckCashandCardExists() {
        List<PaymentMethod> cash = em.createNamedQuery("PaymentMethod.findByMethod")
                .setParameter("method", CASH_ON_DELIVERY.name())
                .getResultList();
        
        List<PaymentMethod> card = em.createNamedQuery("PaymentMethod.findByMethod")
                .setParameter("method", CREDIT_OR_DEBIT_CARD.name())
                .getResultList();
        
        return !cash.isEmpty() && !card.isEmpty();
    }
}
