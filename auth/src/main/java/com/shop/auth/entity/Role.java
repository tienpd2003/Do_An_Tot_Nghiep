package com.shop.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.vuongdev.common.model.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_roles")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false, unique = true)
    private Integer roleId;
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;
    @Column(name = "description")
    private String description;
}
