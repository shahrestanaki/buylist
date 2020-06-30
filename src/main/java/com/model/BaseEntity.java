package com.model;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @JMap
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_Date", nullable = false)

    public Date createDate;

    @JMap
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "change_Date")
    public Date changeDate;

    @PrePersist
    public void prePersist() {
        this.createDate = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.changeDate = new Date();
    }

}
