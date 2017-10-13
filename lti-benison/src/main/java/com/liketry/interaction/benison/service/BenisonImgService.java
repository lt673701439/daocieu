package com.liketry.interaction.benison.service;

import java.util.List;

import com.liketry.interaction.benison.model.BenisonImg;

/**
 * 背景图service
 *
 * @author pengyy
 */
public interface BenisonImgService {

    List<BenisonImg> findBenisonImgAll(String imgName);

}
