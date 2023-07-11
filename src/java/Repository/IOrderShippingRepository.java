/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import java.util.List;
import model.Orders;
import model.Shipping;

/**
 *
 * @author Lenovo
 */
public interface IOrderShippingRepository 
{
    public List<Orders> GetAllOrder();
    public List<Orders> GetOrderByUserId(String userid);
    public List<Shipping> GetAllShipping();
    public Orders GetOrder(String orderid);
    public Shipping GetShipping(String shippingid);
    public boolean AddOrder(Orders order);
    public boolean AddShipping(Shipping shipping);
    public boolean UpdateOrder(Orders order);
    public boolean UpdateShipping(Shipping shipping);
    public boolean DeleteOrder(String orderid);
    public boolean DeleteShipping(String shippingid);

}
