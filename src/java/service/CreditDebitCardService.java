/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Repository.ICreditDebitCardRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Card;

/**
 *
 * @author Lenovo
 */
public class CreditDebitCardService implements ICreditDebitCardRepository
{
    @Resource UserTransaction utx;
    @PersistenceContext EntityManager em;
    
    public CreditDebitCardService(
            UserTransaction utx, 
            EntityManager em)
    {
        this.utx = utx;
        this.em = em;
    }
    
    @Override
    public List<Card> GetAllCard() {
        List<Card> cards = em.createNamedQuery("Card.findAll")
                .getResultList();
        
        return cards.isEmpty() ? new ArrayList() : cards;
    }

    @Override
    public Card GetCard(String cardid) {
        Card card = em.find(Card.class, cardid);
        return card == null ? null : card;
    }

    @Override
    public boolean AddCard(Card card) {
        try
        {
            Card cardExists = GetCard(card.getCardid());
            if(cardExists == null && !CardExistByCardNumber(card.getCardnumber()))
            {
                utx.begin();
                em.persist(card);
                utx.commit();
                
                return true;
            }
        }
        catch(Exception ex)
        {
            System.out.println("add card failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean UpdateCard(Card card) {
        try
        {
            Card cardExists = GetCard(card.getCardid());
            if(cardExists != null && !CardExistByCardNumber(card.getCardnumber()))
            {
                utx.begin();
                em.merge(card);
                utx.commit();
                
                return true;
            }
        }
        catch(Exception ex)
        {
            System.out.println("update card failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean DeleteCard(String cardid) {
        try
        {
            Card cardExists = GetCard(cardid);
            if(cardExists != null)
            {
                utx.begin();
                Card removeCard = em.merge(cardExists);
                em.remove(removeCard);
                utx.commit();
                
                return true;
            }
        }
        catch(Exception ex)
        {
            System.out.println("delete card failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean CardExistByCardNumber(String cardNumber) {
        List<Card> cards = em.createNamedQuery("Card.findByCardnumber")
                .setParameter("cardnumber", cardNumber)
                .getResultList();
        
        return !cards.isEmpty();
    }
    
}
