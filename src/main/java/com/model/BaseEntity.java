package com.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_Date", nullable = false, updatable= false)

    public Date createDate;

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
