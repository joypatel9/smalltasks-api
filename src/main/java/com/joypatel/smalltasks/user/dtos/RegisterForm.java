package com.joypatel.smalltasks.user.dtos;

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

    @NotNull
    @Size(min = 10, max = 10, message="{exactSize}")
    @Pattern(regexp = "^[0-9]+$")
    private String mobile;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Size(min = 8, max = 32)
    private String password;
}
