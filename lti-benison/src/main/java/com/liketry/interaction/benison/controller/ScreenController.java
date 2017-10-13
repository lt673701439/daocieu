package com.liketry.interaction.benison.controller;

import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.model.Screen;
import com.liketry.interaction.benison.service.ScreenService;
import com.liketry.interaction.benison.service.SpotService;
import com.liketry.interaction.benison.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 屏幕Controller
 *
 * @author Simon
 */
@Controller
@RequestMapping("screen")
public class ScreenController {
    @Autowired
    SpotService spotService;
    @Autowired
    ScreenService screenService;

    @RequestMapping()
    ModelAndView getScenicSpotAll() {
        ModelAndView mv = new ModelAndView("/view/configure/screen.jsp");
        mv.addObject("spots", spotService.findByBaseAll());
        return mv;
    }

    @ResponseBody
    @RequestMapping("get_screen_page")
    Map<String, Object> getScreen(int page, int rows, @RequestParam(required = false) String status) {
        PageInfo<HashMap<String, String>> pageInfo = screenService.findByStatus(page, rows, status);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "add_screen", method = RequestMethod.POST)
    Map<String, Object> addScreen(Screen screen) {
        Map<String, Object> map = new HashMap<>();
        if (!validate(screen, true)) {
            map.put(SystemConstants.RESULT, false);
        } else {
            screen.setScreenId(UUID.randomUUID().toString().replace("-", ""));
            int status = screenService.insert(screen);
            map.put(SystemConstants.RESULT, status == 1);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "update_screen", method = RequestMethod.POST)
    Map<String, Object> updateScreen(Screen screen) {
        Map<String, Object> map = new HashMap<>();
        if (!validate(screen, false)) {
            map.put(SystemConstants.RESULT, false);
        } else {
            screen.setScreenId(UUID.randomUUID().toString().replace("-", ""));
            int status = screenService.update(screen);
            map.put(SystemConstants.RESULT, status == 1);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "delete_screen", method = RequestMethod.POST)
    Map<String, Object> deleteScreen(String screenId) {
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isEmpty(screenId)) {
            result.put(SystemConstants.RESULT, false);
        } else {
            int changeStatus = screenService.delete(screenId);
            result.put(SystemConstants.RESULT, changeStatus == 1);
        }
        return result;
    }


    //屏幕信息校验

    private boolean validate(Screen screen, boolean isAdd) {
        if (screen == null)
            return false;
        if (!isAdd && StringUtils.isEmpty(screen.getScreenId()))
            return false;
        if (StringUtils.isEmpty(screen.getScreenName()) ||
                StringUtils.isEmpty(screen.getSpotId()) ||
                StringUtils.isEmpty(screen.getScreenCode()) ||
                StringUtils.isEmpty(screen.getScreenStatus()) ||
                StringUtils.isEmpty(screen.getScreenLocation()) ||
                StringUtils.isEmpty(screen.getScreenLongitude()) ||
                StringUtils.isEmpty(screen.getScreenDimension()) ||
                StringUtils.isEmpty(screen.getScreenLong()) ||
                StringUtils.isEmpty(screen.getScreenWide()) ||
                StringUtils.isEmpty(screen.getScreenSize()) ||
                StringUtils.isEmpty(screen.getScreenResolution())) {
            return false;
        }
        try {
            float longitude = Float.valueOf(screen.getScreenLongitude());
            float dimension = Float.valueOf(screen.getScreenDimension());
            if (longitude < -180 || longitude > 180 || dimension < -90 || dimension > 90)
                return false;
            float width = Float.valueOf(screen.getScreenWide());
            float height = Float.valueOf(screen.getScreenLong());
            float size = Float.valueOf(screen.getScreenSize());
            if (width <= 0 || height <= 0 || size <= 0)
                return false;
            String[] resolution = screen.getScreenResolution().split("\\*");
            float resolutionWidth = Float.valueOf(resolution[0]);
            float resolutionHeight = Float.valueOf(resolution[1]);
            return !(resolutionWidth <= 0 || resolutionHeight <= 0 || resolutionHeight % 1 != 0 || resolutionHeight % 1 != 0);
        } catch (Exception e) {
            return false;
        }
    }
}
