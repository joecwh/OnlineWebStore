/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import java.util.List;
import model.PaymentMethod;

/**
 *
 * @author Lenovo
 */
public interface IPaymentMethodRepository 
{
    public List<PaymentMethod> GetAllPaymentMethod();
    public List<PaymentMethod> GetPaymentMethodsByMethod(String method);
    public List<PaymentMethod> GetAllOnlineBanking();
    public PaymentMethod GetPaymentMethodById(String paymentMethodId);
    public PaymentMethod GetCashPaymentMethod();
    public PaymentMethod GetCardPaymentMethod();

    public boolean AddPayment(PaymentMethod payment);
    public boolean AddAllPayment();
    public boolean InitialPayment();
    public boolean CheckCashandCardExists();
    public boolean DeletePayment(String bankid);
}
