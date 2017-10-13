package com.liketry.interaction.benison.service;

import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.model.Screen;
import com.liketry.interaction.benison.model.Spot;
import com.liketry.interaction.benison.vo.api.ScreenVo;
import javafx.scene.Scene;

import java.util.HashMap;
import java.util.List;

/**
 * 屏幕Service
 *
 * @author Simon
 */
public interface ScreenService {

    int insert(Screen screen);

    int delete(String screenId);

    int update(Screen screen);

    PageInfo<HashMap<String, String>> findByStatus(int page, int rows, String status);

    Screen findById(String screenId);

    List<Screen> findByScreenName(String screenName);

    List<Screen> findNormalScreenAll();

    List<String> findHotScreenId(int screenSize);

    List<String> findExtraScreenId(List<String> extra, int screenSize);

    ScreenVo getAroundScreen(double longitude, double latitude, int radius);

    //根据屏幕id获取景区id
    String findByScreenId(String screenId);
}
