package kr.or.zmapadmin.model.entity;


import kr.or.zmapadmin.model.vo.UserInfoVo;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Builder
@Table(name="TB_ADMIN_USER_INFO")
public class UserInfoEntity {

    @Column(name="seq")
    private Long seq;

    @Id
    @Column(name="id")
    private String id;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="role")
    private String role;

    public UserInfoVo toDomain(){

        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setId(id);
        userInfoVo.setName(name);
        userInfoVo.setEmail(email);
        userInfoVo.setRole(role);

        return userInfoVo;
    }

}
