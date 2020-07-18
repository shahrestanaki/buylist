package com.model;

import com.enump.FeeEnum;
import com.enump.UnitEnum;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Goods")
public class Goods extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "Goods_sequence", strategy = "sequence", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequenceName", value = "Goods_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1"),
    })
    @GeneratedValue(generator = "Goods_sequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", length = 25, nullable = false)
    private Long id;


    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    Group group;


    @Column(name = "date_list", nullable = false)
    private String dateList;


    @Column(name = "name", length = 50, nullable = false)
    private String name;


    @Column(name = "counts", nullable = false)
    private Float counts;


    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private UnitEnum unit;


    @Column(name = "min_fee")
    private Integer minFee;


    @Column(name = "max_fee")
    private Integer maxFee;


    @Column(name = "unit_fee")
    @Enumerated(EnumType.STRING)
    private FeeEnum unitFee;


    @Column(name = "buyer_id")
    private Long buyerId;

    @ManyToOne
    @JoinColumn(name = "buyer_id", insertable = false, updatable = false)
    UserGroup buyer;


    @Column(name = "buy")
    private Boolean buy;


    @Column(name = "comment")
    private String comment;
}
