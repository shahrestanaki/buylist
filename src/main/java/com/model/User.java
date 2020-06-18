package com.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
public class User extends BaseEntity{
    @Id
    @GenericGenerator(name = "user_sequence", strategy = "sequence", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequenceName", value = "user_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "20"),
            @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1"),
    })
    @GeneratedValue(generator = "user_sequence", strategy= GenerationType.SEQUENCE)
    @Column(name = "id", length = 25, nullable = false)
    private Long id;

    @Column(name = "username", length = 50, nullable = false)
    private String userName;

    @Column(name = "enabled", nullable = false)
    private Boolean active;

}
