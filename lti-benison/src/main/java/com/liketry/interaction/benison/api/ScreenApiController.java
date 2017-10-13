package com.liketry.interaction.benison.api;

import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.model.Screen;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.service.ScreenService;
import com.liketry.interaction.benison.util.AreaUtil;
import com.liketry.interaction.benison.vo.api.ScreenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 大屏api
 *
 * @author Simon
 */
@RestController
@RequestMapping("screen_api")
public class ScreenApiController {
    private final int HOT_SCREEN_SIZE = 5;//热门屏幕数量

    @Autowired
    ScreenService screenService;

    /**
     * 根据屏幕id获取屏幕
     *
     * @param screenId
     * @return 返回屏幕详细信息
     */
    @RequestMapping("getScreen")
    Result<Screen> getScreen(@RequestParam String screenId) {
        return new Result<>(SystemConstants.RESULT_SUCCESS, screenService.findById(screenId));
    }

    /**
     * 根据屏幕名称搜索相似屏幕
     *
     * @param screenName 屏幕名称的某些字段
     * @return
     */
    @RequestMapping("searchScreen")
    Result<List<Screen>> searchScreen(@RequestParam String screenName) {
        return new Result<>(SystemConstants.RESULT_SUCCESS, screenService.findByScreenName(screenName));
    }

    /**
     * 获取所有大屏信息
     *
     * @return
     */
    @RequestMapping("getScreenAll")
    Result<List<Screen>> getScreenAll() {
        return new Result<>(SystemConstants.RESULT_SUCCESS, screenService.findNormalScreenAll());
    }

    //获取热门景区(如果不够 HOT_SCREEN_SIZE 所定义的个数，则在默认取够)
    @RequestMapping("getHotSpot")
    Result<List<Screen>> getHotSpot() {
        List<Screen> result = new ArrayList<>();
        List<String> ids = screenService.findHotScreenId(HOT_SCREEN_SIZE);
        if (ids != null && !ids.isEmpty()) {
            for (String id : ids) {
                Screen screen = screenService.findById(id);
                if (screen != null)
                    result.add(screen);
            }
        }
        if (result.size() < HOT_SCREEN_SIZE) {//如果热门不够十个
            List<String> selectId = new ArrayList<>();
            for (Screen screen : result) {
                selectId.add(screen.getScreenId());
            }
            List<String> extraIds = screenService.findExtraScreenId(selectId, HOT_SCREEN_SIZE);
            for (String extraId : extraIds) {
                Screen screen = screenService.findById(extraId);
                if (screen != null)
                    result.add(screen);
                if (result.size() == HOT_SCREEN_SIZE)
                    break;
            }
        }
        return new Result<>(SystemConstants.RESULT_SUCCESS, result);
    }

    //根据经纬度和查询范围，返回附近的大屏;
    @RequestMapping("getAroundScreen")
    public Result< ScreenVo > getAroundScreen(@RequestParam double longitude, @RequestParam double latitude, @RequestParam int radius) {
         ScreenVo  screens = screenService.getAroundScreen(longitude, latitude, radius);
        return new Result<>(SystemConstants.RESULT_SUCCESS, screens);
    }
}