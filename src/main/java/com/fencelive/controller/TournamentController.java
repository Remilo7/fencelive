package com.fencelive.controller;

import com.fencelive.model.entity.Tournament;
import com.fencelive.services.ListService;
import com.fencelive.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TournamentController {

    @Autowired
    TournamentService tournamentService;

    @Autowired
    ListService listService;

    @RequestMapping("/newTournament")
    public String newTournament(Map<String, Object> map){

        Map<Integer,String> lists = new LinkedHashMap<Integer,String>();

        List<com.fencelive.model.entity.List> classLists = listService.getAllLists();

        for (com.fencelive.model.entity.List tempList:classLists) {
            lists.put(tempList.getId(), tempList.getName());
        }

        map.put("tournament",  new Tournament());
        map.put("lists", lists);
        return "newTournament";
    }

    @RequestMapping(value="/addTournament", method= RequestMethod.POST)
    public String doActions(@ModelAttribute Tournament tournament, @RequestParam String action, Map<String, Object> map) {

        Tournament tournamentResult = new Tournament();

        if ("create".equals(action.toLowerCase())) {

            tournamentService.add(new Tournament(tournament.getName(), tournament.getDate(), tournament.getCategory(),
                    tournament.getSex(), tournament.getOutamount(), tournament.getMethod(), tournament.getList()));

        } else if ("edit".equals(action.toLowerCase())) {


        } else if ("delete".equals(action.toLowerCase())) {


        }

        map.put("tournament", tournamentResult);
        return "redirect:/index";
    }
}
