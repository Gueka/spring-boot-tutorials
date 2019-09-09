package net.gueka.rules.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.gueka.rules.model.UserInfo;
import net.gueka.rules.service.PromoService;

@RestController
@RequestMapping(value = "/validate")
public class ValidatorController {

    @Autowired
    PromoService service;

    @PostMapping(value = "/promo")
    public List<String> hasPromo(@RequestBody UserInfo info) {
        return service.getMessages(info);
    }

}