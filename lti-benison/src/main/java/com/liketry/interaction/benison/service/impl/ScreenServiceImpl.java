package com.liketry.interaction.benison.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.dao.ScreenMapper;
import com.liketry.interaction.benison.model.Screen;
import com.liketry.interaction.benison.service.ScreenService;
import com.liketry.interaction.benison.util.AreaUtil;
import com.liketry.interaction.benison.vo.api.ScreenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 屏幕Service实现类
 *
 * @author Simon
 */
@Service
public class ScreenServiceImpl implements ScreenService {
    @Autowired
    ScreenMapper screenMapper;

    @Override
    public int insert(Screen screen) {
        return screenMapper.insert(screen);
    }

    @Override
    public int delete(String screenId) {
        return screenMapper.deleteLogicById(screenId);
    }

    @Override
    public int update(Screen screen) {
        return screenMapper.updateByPrimaryKey(screen);
    }

    /**
     * 分页获取屏幕信息
     *
     * @param pageNumber
     * @param pageSize
     * @param status
     * @return
     */
    @Override
    public PageInfo<HashMap<String, String>> findByStatus(int pageNumber, int pageSize, String status) {
        PageHelper.startPage(pageNumber, pageSize);
        return new PageInfo<>(screenMapper.selectAll(status));
    }

    @Override
    public Screen findById(String screenId) {
        return screenMapper.selectByPrimaryKey(screenId);
    }

    @Override
    public List<Screen> findByScreenName(String screenName) {
        return screenMapper.selectByName(screenName);
    }

    /**
     * 移动端使用，获取所有正常屏幕信息
     *
     * @return
     */
    @Override
    public List<Screen> findNormalScreenAll() {
        return screenMapper.selectNormalAll();
    }

    //获取热门景区
    @Override
    public List<String> findHotScreenId(int screenSize) {
        return screenMapper.selectHotScreenId(screenSize);
    }

    /**
     * 获取额外指定数量的屏幕
     *
     * @param extra      要排除的screenid
     * @param screenSize 要获取的数量
     * @return 指定数量的屏幕id
     */
    @Override
    public List<String> findExtraScreenId(List<String> extra, int screenSize) {
        return screenMapper.findExtraScreenId(extra, screenSize);
    }

    /**
     * 根据经纬度返回符合要求最近的屏幕集合
     *
     * @param longitude
     * @param latitude
     * @param radius
     * @return
     */
    @Override
    public ScreenVo getAroundScreen(double longitude, double latitude, int radius) {
        double[] rectangleAround = AreaUtil.getRectangleAround(longitude, latitude, radius);
        List<ScreenVo> screens = screenMapper.aroundScreen(rectangleAround[0], rectangleAround[2], rectangleAround[1], rectangleAround[3]);
        if (!screens.isEmpty()) {
            Iterator<ScreenVo> iterator = screens.iterator();
            while (iterator.hasNext()) {
                ScreenVo data = iterator.next();
                double distance = AreaUtil.getDistance(longitude, latitude, Double.valueOf(data.getScreenLongitude()), Double.valueOf(data.getScreenDimension()));
                if (distance > radius)
                    iterator.remove();
                else
                    data.setDistance(distance);
            }
        }
        screens.sort(new Comparator<ScreenVo>() {
            @Override
            public int compare(ScreenVo o1, ScreenVo o2) {
                return (int) (o1.getDistance() - o2.getDistance());
            }
        });
        if (screens.isEmpty()) {
            return null;
        } else {
            return screens.get(0);
        }
    }

    @Override
    public String findByScreenId(String screenId) {
        return screenMapper.findByScreenId(screenId);
    }
}