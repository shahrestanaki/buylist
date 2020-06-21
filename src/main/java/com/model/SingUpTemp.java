package com.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SingUpTemp")
public class SingUpTemp extends BaseEntity {

    @Id
    @GenericGenerator(name = "SingUpTemp_sequence", strategy = "sequence", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequenceName", value = "SingUpTemp_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1"),
    })
    @GeneratedValue(generator = "SingUpTemp_sequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", length = 25, nullable = false)
    private Long id;

    @Column(name = "mobile", length = 11, nullable = false)
    private String mobile;

    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Column(name = "counts", nullable = false)
    private Integer counts;

    @Column(name = "singdateStr", nullable = false)
    private String singDateStr;

    @Column(name = "hashPassword", nullable = false)
    private String hashPassword;
}
