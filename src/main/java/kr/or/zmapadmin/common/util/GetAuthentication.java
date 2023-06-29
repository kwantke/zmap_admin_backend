package kr.or.zmapadmin.common.util;

import kr.or.zmapadmin.model.vo.UserInfoVo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetAuthentication {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public UserInfoVo getPrincipal(){

        return (UserInfoVo) authentication.getPrincipal();
    }
}
