package ru.test.banner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by ALinkov<br/>
 * Date: 08.05.2018<br/>
 *
 * Model for banner
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BANNER")
public class Banner implements Serializable, Comparable<Banner> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Min(value = 1)
    @Column(name = "weight")
    private int weight;

    public Banner(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Banner o) {
        if (this.getWeight() != o.getWeight()) {
            return Integer.compare(o.getWeight(), this.getWeight());
        } else {
            return Integer.compare(o.getId(), this.getId());
        }
    }
}
