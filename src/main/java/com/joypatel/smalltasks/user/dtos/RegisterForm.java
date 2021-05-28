package com.joypatel.smalltasks.user.dtos;

import com.joypatel.smalltasks.user.validation.UniqueMobile;
import com.joypatel.smalltasks.user.validation.ValidMobile;
import com.joypatel.smalltasks.user.validation.ValidName;
import com.joypatel.smalltasks.user.validation.ValidPassword;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "password")
public class RegisterForm {

    @ValidMobile
    @UniqueMobile
    private String mobile;

    @ValidName
    private String name;

    @ValidPassword
    private String password;
}
