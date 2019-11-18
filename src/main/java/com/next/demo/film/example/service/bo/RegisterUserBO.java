package com.next.demo.film.example.service.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserBO {

    private String uuid;
    private String username;
    private String userpwd;


}
