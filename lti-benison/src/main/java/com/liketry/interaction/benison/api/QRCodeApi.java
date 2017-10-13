package com.liketry.interaction.benison.api;

import com.liketry.interaction.benison.constants.Constants;
import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.model.BenisonTemplate;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.util.QRCodeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 二维码制作
 *
 * @author Simon
 */
@RestController
@RequestMapping("qr_code_api")
public class QRCodeApi {
    private final int ERROR_MERCHANT_ID = 11;
    private final int ERROR_CREATE_FAILED = 12;

    @PostMapping("create")
    Result<String> get(@RequestParam String merchantId) {
        if (merchantId == null || merchantId.length() != 32)
            return new Result<>(ERROR_MERCHANT_ID, "id validate failed");
        String code = QRCodeUtils.createMINACode(merchantId);
        if (code == null)
            return new Result<>(ERROR_CREATE_FAILED, "create failed");
        else
            return new Result<>(SystemConstants.RESULT_SUCCESS, code);
    }
}