package com.liketry.interaction.benison.vo.api;

import com.liketry.interaction.benison.model.Screen;

/**
 * 屏幕信息
 *
 * @author Simon
 */
public class ScreenVo extends Screen {
    private String spotName;
    private double distance;

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}