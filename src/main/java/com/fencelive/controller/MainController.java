package com.fencelive.controller;

import java.util.List;
import java.util.Map;

import com.fencelive.model.entity.Category;
import com.fencelive.model.entity.Tournament;
import com.fencelive.services.CategoryService;
import com.fencelive.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @Autowired
    TournamentService tournamentService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String indexPage(Map<String, Object> map) {

        List<Category> categories = categoryService.getAllCategories();

        map.put("tournament", new Tournament());
        map.put("tournamentList", tournamentService.getAllTournaments());
        map.put("categories", categories);
        return "index";
    }
}