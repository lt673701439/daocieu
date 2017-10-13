/**
 * @author Simon
 * created at 2017/5/18 18:09
 */
package com.liketry.interaction.benison.service;

import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.model.Spot;
import com.liketry.interaction.benison.vo.api.SpotVo;

import java.util.List;
import java.util.Map;

/**
 * 景区服务类
 *
 * @author Simon
 */
public interface SpotService {

    int addSpot(Spot spot);

    PageInfo<Spot> findByStatus(int pageSize, int pageNumber, String spotStatus);

    List<SpotVo> findApiAll();

    int updateSpot(Spot spot);

    int deleteSpot(String spotId);

    List<Spot> findByBaseAll();
}