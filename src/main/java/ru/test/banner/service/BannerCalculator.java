package ru.test.banner.service;

import ru.test.banner.model.Banner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by ALinkov<br/>
 * Date: 09.05.2018<br/>
 *
 * Class for banner calculation with priorities
 */
class BannerCalculator {
    private final List<Banner> banners;

    BannerCalculator(List<Banner> banners) {
        this.banners = new ArrayList<>(banners);
    }

    /**
     * Get banners with priorities
     * @param count     count of banners (must more than 0 and less or equals than all count of banners)
     * @return  banners
     */
    List<Banner> getBanners(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be more than 0");
        }
        if (count > banners.size()) {
            throw new IllegalArgumentException("Count must be less than all banners count");
        }
        if (count == banners.size()) {
            final List<Banner> result = new ArrayList<>(banners);
            //if number of banners equals count numbers then return all banners in random order
            Collections.shuffle(result);//May be not need random order
            return result;
        }
        final List<Banner> result = new ArrayList<>();
        final TreeSet<Banner> unusedBanners = new TreeSet<>(banners);//all unused banners
        for (int i = 0; i < count; i++) {
            final Banner randomBanner = getRandomBanner(unusedBanners);
            unusedBanners.remove(randomBanner);
            result.add(randomBanner);
        }
        return result;
    }

    /**
     * Get single random banner
     * @param banners collection of banners
     * @return  random banner
     */
    private Banner getRandomBanner(TreeSet<Banner> banners) {
        final IntSummaryStatistics stat = banners.stream().mapToInt(Banner::getWeight).summaryStatistics();
        final long val = (long) (Math.random() * stat.getSum());
        long weight = 0;
        for (Banner unusedBanner : banners) {
            weight += unusedBanner.getWeight();
            if (weight > val) {
                return unusedBanner;
            }
        }
        throw new IllegalArgumentException("Incorrect values in banners");
    }
}
