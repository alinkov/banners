package ru.test.banner.service;

import org.junit.Test;
import ru.test.banner.model.Banner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ALinkov<br/>
 * Date: 09.05.2018<br/>
 *
 * Test for {@link BannerCalculator}
 */
public class BannerCalculatorTest {
    private final Banner banner1 = new Banner(100, 100);
    private final Banner banner2 = new Banner(200, 200);

    @Test(expected = IllegalArgumentException.class)
    public void getIncorrectCountBanners() {
        final BannerCalculator bannerCalculator = new BannerCalculator(Arrays.asList(banner1, banner2));
        bannerCalculator.getBanners(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMoreBannersWhenSaved() {
        final BannerCalculator bannerCalculator = new BannerCalculator(Arrays.asList(banner1, banner2));
        bannerCalculator.getBanners(3);
    }

    @Test
    public void getAllBanners() {
        final List<Banner> inputBanners = Arrays.asList(banner1, banner2);
        final BannerCalculator bannerCalculator = new BannerCalculator(inputBanners);
        final List<Banner> result = bannerCalculator.getBanners(2);
        assertEquals(new HashSet<>(inputBanners), new HashSet<>(result));
    }

    @Test
    public void getBanner() {
        final List<Banner> inputBanners = Arrays.asList(banner1, banner2);
        final BannerCalculator bannerCalculator = new BannerCalculator(inputBanners);
        int countBanner1 = 0;
        int countBanner2 = 0;
        for (int i = 0; i < 1000; i++) {
            List<Banner> banners = bannerCalculator.getBanners(1);
            assertEquals(1, banners.size());
            if(banners.get(0).equals(banner1)) {
                countBanner1++;
            } else {
                countBanner2++;
            }
        }
        //weight for banner2 more than weight for banner1
        assertTrue(countBanner1 < countBanner2);
    }

}