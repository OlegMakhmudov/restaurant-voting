package ru.javaops.bootjava.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTo {
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 5, max = 100)
    private String password;
}
