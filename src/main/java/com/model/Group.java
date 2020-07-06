package com.model;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Group_list",
        uniqueConstraints = { @UniqueConstraint( columnNames = { "CODE" } ) } )
public class Group extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @JMap
    @Id
    @GenericGenerator(name = "Group_sequence", strategy = "sequence", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequenceName", value = "Group_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1"),
    })
    @GeneratedValue(generator = "Group_sequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", length = 25, nullable = false)
    private Long id;

    @JMap
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @JMap
    @Column(name = "CODE", length = 50, nullable = false)
    private String code;

    @JMap
    @Column(name = "about", length = 255)
    private String about;

    @JMap
    @Column(name = "avatar", length = 20)
    private String avatar;

}
