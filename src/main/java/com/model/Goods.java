package com.model;

import com.enump.FeeEnum;
import com.enump.UnitEnum;
import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "Goods")
public class Goods extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @JMap
    @Id
    @GenericGenerator(name = "Goods_sequence", strategy = "sequence", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequenceName", value = "Goods_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1"),
    })
    @GeneratedValue(generator = "Goods_sequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", length = 25, nullable = false)
    private Long id;

    @JMap
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    Group group;

    @JMap
    @Column(name = "date_list", nullable = false)
    private Date dateList;

    @JMap
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @JMap
    @Column(name = "counts", nullable = false)
    private float counts;

    @JMap
    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private UnitEnum unit;

    @JMap
    @Column(name = "min_fee")
    private Integer minFee;

    @JMap
    @Column(name = "max_fee")
    private Integer maxFee;

    @JMap
    @Column(name = "unit_fee")
    @Enumerated(EnumType.STRING)
    private FeeEnum unitFee;

    @JMap
    @Column(name = "buyer_id")
    private Long buyerId;

    @ManyToOne
    @JoinColumn(name = "buyer_id", insertable = false, updatable = false)
    Users buyer;

    @JMap
    @Column(name = "buy")
    private boolean buy;

    @JMap
    @Column(name = "comment")
    private String comment;
}
