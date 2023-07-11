/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Repository.ICategoryRepository;
import static enumeration.CategoryCode.*;
import static enumeration.ProductCode.PRODUCT_FOUND;
import static enumeration.ProductCode.PRODUCT_ID_INVALID;
import static enumeration.ProductCode.PRODUCT_NOT_FOUND;
import static enumeration.UserCode.ADMIN;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Categories;
import model.Category;
import model.Product;
import model.Result;

/**
 *
 * @author Lenovo
 */
public class CategoryService implements ICategoryRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    public CategoryService(
        UserTransaction utx,
        EntityManager em)
    {
        this.utx = utx;
        this.em = em;
    }

    @Override
    public Result<Categories> GetCategories(String id) {
        Categories categories = em.find(Categories.class, id);
        if(categories == null)
            return new Result<>(null, null, false, CATEGORIES_ID_INVALID.getMessage());
        
        return new Result<>(categories, null, true, CATEGORIES_FOUND.getMessage());
    }

    @Override
    public Result<Category> GetCategory(String id) {
        Category category = em.find(Category.class, id);
        if(category == null)
            return new Result<>(null, null, false, CATEGORY_ID_INVALID.getMessage());
        
        return new Result<>(category, null, true, CATEGORY_FOUND.getMessage());
    }

    @Override
    public Result<Categories> GetCategories() {
        List<Categories> categories = (List<Categories>) em.createNamedQuery("Categories.findAll").getResultList();
        if(categories.isEmpty())
            return new Result<>(null, null, false, CATEGORIES_NOT_FOUND.getMessage());

        return new Result<>(categories, null, true, CATEGORIES_FOUND.getMessage());
    }

    @Override
    public Result<Category> GetCategory() {
        List<Category> category = (List<Category>) em.createNamedQuery("Category.findAll").getResultList();
        if(category.isEmpty())
            return new Result<>(null, null, false, CATEGORY_NOT_FOUND.getMessage());

        return new Result<>(category, null, true, CATEGORY_FOUND.getMessage());
    }

    @Override
    public Result<Categories> AddCategories(Categories categories) {
        try
        {
            utx.begin();
            em.persist(categories);
            utx.commit();
            
            return new Result<>(categories, null, true, CATEGORIES_ADD_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(categories, null, false, CATEGORIES_ADD_FAILED.getMessage());
        }
    }

    @Override
    public Result<Category> AddCategory(Category category) {
        try
        {
            utx.begin();
            em.persist(category);
            utx.commit();
            
            return new Result<>(category, null, true, CATEGORY_ADD_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(category, null, false, CATEGORY_ADD_FAILED.getMessage());
        }
    }

    @Override
    public Result<Categories> UpdateCategories(Categories categories) {
        try
        {
            utx.begin();
            em.merge(categories);
            utx.commit();
            
            return new Result<>(categories, null, true, CATEGORIES_UPDATE_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(categories, null, false, CATEGORIES_UPDATE_FAILED.getMessage());
        }
    }

    @Override
    public Result<Category> UpdateCategory(Category category) {
        try
        {
            utx.begin();
            em.merge(category);
            utx.commit();
            
            return new Result<>(category, null, true, CATEGORY_UPDATE_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(category, null, false, CATEGORY_UPDATE_FAILED.getMessage());
        }
    }

    @Override
    public Result<Categories> DeleteCategories(String id) {
        try
        {
            Categories categories = em.find(Categories.class, id);
            if(categories == null)
                return new Result<>(null, null, false, CATEGORIES_ID_INVALID.getMessage());
        
            utx.begin();
            Categories removeCategories = em.merge(categories);
            em.remove(removeCategories);
            utx.commit();
            
            return new Result<>(null, null, true, CATEGORIES_DELETE_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(null, null, false, CATEGORIES_DELETE_FAILED.getMessage());
        }
    }

    @Override
    public Result<Category> DeleteCategory(String id) {
        Category category = GetCategory(id).getResult();
        if(category == null)
            return new Result<>(null, null, false, CATEGORY_ID_INVALID.getMessage());

        try
        {
            utx.begin();
            Category removeCategory = em.merge(category);
            em.remove(removeCategory);
            utx.commit();
            
            return new Result<>(category, null, true, CATEGORY_DELETE_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(null, null, false, CATEGORY_DELETE_FAILED.getMessage());
        }
    }

    @Override
    public Result<Category> GetCategoryByProduct(String productid) {
        try
        {
            Product product = em.find(Product.class, productid);
            if(product == null)
                return new Result<>(null, null, false, PRODUCT_ID_INVALID.getMessage());

            List<Category> category = (List<Category>) em.createNamedQuery("Category.findByProductid").setParameter("productid", productid).getResultList();
            if(category.isEmpty())
                return new Result<>(null, null, false, PRODUCT_NOT_FOUND.getMessage());

            return new Result<>(category.get(0), ADMIN.name(), true, PRODUCT_FOUND.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(null, null, false, PRODUCT_NOT_FOUND.getMessage() + ex.getMessage());
        }
    }

    @Override
    public Result<Category> GetCategoryByCategories(String categoriesid) {
        try
        {
            List<Category> category = em.createNamedQuery("Category.findByCategoriesid").setParameter("categoriesid", categoriesid).getResultList();
            if(category.isEmpty())
                return new Result<>(null, null, false, CATEGORY_NOT_FOUND.getMessage());

            return new Result<>(category, null, true, CATEGORY_FOUND.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(null, null, false, PRODUCT_NOT_FOUND.getMessage() + ex.getMessage());
        }
    }

    @Override
    public boolean CategoryExistsByCategories(String id) {
        List<Category> category = em.createNamedQuery("Category.findByCategoriesid").setParameter("categoriesid", id).getResultList();
        if(category.isEmpty())
            return false;

        return true;
    }

    @Override
    public boolean CategoryExistsByProduct(String id) {
        List<Category> category = em.createNamedQuery("Category.findByProductid").setParameter("productid", id).getResultList();
        if(category.isEmpty())
            return false;

        return true;
    }
    
}
