package kr.or.zmapadmin.common.exption;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@ToString
public class ApiExceptionEntity {
  private String errorCode;
  private String errorMessage;
  private String path;
  private ZonedDateTime timestamp;


  @Builder
  public ApiExceptionEntity(HttpStatus status, String errorCode, String errorMessage, String path, ZonedDateTime timestamp){
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.path = path;
    this.timestamp = timestamp;
  }
}
