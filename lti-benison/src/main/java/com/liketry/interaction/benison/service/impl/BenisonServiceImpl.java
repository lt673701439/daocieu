package com.liketry.interaction.benison.service.impl;

import com.liketry.interaction.benison.dao.BenisonTemplateMapper;
import com.liketry.interaction.benison.dao.BenisonTypeMapper;
import com.liketry.interaction.benison.model.BenisonTemplate;
import com.liketry.interaction.benison.service.BenisonService;
import com.liketry.interaction.benison.vo.api.BenisonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 祝福语实现类
 *
 * @author Simon
 */
@Service
public class BenisonServiceImpl implements BenisonService {
    @Autowired
    private BenisonTypeMapper benisonTypeMapper;
    @Autowired
    private BenisonTemplateMapper benisonTemplateMapper;

    @Override
    public List<BenisonVo> findBenisonAll() {
        return benisonTypeMapper.selectTypeAndContent();
    }

    @Override
    public List<BenisonTemplate> findBenisonTemplate(String screenId, String imgId, String typeId, String benisonId) {
        return benisonTemplateMapper.selectBenisonTemplate(screenId, imgId, typeId, benisonId);
    }

    @Override
    public BenisonTemplate findBenisonTemplate(String benisonId) {
        return benisonTemplateMapper.selectByPrimaryKey(benisonId);
    }

    public BenisonTemplate findBenisonTemplateById(String benisonId) {
        return benisonTemplateMapper.findBenisonTemplateById(benisonId);
    }
}
