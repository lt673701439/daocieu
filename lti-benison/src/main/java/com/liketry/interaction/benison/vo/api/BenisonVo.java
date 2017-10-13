package com.liketry.interaction.benison.vo.api;

import com.liketry.interaction.benison.model.Benison;
import com.liketry.interaction.benison.model.BenisonType;

import java.util.List;

/**
 *祝福语信息
 *
 *@author Simon
 */
public class BenisonVo extends BenisonType{
    private List<Benison> benisons = null;

    public List<Benison> getBenisons() {
        return benisons;
    }

    public void setBenisons(List<Benison> benisons) {
        this.benisons = benisons;
    }
}
