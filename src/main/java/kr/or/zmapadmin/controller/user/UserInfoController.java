package kr.or.zmapadmin.controller.user;

import kr.or.zmapadmin.model.vo.UserInfoVo;
import kr.or.zmapadmin.service.UserInfoServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserInfoController {


    UserInfoServiceImpl userInfoService;

    @Autowired
    public UserInfoController(UserInfoServiceImpl userInfoService){
        this.userInfoService = userInfoService;
    }
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody UserInfoVo userInfoVo){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userInfoService.login(userInfoVo));
    }

}
