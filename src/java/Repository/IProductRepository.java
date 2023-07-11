/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import java.util.List;
import model.Product;
import model.Result;

/**
 *
 * @author Lenovo
 */
public interface IProductRepository 
{
    public Result<Product> GetProduct(String id);
    public Result<Product> GetProducts();
    public Result<Product> AddProduct(Product product);
    public Result<Product> UpdateProduct(Product product);
    public Result<Product> DeleteProduct(String id);
    public boolean UpdateProductList(List<Product> products);
}
