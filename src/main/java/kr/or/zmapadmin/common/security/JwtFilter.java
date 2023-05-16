package kr.or.zmapadmin.common.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import kr.or.zmapadmin.common.exption.ApiException;
import kr.or.zmapadmin.common.exption.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
  public static final String AUTHORIZATION_HEADER = "x-auth-token";
  private final JwtAuthTokenProvider tokenProvider;

  private static final List<String> EXCLUDE_URL = Collections.unmodifiableList(
          Arrays.asList(
                  "/user/login"
                  ,"/user/join"
          )
  );

  public JwtFilter(JwtAuthTokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request){
    return EXCLUDE_URL.stream().anyMatch(exclude-> exclude.equalsIgnoreCase(request.getServletPath()));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    response.setCharacterEncoding("utf-8");

    Cookie[] cookies = request.getCookies();
    if(cookies != null){
      for(Cookie cookie : cookies){
        log.info("name : {}, value : {}", cookie.getName(), cookie.getValue());
      }
    }
    PrintWriter writer = response.getWriter();
    Optional<String> token = resolveToken(request);
    try {


      if(token.isPresent()){
        JwtAuthToken jwtAuthToken = tokenProvider.convertAuthToken(token.get());

        if(jwtAuthToken.validate()) {
          Authentication authentication = tokenProvider.getAuthentication(jwtAuthToken);
          SecurityContextHolder.getContext().setAuthentication(authentication);
          //filterChain.doFilter(request, response);
        }
      }else {
        writer.println(ExceptionEnum.ERROR_TOKEN_0001.getCode());
      }

    } catch (SecurityException | MalformedJwtException e) {// 손상된 토큰
      writer.println(ExceptionEnum.ERROR_TOKEN_0001.getCode());
    } catch (ExpiredJwtException e) {// 만료된 토큰
      writer.println(ExceptionEnum.ERROR_TOKEN_0002.getCode());
    } catch (UnsupportedJwtException e) {// 지원하지 않는 토큰
      writer.println(ExceptionEnum.ERROR_TOKEN_0003.getCode());
    } catch (IllegalArgumentException e) {// 적합하지 않는 토큰
      writer.println(ExceptionEnum.ERROR_TOKEN_0004.getCode());
    } catch (Exception e) {
      log.error("================================================");
      log.error("JwtFilter - doFilterInternal() 오류발생");
      log.error("token : {}", token);
      log.error("Exception Message : {}", e.getMessage());
      log.error("Exception StackTrace : {");
      e.printStackTrace();
      log.error("}");
      log.error("================================================");
      request.setAttribute("exception", ExceptionEnum.RUNTIME_EXCEPTION.getCode());
    }
    filterChain.doFilter(request, response);
  }

  private Optional<String> resolveToken(HttpServletRequest request) {
    String authToken = request.getHeader(AUTHORIZATION_HEADER);
    if(StringUtils.hasText(authToken)){
      return Optional.of(authToken);
    } else
      return Optional.empty();
  }
}
