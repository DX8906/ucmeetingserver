package com.uc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserInfo {
    private String username;
    private String name;
    private String permission;
    private Integer id;
}
