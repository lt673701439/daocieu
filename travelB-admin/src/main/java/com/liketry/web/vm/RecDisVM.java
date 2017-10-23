package com.liketry.web.vm;

import com.liketry.domain.RecDis;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecDisVM extends RecDis{

    private String bcBankType;
    private String bcBankName;
    private String bcBankNumber;
    private String bcReservedTelephone;
    private String bcUserName;

}
