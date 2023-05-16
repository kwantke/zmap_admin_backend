package kr.or.zmapadmin.model.vo;

import lombok.Data;

@Data
public class UserInfoVo {

    private String id;

    private String password;

    private String name;

    private String email;

    private String role;

    private String token;

}
