/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import java.util.List;
import model.*;

/**
 *
 * @author Lenovo
 */
public interface ICategoryRepository 
{
    public Result<Categories> GetCategories(String id);
    public Result<Category> GetCategory(String id);
    public Result<Category> GetCategoryByProduct(String productid);
    public Result<Category> GetCategoryByCategories(String categoriesid);
    
    public Result<Categories> GetCategories();
    public Result<Category> GetCategory();
    
    public Result<Categories> AddCategories(Categories categories);
    public Result<Category> AddCategory(Category category);
    
    public Result<Categories> UpdateCategories(Categories categories);
    public Result<Category> UpdateCategory(Category category);
    
    public Result<Categories> DeleteCategories(String id);
    public Result<Category> DeleteCategory(String id);
    
    public boolean CategoryExistsByCategories(String id);
    public boolean CategoryExistsByProduct(String id);
}
