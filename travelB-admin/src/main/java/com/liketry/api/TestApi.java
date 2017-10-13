package com.liketry.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * api示例
 *
 * @author pengyy
 */
@RestController
@RequestMapping("api/test_api")
public class TestApi {
	
    @RequestMapping("")
    String test() {
        return "Ok";
    }
    
}