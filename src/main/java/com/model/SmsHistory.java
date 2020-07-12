package com.model;

import com.enump.SmsTypeEnum;
import com.tools.CorrectDate;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "SmsHistory")
public class SmsHistory extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "SmsHistory_sequence", strategy = "sequence", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequenceName", value = "SmsHistory_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1"),
    })
    @GeneratedValue(generator = "SmsHistory_sequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", length = 25, nullable = false)
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    Users users;

    @Column(name = "smsType", nullable = false)
    @Enumerated(EnumType.STRING)
    private SmsTypeEnum smsType;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "date_str", nullable = false)
    private Long dateStr;

    public SmsHistory(SmsTypeEnum smsType, String mobile, String message) {
        this.smsType = smsType;
        this.mobile = mobile;
        this.message = message;
        this.dateStr = Long.valueOf(CorrectDate.miladiToShamsi(new Date(), ""));
    }

    public SmsHistory(Long userId, SmsTypeEnum smsType, String mobile, String message) {
        this.userId = userId;
        this.smsType = smsType;
        this.mobile = mobile;
        this.message = message;
        this.dateStr = Long.valueOf(CorrectDate.miladiToShamsi(new Date(), ""));
    }
}
