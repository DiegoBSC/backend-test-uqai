package com.uqai.demo.app.uqaidemo.model.user;

import com.uqai.demo.app.uqaidemo.enums.EnumRol;
import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Table(name = "sec_roles")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumRol name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumStatus status = EnumStatus.ACT;
}
