package com.joypatel.smalltasks.user.dtos;

import com.joypatel.smalltasks.common.validation.ValidMobile;
import com.joypatel.smalltasks.common.validation.ValidName;
import com.joypatel.smalltasks.common.validation.ValidPassword;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString(exclude = "password")
public class RegisterForm {

    @ValidMobile
    private String mobile;

    @ValidName
    private String name;

    @ValidPassword
    private String password;
}
