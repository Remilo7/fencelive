package com.fencelive.controller;

import com.fencelive.model.entity.Category;
import com.fencelive.model.entity.Fencer;
import com.fencelive.model.entity.Tournament;
import com.fencelive.model.entity.TournamentFencers;
import com.fencelive.model.viewModel.FencerViewModel;
import com.fencelive.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class TournamentController {

    @Autowired
    TournamentService tournamentService;

    @Autowired
    ListService listService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    FencerService fencerService;

    @Autowired
    TournamentFencersService tournamentFencersService;

    public static int tournamentId = 1;

    @RequestMapping("/tournament")
    public String tournament(Map<String, Object> map){

        List<FencerViewModel> fencerList = new ArrayList<>();

        int category = tournamentService.getTournament(tournamentId).getCategory();
        int curr_year = Calendar.getInstance().get(Calendar.YEAR);

        int from = curr_year-categoryService.getCategory(category).getMax_age();
        int to = curr_year-categoryService.getCategory(category).getMin_age();

        List<Fencer> allowedFencerList = fencerService.getCategoryFencers(from, to);
        List<Fencer> competingFencerList = tournamentFencersService.getAllTournamentFencers(tournamentService.getTournament(tournamentId));

        for (Fencer fencer:allowedFencerList) {

            FencerViewModel tempf = new FencerViewModel(fencer.getId(), fencer.getSurname()+" "+fencer.getName(), fencer.getClub(),
                    fencer.getCountry(), fencer.getYear());

            tempf.setCompeting(false);

            for (Fencer tempcomp:competingFencerList) {
                if (tempcomp.getId() == fencer.getId())
                    tempf.setCompeting(true);
            }

            fencerList.add(tempf);
        }

        map.put("fencer", new FencerViewModel());
        map.put("fencerList", fencerList);
        map.put("tournament", tournamentService.getTournament(tournamentId).getName());
        return "tournament";
    }

    @RequestMapping(value="/tournament.do", method= RequestMethod.POST)
    public String doActions(@ModelAttribute FencerViewModel fencer, @RequestParam String action, Map<String, Object> map, HttpSession session) {

        FencerViewModel fencerResult = new FencerViewModel();

        List<Fencer> tournamentFencers = tournamentFencersService.getAllTournamentFencers(tournamentService.getTournament(tournamentId));
        List<Integer> fencersToChange = fencer.getIdList();

        if ("cancel".equals(action.toLowerCase())) {

            List<TournamentFencers> tournamentFencersAbove = tournamentFencersService.getAllFencers(tournamentService.getTournament(tournamentId));

            for (Integer i : fencersToChange) {
                for (Fencer temp_fencer:tournamentFencers) {
                    if (i == temp_fencer.getId()) {

                        for (TournamentFencers tourfenc : tournamentFencersAbove) {
                            if (i == tourfenc.getFencer_id().getId())
                                tournamentFencersService.delete(tourfenc.getId());
                        }
                    }
                }
            }

        } else if ("confirm".equals(action.toLowerCase())) {

            boolean test = false;

            for (Integer i : fencersToChange) {
                for (Fencer temp_fencer:tournamentFencers) {
                    if (i == temp_fencer.getId())
                        test = true;
                }

                if (!test)
                    tournamentFencersService.add(new TournamentFencers(tournamentService.getTournament(tournamentId), fencerService.getFencer(i)));

                test = false;
            }
        }

        map.put("fencer", fencerResult);
        return "redirect:/tournament";
    }

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
