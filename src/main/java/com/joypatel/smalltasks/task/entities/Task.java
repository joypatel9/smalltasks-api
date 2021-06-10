package com.joypatel.smalltasks.task.entities;

import com.joypatel.smalltasks.common.domain.AbstractEntity;
import com.joypatel.smalltasks.user.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "t_task")
@Getter
@Setter
public class Task extends AbstractEntity<Integer> {

    private static final int SUBJECT_MAX = 30;
    private static final int DESCRIPTION_MAX = 5000;
    public static final int PINCODE_MIN = 100000;
    public static final int PINCODE_MAX = 999999;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(nullable = false, length = SUBJECT_MAX)
    private String subject;

    @Column(nullable = true, length = DESCRIPTION_MAX)
    private String description;

    @Column(nullable = false)
    private Integer originPincode;
}
