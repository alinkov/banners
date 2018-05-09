package ru.test.banner.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.test.banner.model.Banner;
import ru.test.banner.service.BannerService;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ALinkov<br/>
 * Date: 08.05.2018<br/>
 *
 * Controller for banner
 *
 * Include 3 methods
 * GET /banners?k=number {@link #getBanners(int)}
 * POST /banners {@link #saveBanner(Banner)} input parameter weight
 * DELETE /banners/{id} {@link #deleteBanner(Integer)}
 */
@RestController
@RequestMapping("/banners")
public class BannerController {
    private final BannerService bannerService;

    @Autowired
    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    /**
     * Save banner
     * POST /banners input parameter weight
     */
    @PostMapping
    public ResponseEntity<SaveResult> saveBanner(@RequestBody Banner banner) {
        final Banner savedBanner = bannerService.save(banner);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SaveResult(savedBanner));
    }

    /**
     * GET /banners?k=number
     * @param count number of banners
     */
    @GetMapping
    public GetResult getBanners(@RequestParam(value = "k") int count) {
        return new GetResult(bannerService.getBanners(count));
    }

    /**
     * Delete banner with id
     */
    @DeleteMapping(value = "/{id}")
    public void deleteBanner(@PathVariable("id") Integer id) {
        bannerService.delete(id);
    }

    /**Model to return save result in format {"banner": {"id": 172121212}}*/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SaveResult implements Serializable {
        private Banner banner;
    }

    /**Model to return get result in format {"banners": [{"id": 172121212}]}*/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class GetResult implements Serializable {
        private List<Banner> banners;
    }

}
