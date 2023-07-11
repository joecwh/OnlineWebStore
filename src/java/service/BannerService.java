/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Repository.IBannerRepository;
import static enumeration.BannerCode.*;
import static enumeration.UserCode.*;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Banner;
import model.Result;

/**
 *
 * @author Lenovo
 */
public class BannerService implements IBannerRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    public BannerService(
        UserTransaction utx,
        EntityManager em)
    {
        this.utx = utx;
        this.em = em;
    }

    @Override
    public Result<Banner> GetBanners() {
        List<Banner> banners = (List<Banner>) em.createNamedQuery("Banner.findAll").getResultList();
        if(banners.isEmpty())
            return new Result<>(null, ADMIN.name(), false, BANNER_NOT_FOUND.getMessage());
        
        return new Result<>(banners, ADMIN.name(), true, BANNER_FOUND.getMessage());
    }

    @Override
    public Result<Banner> GetBanner(String id) {
        Banner banner = (Banner) em.createNamedQuery("Banner.findByBannerid").setParameter("bannerid", id).getResultList().get(0);
        if(banner == null)
            return new Result<>(null, ADMIN.name(), false, BANNER_ID_INVALID.getMessage());
        
        return new Result<>(banner, ADMIN.name(), true, BANNER_FOUND.getMessage());
    }


    @Override
    public Result<Banner> AddBanner(Banner banner) {
        try
        {
            utx.begin();
            em.persist(banner);
            utx.commit();
            
            return new Result<>(banner, ADMIN.name(), true, BANNER_ADD_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(banner, ADMIN.name(), false, BANNER_ADD_FAILED.getMessage() + "\nDetails message: " + ex.getMessage());
        }
    }

    @Override
    public Result<Banner> UpdateBanner(Banner banner) {
        try
        {
            utx.begin();
            em.merge(banner);
            utx.commit();
            
            return new Result<>(banner, ADMIN.name(), true, BANNER_UPDATE_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(banner, ADMIN.name(), false, BANNER_UPDATE_FAILED.getMessage() + "\nDetails message: " + ex.getMessage());
        }
    }

    @Override
    public Result<Banner> DeleteBanner(String id) {
        Banner banner = em.find(Banner.class, id);
        if(banner == null)
            return new Result<>(null, ADMIN.name(), false, BANNER_ID_INVALID.getMessage());

        try
        {
            utx.begin();
            Banner mergeBanner = em.merge(banner);
            em.remove(mergeBanner);
            utx.commit();
            
            return new Result<>(banner, ADMIN.name(), true, BANNER_DELETE_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(null, ADMIN.name(), false, BANNER_DELETE_FAILED.getMessage() + "\nDetails message: " + ex.getMessage());
        }
    }
}
