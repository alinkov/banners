package ru.test.banner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.banner.model.Banner;

/**
 * Created by ALinkov<br/>
 * Date: 09.05.2018<br/>
 *
 * Repository for {@link Banner}
 */
@Repository
public interface BannerRepository  extends JpaRepository<Banner, Integer> {
}
