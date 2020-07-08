package com.model;

import com.enump.MessageEnum;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Message",
        uniqueConstraints = { @UniqueConstraint( columnNames = { "mobile","ticket" } ) } )
public class Message extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "Message_sequence", strategy = "sequence", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequenceName", value = "Message_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1"),
    })
    @GeneratedValue(generator = "Message_sequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", length = 25, nullable = false)
    private Long id;

    /*only no register*/
    @Column(name = "mobile")
    private String mobile;

    /*only no register*/
    @Column(name = "ip")
    private String ip;

    /*only no register*/
    @Column(name = "ticket")
    private String ticket;

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageEnum type;

    @Column(name = "comment", nullable = false, length = 500)
    private String comment;

    @Column(name = "USER_ID")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    Users users;

    @Column(name = "answered")
    private Boolean answered;

    @Column(name = "answer", length = 2000)
    private String answer;

    @Column(name = "date_str")
    private Long dateStr;
}
