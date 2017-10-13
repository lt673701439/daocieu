package com.liketry.interaction.benison.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.liketry.interaction.benison.api.BenisonImgApiController;
import com.liketry.interaction.benison.dao.BenisonImgMapper;
import com.liketry.interaction.benison.model.BenisonImg;
import com.liketry.interaction.benison.service.BenisonImgService;

/**
 * 背景图service实现类
 *
 * @author pengyy
 */
@Service
public class BenisonImgServiceImpl implements BenisonImgService {
	
	private static final Logger log = LoggerFactory.getLogger(BenisonImgApiController.class);
	
    @Autowired
    private BenisonImgMapper benisonImgMapper;

    @Override
    public List<BenisonImg> findBenisonImgAll(String imgName) {
    	List<BenisonImg> benisonImgList = benisonImgMapper.findBenisonImgAll(imgName);
    	log.info("<=====BenisonImgApiController.getBenisonImgList=返回参数：{}============>",JSONObject.toJSON(benisonImgList));
        return benisonImgList;
    }

}
