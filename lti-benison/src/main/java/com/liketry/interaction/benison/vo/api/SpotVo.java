package com.liketry.interaction.benison.vo.api;

import com.liketry.interaction.benison.model.Spot;

import java.util.List;

/**
 * 景区详细信息
 *
 * @author Simon
 */
public class SpotVo extends Spot {
    private List<ScreenVo> screens;

    public List<ScreenVo> getScreens() {
        return screens;
    }

    public void setScreens(List<ScreenVo> screens) {
        this.screens = screens;
    }
}
