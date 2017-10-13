package com.liketry.interaction.benison.api;

import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.service.SpotService;
import com.liketry.interaction.benison.vo.api.SpotVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 景区管理api
 *
 * @author Simon
 */
@RestController
@RequestMapping("spot_api")
public class SpotApiController {
    @Autowired
    private SpotService service;

    //获取所有景区
    @RequestMapping("getSpotAll")
    Result<List<SpotVo>> getAll() {
        return new Result<>(SystemConstants.RESULT_SUCCESS, service.findApiAll());
    }
}