/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import java.util.List;
import model.Cart;

/**
 *
 * @author Lenovo
 */
public interface ICartRepository 
{   
    public boolean AddCart(Cart cart);
    public List<Cart> GetCarts();
    public Cart GetCartByCartId(String cartid);
    public List<Cart> GetCartByUserId(String userid);
    public List<Cart> GetCartByProductId(String productid);
    public Cart GetCartByUserProductId(String userid, String productid);
    public boolean AddCartList(List<Cart> carts);
    public boolean UpdateCart(Cart cart);
    public boolean DeleteCart(String cartid);
    public boolean CartExist(String cartid);
}
