package com.joypatel.smalltasks.common.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity<ID extends Serializable> {

    @Id
    @GeneratedValue
    private ID id;

    @Column(nullable = false, length=36, unique = true)
    private String ref;

    @CreatedBy
    private Integer createdById;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedBy
    private Integer lastModifiedById;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Version
    private Long version;
}
