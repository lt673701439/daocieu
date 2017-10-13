package com.liketry.web.vm;

import lombok.Data;

@Data
public class CensorBodyVM {
    private String merchantId;
    private Boolean status;
    private String reason;
}
