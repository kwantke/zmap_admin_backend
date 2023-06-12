package kr.or.zmapadmin.common.security;

public interface AuthToken<T> {
  String AUTHORITIES_KEY = "role";
  boolean validate();
  T getData();
}
