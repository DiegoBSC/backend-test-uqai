package com.uqai.demo.app.uqaidemo.model.car;

import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name = "catalogs")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumStatus status = EnumStatus.ACT;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "catalogs_cars", joinColumns= @JoinColumn(name = "catalog_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id"))
    private Set<Car> cars;
}
