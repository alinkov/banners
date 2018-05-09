package ru.test.banner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.banner.model.Banner;
import ru.test.banner.repository.BannerRepository;

import java.util.List;

/**
 * Created by ALinkov<br/>
 * Date: 09.05.2018<br/>
 *
 * Implementation of BannerService
 */
@Service("bannerService")
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;

    @Autowired
    public BannerServiceImpl(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    /**{@inheritDoc}*/
    @Override
    public Banner save(Banner banner) {
        return bannerRepository.save(banner);
    }

    /**{@inheritDoc}*/
    @Override
    public void delete(Integer id) {
        bannerRepository.deleteById(id);
    }

    /**{@inheritDoc}*/
    @Override
    public List<Banner> getBanners(int count) {
        final List<Banner> banners = bannerRepository.findAll();
        return new BannerCalculator(banners).getBanners(count);
    }

}
