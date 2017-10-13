package com.liketry.interaction.benison.service;

import com.liketry.interaction.benison.model.BenisonTemplate;
import com.liketry.interaction.benison.vo.api.BenisonVo;

import java.util.List;

/**
 * 祝福语service
 *
 * @author Simon
 */
public interface BenisonService {

    List<BenisonVo> findBenisonAll();

    List<BenisonTemplate> findBenisonTemplate(String screenId, String imgId, String typeId, String benisonId);

    public BenisonTemplate findBenisonTemplate(String benisonId);

    public BenisonTemplate findBenisonTemplateById(String benisonId);
}
