/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import java.util.List;
import model.Feedback;

/**
 *
 * @author Lenovo
 */
public interface IFeedbackRepository 
{
    public boolean AddFeedback(Feedback feedback);
    public boolean UpdateFeedbackStatus(Feedback feedback);
    public List<Feedback> GetFeedbacks();
    public List<Feedback> GetFeedbackByEmail(String email);
    public Feedback GetFeedback(String feedbackid);
}
