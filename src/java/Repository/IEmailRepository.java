/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import model.*;

/**
 *
 * @author Lenovo
 */
public interface IEmailRepository 
{
    public boolean SendVerifyEmail(Email email, String htmlTemplate);
    public boolean SendAccountSetupEmail(Email email, String htmlTemplate, String tempass);
    public boolean SendResetPasswordEmail(Email email, String htmlTemplate);
    public boolean AddSubscriber(String email);
    public boolean CheckSubscriberExist(String email);
    public boolean SendSubscribeEmail(Email email);
    public boolean SendFeedbackResponseEmail(Email email);
    public boolean SendPurchaseEmail(Email email, String name, String htmlTemplate);
}
