package kr.or.zmapadmin.common.exption;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
  RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "zero_EXCEPTION"), // 400 에러

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "zero_SERVER_ERROR"), // 500 에러

  ERROR_0001(HttpStatus.NOT_FOUND, "ERROR_0001","해당 아이디가 존재하지 않습니다."),
  ERROR_0002(HttpStatus.BAD_REQUEST, "ERROR_0002","비밀번호가 일치하지 않습니다."),
  ERROR_TOKEN_0001(HttpStatus.BAD_REQUEST, "ERROR_TOKEN_0001","손상된 토큰입니다."),
  ERROR_TOKEN_0002(HttpStatus.BAD_REQUEST, "ERROR_TOKEN_0002","만료된 토큰입니다."),
  ERROR_TOKEN_0003(HttpStatus.BAD_REQUEST, "ERROR_TOKEN_0003","지원하지 않는 토큰입니다."),
  ERROR_TOKEN_0004(HttpStatus.BAD_REQUEST, "ERROR_TOKEN_0004","적합하지 않는 토큰입니다."),

  //커스텀 에러
  ZERO_0001(HttpStatus.NOT_FOUND, "ZERO_0001","데이터가 없습니다."),

  ZERO_0002(HttpStatus.BAD_REQUEST, "ZERO_0002","입력 파라미터가 잘못되었습니다."),

  ZERO_0003(HttpStatus.CONFLICT, "ZERO_0003", "동일한 데이터가 존재합니다."),

  ZERO_0004(HttpStatus.CONFLICT, "ZERO_0004", "데이터 등록이 되지 않았습니다."),
  ZERO_0005(HttpStatus.UNAUTHORIZED, "ZERO_0005", "대표자만 가능합니다.");

  private final HttpStatus status;
  private final String code;
  private String message;

  ExceptionEnum(HttpStatus status, String code) {
    this.status = status;
    this.code = code;
  }

  ExceptionEnum(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
  }
