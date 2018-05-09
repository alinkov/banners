package ru.test.banner.service;

import ru.test.banner.model.Banner;

import java.util.List;

/**
 * Created by ALinkov<br/>
 * Date: 09.05.2018<br/>
 *
 * Service for work with {@link Banner}
 */
public interface BannerService {

    /**
     * Save banner
     * @param banner    banner for save
     * @return          saved banner
     */
    Banner save(Banner banner);

    /**
     * Delete banner by id
     * @param id    id of the banner
     */
    void delete(Integer id);

    /**
     * Get banners with priorities
     * @param count     count of banners (must more than 0 and less or equals than all count of banners)
     * @return  banners
     */
    List<Banner> getBanners(int count);
}
