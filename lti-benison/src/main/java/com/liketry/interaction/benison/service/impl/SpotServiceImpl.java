package com.liketry.interaction.benison.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.dao.SpotMapper;
import com.liketry.interaction.benison.model.Spot;
import com.liketry.interaction.benison.service.SpotService;
import com.liketry.interaction.benison.vo.api.SpotVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 景区服务实现类
 *
 * @author Simon
 */
@Service
public class SpotServiceImpl implements SpotService {
    @Autowired
    SpotMapper spotMapper;

    @Override
    public int addSpot(Spot spot) {
        return spotMapper.insert(spot);
    }

    /**
     * 已分页方式根据状态获取景区信息
     *
     * @param pageSize
     * @param pageNumber
     * @param spotStatus
     * @return 带分页数据的景区信息
     */
    @Override
    public PageInfo<Spot> findByStatus(int pageSize, int pageNumber, String spotStatus) {
        PageHelper.startPage(pageSize, pageNumber);
        return new PageInfo<>(spotMapper.selectAll(spotStatus));
    }

    @Override
    public List<SpotVo> findApiAll() {
        return spotMapper.selectApiAll();
    }

    @Override
    public int updateSpot(Spot spot) {
        return spotMapper.updateByPrimaryKey(spot);
    }


    @Override
    public int deleteSpot(String spotId) {
        return spotMapper.deleteSport(spotId);
    }

    /**
     *  获取非删除的所有景区的基本信息（id，name）
     * @return
     */
    @Override
    public List<Spot> findByBaseAll() {
        return spotMapper.selectBaseAll();
    }
}
