/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Lenovo
 */
public class ProductDemandDto 
{
    private String productId;
    private int totalQuantity;
    
    public ProductDemandDto(){}
    
    public ProductDemandDto(String productId, int totalQuantity)
    {
        this.productId = productId;
        this.totalQuantity = totalQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
