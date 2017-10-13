package com.liketry.web;

import com.liketry.util.PushUtil;
import com.liketry.web.vm.ResultVM;
import org.apache.http.HttpStatus;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * author Simon
 * create 2017/9/1
 * 推送
 */
@RestController
@RequestMapping(value = "api/push", method = RequestMethod.POST)
public class PushController {

    //发送全体广播
    @RequestMapping("sendBroadcast")
    ResultVM sendBroadcast(@RequestParam String title, @RequestParam String setContent) {
        String body = PushUtil.getBroadCast(title, setContent);
        PushUtil.UMResultModel data = PushUtil.getUMData("ios", body);
        if (data.getStatusCode() != HttpStatus.SC_OK)
            return getErrorResult(data);
        return ResultVM.ok(data.getContent());
    }


    //发送全体广播
    @RequestMapping("sendSingle")
    ResultVM sendBroadcast(@RequestParam String id, @RequestParam String title, @RequestParam String setContent) {
        String body = PushUtil.getBroadCast(title, setContent);
        PushUtil.UMResultModel data = PushUtil.getUMData("ios", body);
        if (data.getStatusCode() != HttpStatus.SC_OK)
            return getErrorResult(data);
        return ResultVM.ok(data.getContent());
    }

    private ResultVM getErrorResult(PushUtil.UMResultModel data) {
        ResultVM errorResult = new ResultVM();
        errorResult.setCode(500);
        errorResult.setResult(data.getContent());
        errorResult.setMsg(String.valueOf(data.getStatusCode()));
        return errorResult;
    }
}