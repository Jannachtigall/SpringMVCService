package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
   В данном @Data классе аннотация @NoArgsConstructor используется,
   так как он необходим для работы аннотации @ModelAtribute
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String name;
}
