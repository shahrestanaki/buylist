package com.model;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class Users extends BaseEntity {
    @Id
    @GenericGenerator(name = "user_sequence", strategy = "sequence", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequenceName", value = "user_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1"),
    })
    @GeneratedValue(generator = "user_sequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", length = 25, nullable = false)
    private Long id;

    @Column(name = "username", length = 50, nullable = false , unique = true)
    private String userName;

    @Column(name = "mobile", length = 11, nullable = false , unique = true)
    private String mobile;

    @Column(name = "nickName", length = 20)
    private String nickName;

    @Column(name = "avatar", length = 20)
    private String avatar;

    public Users() {

    }

    public Users(String userName, String mobile, String nickName, String avatar) {
        this.userName = userName;
        this.mobile = mobile;
        this.nickName = nickName;
        this.avatar = avatar;
    }
}
