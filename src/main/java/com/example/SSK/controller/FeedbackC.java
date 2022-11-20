package com.example.SSK.controller;

import com.example.SSK.model.FeedBack;
import com.example.SSK.repo.FeedbackR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FeedbackC {

    @Autowired
    FeedbackR feedbackR;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String page() {
        return "index";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public String pageP(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String email
            , @RequestParam(defaultValue = "") String message) {
        FeedBack feedBack = new FeedBack(name, email, message);
        feedbackR.save(feedBack);
        return "index";
    }
}
