package com.liketry.web;

import com.liketry.domain.Censor;
import com.liketry.service.CensorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("censor")
public class CensorController extends BaseController<CensorService, Censor> {
}