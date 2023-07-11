/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Mapper.Mapper;
import Repository.IFeedbackRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Feedback;

/**
 *
 * @author Lenovo
 */
public class FeedbackService implements IFeedbackRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    public FeedbackService(
        UserTransaction utx, 
        EntityManager em)
    {
        this.utx = utx;
        this.em = em;
    }
    
    @Override
    public boolean AddFeedback(Feedback feedback) {
        try
        {
            utx.begin();
            em.persist(feedback);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("add feedback  fail : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public List<Feedback> GetFeedbacks() {
        List<Feedback> feedbacks = em.createNamedQuery("Feedback.findAll").getResultList();
        return feedbacks.isEmpty() ? new ArrayList() : feedbacks;
    }

    @Override
    public Feedback GetFeedback(String feedbackid) {
        Feedback feedback = em.find(Feedback.class, feedbackid);
        return feedback == null ? null : feedback;
    }

    @Override
    public List<Feedback> GetFeedbackByEmail(String email) {
        List<Feedback> feedbacks = em.createNamedQuery("Feedback.findByEmail")
                .setParameter("email", email)
                .getResultList();
        
        return feedbacks.isEmpty() ? new ArrayList() : feedbacks;
    }

    @Override
    public boolean UpdateFeedbackStatus(Feedback feedback) {
        try
        {
            utx.begin();
            em.merge(feedback);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("update feedback status failed : " + ex.getMessage());
        }
        return false;
    }
}
