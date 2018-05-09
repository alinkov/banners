package ru.test.banner.controller;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import ru.test.banner.model.Banner;
import ru.test.banner.repository.BannerRepository;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ALinkov<br/>
 * Date: 09.05.2018<br/>
 *
 * Test for {@link BannerController}
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BannerController.class)
public class BannerControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BannerRepository bannerRepository;

    @Before
    public void setUp() {
        final Banner defaultBanner = new Banner(100);
        defaultBanner.setId(100);
        when(bannerRepository.findAll()).thenReturn(Collections.singletonList(defaultBanner));
    }

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Test
    public void saveBanner() throws Exception {
        final Banner banner = new Banner();
        banner.setWeight(100);
        String bannerJson = json(new BannerPojo(banner));

        this.mockMvc.perform(post("/banners")
                .contentType(contentType)
                .content(bannerJson))
                .andExpect(status().isCreated());
        verify(bannerRepository).save(argThat(bannerParam -> banner.getWeight() == bannerParam.getWeight()));
    }

    @Test
    public void getBanners() throws Exception {
        this.mockMvc.perform(get("/banners?k=1")
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void getBannersError() throws Exception {
        this.mockMvc.perform(get("/banners?k=2")
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBanner() throws Exception {
        final int bannerId = 100;
        this.mockMvc.perform(delete("/banners/" + bannerId)
                .contentType(contentType))
                .andExpect(status().isOk());
        verify(bannerRepository).deleteById(bannerId);
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    @Data
    @NoArgsConstructor
    private static class BannerPojo implements Serializable {
        private Integer id;
        private int weight;

        BannerPojo(Banner banner) {
            this.id = banner.getId();
            this.weight = banner.getWeight();
        }
    }
}