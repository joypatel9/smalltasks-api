package com.joypatel.smalltasks.user.entities;

import com.joypatel.smalltasks.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_user")
@Getter
@Setter
public class User extends AbstractEntity<Integer> {

    public static final int MOBILE_LEN = 10;
    public static final int PASSWORD_MIN = 8;
    public static final int PASSWORD_MAX = 32;
    public static final int PINCODE_MIN = 100000;
    public static final int PINCODE_MAX = 999999;
    private static final int NAME_MAX = 50;
    @Column(nullable = false, unique = true, length = MOBILE_LEN)
    private String mobile;

    @Column(nullable = false, length = NAME_MAX)
    private String name;

    @Column(nullable = false) // Encrypted password will be longer than PASSWORD_MAX
    private String password;

    @Column(nullable = false)
    private Integer pincode;
}
