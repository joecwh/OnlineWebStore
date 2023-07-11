/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import enumeration.AccountCode;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class Result<T> 
{
    private List<T> resultList;
    private T result;
    private String role;
    private boolean status;
    private String message;

    public Result(){}
    
    public Result(List<T> resultList, String role, boolean status, String message) 
    {
        this.resultList = resultList;
        this.role = role;
        this.status = status;
        this.message = message;
    }
    
    public Result(T result, String role, boolean status, String message) 
    {
        this.result = result;
        this.role = role;
        this.status = status;
        this.message = message;
    }

    public List<T> getResultList() 
    {
        return resultList;
    }
    
    public void setResultList(List<T> resultList)
    {
        this.resultList = resultList;
    }
    
    public T getResult()
    {
        return result;
    }
    
    public void setResult(T result)
    {
        this.result = result;
    }
    
    public String getRole()
    {
        return role;
    }
    
    public void setRole(String rolename)
    {
        this.role = rolename;
    }
    
    public boolean getStatus()
    {
        return status;
    }
    
    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public String getMessage() 
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
}
