package com.liketry.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liketry.domain.User;
import com.liketry.service.UserService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by pengyy
 */
@RestController
@RequestMapping("/sys/customer")
@EnableSwagger2
public class UserController extends BaseController<UserService,User>{

}
