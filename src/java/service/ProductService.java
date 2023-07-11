/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Repository.IProductRepository;
import static enumeration.ProductCode.*;
import static enumeration.UserCode.*;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Cart;
import model.Product;
import model.Result;

/**
 *
 * @author Lenovo
 */
public class ProductService implements IProductRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    public ProductService(
            UserTransaction utx,
            EntityManager em)
    {
        this.utx = utx;
        this.em = em;
    }

    @Override
    public Result<Product> GetProduct(String id) 
    {
        Product product = em.find(Product.class, id);
        if(product == null)
            return new Result<>(null, ADMIN.name(), false, PRODUCT_NOT_FOUND.getMessage());
        
        return new Result<>(product, ADMIN.name(), true, PRODUCT_FOUND.getMessage());
    }

    @Override
    public Result<Product> GetProducts() 
    {
        List<Product> products = (List<Product>) em.createNamedQuery("Product.findAll").getResultList();
        if(products.isEmpty())
            return new Result<>(null, ADMIN.name(), false, PRODUCT_NOT_FOUND.getMessage());

        return new Result<>(products, ADMIN.name(), true, PRODUCT_FOUND.getMessage());
    }

    @Override
    public Result<Product> UpdateProduct(Product product) 
    {
        try
        {
            utx.begin();
            em.merge(product);
            utx.commit();
            
            return new Result<>(product, ADMIN.name(), true, PRODUCT_UPDATE_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(product, ADMIN.name(), false, PRODUCT_UPDATE_FAILED.getMessage() + "\nDetails message: " + ex.getMessage());
        }
    }

    @Override
    public Result<Product> DeleteProduct(String id) 
    {
        Product product = em.find(Product.class, id);
        if(product == null)
            return new Result<>(null, ADMIN.name(), false, PRODUCT_NOT_FOUND.getMessage());
        
        List<Cart> carts = em.createNamedQuery("Cart.findByProductid").setParameter("productid", product.getProductid()).getResultList();
        if(!carts.isEmpty())
            return new Result<>(null, ADMIN.name(), false, PRODUCT_DELETE_FAILED_CART_EXIST.getMessage());
        
        try
        {
            utx.begin();
            Product removeProduct = em.merge(product);
            em.remove(removeProduct);
            utx.commit();
            
            return new Result<>(product, ADMIN.name(), true, PRODUCT_DELETE_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(product, ADMIN.name(), false, PRODUCT_DELETE_FAILED.getMessage() + "\nDetails message: " + ex.getMessage());
        }
    }

    @Override
    public Result<Product> AddProduct(Product product) {
        try
        {
            utx.begin();
            em.persist(product);
            utx.commit();
            
            return new Result<>(product, ADMIN.name(), true, PRODUCT_ADD_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(product, ADMIN.name(), false, PRODUCT_ADD_FAILED.getMessage() + "\nDetails message: " + ex.getMessage());
        }
    }

    @Override
    public boolean UpdateProductList(List<Product> products) {
        try
        {
            utx.begin();
            for(Product p : products)
            {
                em.merge(p);
            }
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("add product list fail " + ex.getMessage());
        }
        return false;
    }
}
