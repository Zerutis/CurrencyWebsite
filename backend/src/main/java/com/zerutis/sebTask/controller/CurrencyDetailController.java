package com.zerutis.sebTask.controller;

import com.zerutis.sebTask.model.CurrencyDetail;
import com.zerutis.sebTask.service.CurrencyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CurrencyDetailController {

    @Autowired
    CurrencyDetailService detailService;

    @GetMapping("/currency/{code}/{dtFrom}/{dtTo}")
    public List<CurrencyDetail> getCurrencyDetail(
            @PathVariable String code,
            @PathVariable String dtFrom,
            @PathVariable String dtTo
    ){
        return detailService.getCurrencyDetail(code, dtFrom, dtTo);
    }
}
