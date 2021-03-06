package com.app.listaprzebojow.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> users;
}
