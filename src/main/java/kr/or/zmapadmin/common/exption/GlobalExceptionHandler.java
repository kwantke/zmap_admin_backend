package kr.or.zmapadmin.common.exption;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ApiException.class})
  public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final ApiException e) {
      //e.printStackTrace();
    return createCustomErrorInfo(e.getError().getCode(), request.getServletPath(), e.getMessage());

  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final RuntimeException e) {
    //e.printStackTrace();
    return createCustomErrorInfo(ExceptionEnum.RUNTIME_EXCEPTION.getCode(), request.getServletPath(), e.getMessage());

  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final Exception e) {
    //e.printStackTrace();
    return createCustomErrorInfo(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode(), request.getServletPath(), e.getMessage());

  }

  private ResponseEntity<ApiExceptionEntity> createCustomErrorInfo(String code, String path, String message){
    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    log.error("Error code: {}, path: {}, message: {}",code, path, message);
    return ResponseEntity
            .status(400)
            .body(ApiExceptionEntity.builder()
                    .errorCode(code)
                    .errorMessage(message)
                    .path(path)
                    .timestamp(now)
                    .build());
  }


}
