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
public interface IBannerRepository 
{
    public Result<Banner> GetBanners();
    public Result<Banner> GetBanner(String id);
    public Result<Banner> AddBanner(Banner banner);
    public Result<Banner> UpdateBanner(Banner banner);
    public Result<Banner> DeleteBanner(String id);
}
