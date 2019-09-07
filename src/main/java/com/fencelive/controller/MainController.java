package com.fencelive.controller;

import java.util.Map;

import com.fencelive.model.entity.Tournament;
import com.fencelive.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @Autowired
    TournamentService tournamentService;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String indexPage(Map<String, Object> map) {

        map.put("tournament", new Tournament());
        map.put("tournamentList", tournamentService.getAllTournaments());
        return "index";
    }
}