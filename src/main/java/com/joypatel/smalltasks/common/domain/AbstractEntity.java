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

import static com.joypatel.smalltasks.common.MyUtils.UUID_LEN;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity<ID extends Serializable> {

    @Id
    @GeneratedValue
    private ID id;

    @Column(nullable = false, length = UUID_LEN, unique = true)
    private String ref;

    @CreatedBy
    private Integer createdById;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date createdDate;

    @LastModifiedBy
    private Integer lastModifiedById;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date lastModifiedDate;

    @Version
    private Long version;
}
