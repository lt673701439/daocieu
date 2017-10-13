package com.liketry.interaction.benison.controller;

import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.model.Spot;
import com.liketry.interaction.benison.service.SpotService;
import com.liketry.interaction.benison.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 景区管理Controller
 *
 * @author Simon
 */
@Controller
@RequestMapping("spot")
public class SpotController {
    private static final Logger log = LoggerFactory.getLogger(SpotController.class);

    @Autowired
    SpotService service;

    /**
     * 获取景区数据分页页面
     *
     * @return
     */
    @RequestMapping()
    String getScenicSpotAll() {
        return "/view/configure/spot.jsp";
    }

    /**
     * 景区信息分页获取
     *
     * @param page
     * @param rows
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_spot_page")
    Map<String, Object> getScenicSpotPage(int page, int rows, @RequestParam(required = false) String status) {
        PageInfo<Spot> pageInfo = service.findByStatus(page, rows, status);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
        return map;
    }

    /**
     * 添加景区
     *
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "add_spot", method = RequestMethod.POST)
    Map<String, Object> addScenicSpot(Spot data) {
        Map<String, Object> result = new HashMap<>();
        if (!validate(data, true)) {
            result.put(SystemConstants.RESULT, false);
        } else {
            data.setSpotId(UUID.randomUUID().toString().replace("-", ""));
            int addStatus = service.addSpot(data);
            result.put(SystemConstants.RESULT, addStatus == 1);
        }
        return result;
    }

    /**
     * 修改景区
     *
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update_spot", method = RequestMethod.POST)
    Map<String, Object> updateScenicSpot(Spot data) {
        Map<String, Object> result = new HashMap<>();
        if (!validate(data, false)) {
            result.put(SystemConstants.RESULT, false);
        } else {
            int changeStatus = service.updateSpot(data);
            result.put(SystemConstants.RESULT, changeStatus == 1);
        }
        return result;
    }

    /**
     * 删除景区
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete_spot", method = RequestMethod.POST)
    Map<String, Object> deleteScenicSpot(String id) {
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isEmpty(id)) {
            result.put(SystemConstants.RESULT, false);
        } else {
            int changeStatus = service.deleteSpot(id);
            result.put(SystemConstants.RESULT, changeStatus == 1);
        }
        return result;
    }

    /**
     * 数据校验
     *
     * @param data
     * @param isAdd
     * @return
     */
    private boolean validate(Spot data, boolean isAdd) {
        if (data == null)
            return false;
        if (!isAdd && StringUtils.isEmpty(data.getSpotId()))
            return false;
        return !(StringUtils.isEmpty(data.getSpotName()) ||
                StringUtils.isEmpty(data.getSpotCity()) ||
                StringUtils.isEmpty(data.getSpotAddress()) ||
                StringUtils.isEmpty(data.getSpotProvince()) ||
                StringUtils.isEmpty(data.getSpotCode()) ||
                StringUtils.isEmpty(data.getSpotStatus()));
    }
}