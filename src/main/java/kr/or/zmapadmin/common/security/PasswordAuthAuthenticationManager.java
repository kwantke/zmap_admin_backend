package kr.or.zmapadmin.common.security;


import kr.or.zmapadmin.common.exption.ApiException;
import kr.or.zmapadmin.common.exption.ExceptionEnum;
import kr.or.zmapadmin.model.entity.UserInfoEntity;
import kr.or.zmapadmin.repository.UserInfoRepository;
import kr.or.zmapadmin.service.UserInfoServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PasswordAuthAuthenticationManager implements AuthenticationProvider {


  public final UserInfoRepository userInfoRepository;
  @NonNull
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Optional<UserInfoEntity> userInfoOptional = userInfoRepository.findById((String)authentication.getPrincipal().toString());

    if(userInfoOptional.isEmpty()){
      throw new ApiException(ExceptionEnum.ERROR_0001);
    }
    String pw = passwordEncoder.encode((String)authentication.getCredentials());
    UserInfoEntity userInfoEntity = userInfoOptional.get();
    if(!authentication.getCredentials().equals(userInfoEntity.getPassword())){
      throw new ApiException(ExceptionEnum.ERROR_0002);
    }

    PasswordAuthenticationToken token = new PasswordAuthenticationToken(userInfoEntity.getId(),userInfoEntity.getPassword()
    , Collections.singleton(new SimpleGrantedAuthority(userInfoEntity.getRole())));
    token.setId(userInfoEntity.getId());
    /*token.setRole(UserInfoEntity.getRole());*/
    token.setName(userInfoEntity.getName());
    token.setEmail(userInfoEntity.getEmail());
    return token;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(PasswordAuthenticationToken.class);
  }
}
