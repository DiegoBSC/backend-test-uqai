package com.uqai.demo.app.uqaidemo.model.car;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name = "brands")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumStatus status = EnumStatus.ACT;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy="brand")
    private Set<Car> cars;
}
