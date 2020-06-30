package com.model;

import com.enump.UserGroupRoleEnum;
import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table( name = "UserGroup",
        uniqueConstraints = { @UniqueConstraint( columnNames = { "NICKNAME", "GROUP_ID" } ),
                @UniqueConstraint( columnNames = { "GROUP_ID", "USER_ID" } )} )
public class UserGroup extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @JMap
    @Id
    @GenericGenerator(name = "UserGroup_sequence", strategy = "sequence", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequenceName", value = "UserGroup_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1"),
    })
    @GeneratedValue(generator = "UserGroup_sequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", length = 25, nullable = false)
    private Long id;

    @JMap
    @Column(name = "GROUP_ID", nullable = false)
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", insertable = false, updatable = false)
    Group group;

    @JMap
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    Users users;

    @JMap
    @Column(name = "NICKNAME", length = 20 )
    private String nickName;

    @JMap
    @Column(name = "AVATAR", length = 20)
    private String avatar;

    @JMap
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserGroupRoleEnum role;

    public UserGroup(){}

    public UserGroup(Long groupId, Long userId, UserGroupRoleEnum role) {
        this.groupId = groupId;
        this.userId = userId;
        this.role = role;
        this.createDate = new Date();
    }
}
