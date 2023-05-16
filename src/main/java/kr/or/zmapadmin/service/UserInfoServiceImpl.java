package kr.or.zmapadmin.service;

import kr.or.zmapadmin.common.exption.ApiException;
import kr.or.zmapadmin.common.exption.ExceptionEnum;
import kr.or.zmapadmin.common.security.JwtAuthToken;
import kr.or.zmapadmin.common.security.JwtAuthTokenProvider;
import kr.or.zmapadmin.common.security.PasswordAuthenticationToken;
import kr.or.zmapadmin.model.entity.UserInfoEntity;
import kr.or.zmapadmin.model.vo.UserInfoVo;
import kr.or.zmapadmin.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserInfoServiceImpl {


    UserInfoRepository userInfoRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtAuthTokenProvider tokenProvider;
    @Autowired
    UserInfoServiceImpl(UserInfoRepository userInfoRepository
                      , AuthenticationManager authenticationManager
                      , JwtAuthTokenProvider tokenProvider
                        ){
        this.userInfoRepository = userInfoRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    public UserInfoVo login(UserInfoVo reqInfoVo){
        /*Optional<UserInfoEntity> userInfoEntity = Optional.of(userInfoRepository.findByIdAndPassword(reqInfoVo.getId(),reqInfoVo.getPassword())
                .orElseThrow(() -> new ApiException(ExceptionEnum.ERROR_0001)));
        UserInfoVo userInfoVo = userInfoEntity.get().toDomain();*/

        PasswordAuthenticationToken token = new PasswordAuthenticationToken(reqInfoVo.getId(),reqInfoVo.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String userToken = createToken((PasswordAuthenticationToken) authentication);

        reqInfoVo.setPassword("");

        reqInfoVo.setToken(userToken.substring(9,userToken.length()-1));

        return reqInfoVo;
    }
    public String createToken(PasswordAuthenticationToken token){
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant());
        Map<String,String> claims = new HashMap<>();
        claims.put("id",token.getId());
        claims.put("name",token.getName());

        JwtAuthToken jwtAuthToken = tokenProvider.createAuthToken(
                token.getPrincipal().toString()
                ,token.getAuthorities().iterator().next().getAuthority()
                ,claims
                ,expiredDate
        );

        return jwtAuthToken.getToken(jwtAuthToken);
    }

    public Optional<UserInfoEntity> findById(String toString) {
        Optional<UserInfoEntity> test  =  userInfoRepository.findById(toString);
        return test;
    }
}
