package com.model;

import com.googlecode.jmapper.annotations.JMap;
import com.view.GroupView;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

    public void setCreateDate(Date createDate) {
        this.createDate = new Date();
    }

}
