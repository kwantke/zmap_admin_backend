package kr.or.zmapadmin.common.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class PasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

  private String id;
  private String role;
  private String name;
  private String email;
  private String phone;

  public PasswordAuthenticationToken(Object principal, Object credentials) {
    super(principal, credentials);
  }

  public PasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }
}
