/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import java.util.List;
import model.Discount;

/**
 *
 * @author Lenovo
 */
public interface IDiscountRepository 
{
    public Discount GetDiscount(String discountid);
    public Discount GetDiscountByCode(String code);
    public List<Discount> GetAllDiscounts();
    public boolean UpSertDiscount(Discount discount);
    public boolean DeleteDiscount(String discountid);
    public boolean DiscountExistById(String discountid);
    public boolean DiscountExistByCode(String code);
}
