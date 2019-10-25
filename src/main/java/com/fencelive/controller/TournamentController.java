package com.fencelive.controller;

import com.fencelive.model.entity.*;
import com.fencelive.model.viewModel.FencerViewModel;
import com.fencelive.model.viewModel.GroupFightsForm;
import com.fencelive.model.viewModel.GroupFightsViewModel;
import com.fencelive.services.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

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

    @Autowired
    TournamentGroupsService tournamentGroupsService;

    @Autowired
    GroupFencersService groupFencersService;

    @Autowired
    GroupFightsService groupFightsService;

    public static int tournamentId = 1;

    // tournament JSP page

    @RequestMapping("/tournament")
    public String tournament(Map<String, Object> map){

        Tournament tournament = tournamentService.getTournament(tournamentId);

        // displaying fencers allowed to compete in the tournament

        List<FencerViewModel> fencerList = new ArrayList<>();

        int category = tournamentService.getTournament(tournamentId).getCategory();
        int curr_year = Calendar.getInstance().get(Calendar.YEAR);

        int from = curr_year-categoryService.getCategory(category).getMax_age();
        int to = curr_year-categoryService.getCategory(category).getMin_age();

        List<Fencer> allowedFencerList = fencerService.getCategoryFencers(from, to);
        List<Fencer> competingFencerList = tournamentFencersService.getAllTournamentFencers(tournament);

        for (Fencer fencer:allowedFencerList) {

            FencerViewModel tempf = new FencerViewModel(fencer);

            tempf.setCompeting(false);

            for (Fencer tempcomp:competingFencerList) {
                if (tempcomp.getId() == fencer.getId())
                    tempf.setCompeting(true);
            }

            fencerList.add(tempf);
        }

        // displaying groups

        List<TournamentGroups> allTournamentGroups = tournamentGroupsService.getTournamentGroups(tournament);
        int groupsNum = allTournamentGroups.size();
        boolean generated;

        if (groupsNum>0){

            generated = true;

            List<FencerViewModel> [] groups = prepareGroups(groupsNum, allTournamentGroups);

            GroupFightsForm groupFightsForm = new GroupFightsForm();
            groupFightsForm.setGroupFights(getAllFights(allTournamentGroups));

            map.put("groups", groups);
            map.put("list", new ArrayList());
            map.put("fight", new GroupFightsViewModel());
            map.put("groupFightsForm", groupFightsForm);
        }

        else{
            generated = false;
        }

        map.put("fencer", new FencerViewModel());
        map.put("fencerList", fencerList);
        map.put("tournament", tournament.getName());

        map.put("generated", generated);

        return "tournament";
    }

    // method for tournament JSP page form

    @RequestMapping(value="/tournament.do", method= RequestMethod.POST)
    public String doActions(@ModelAttribute FencerViewModel fencer, @RequestParam String action, Map<String, Object> map) {

        Tournament tournament = tournamentService.getTournament(tournamentId);

        FencerViewModel fencerResult = new FencerViewModel();

        List<Fencer> tournamentFencers = tournamentFencersService.getAllTournamentFencers(tournament);
        List<Integer> fencersToChange = fencer.getIdList();

        if ("cancel".equals(action.toLowerCase())) {

            List<TournamentFencers> tournamentFencersAbove = tournamentFencersService.getAllFencers(tournament);

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
                    tournamentFencersService.add(new TournamentFencers(tournament, fencerService.getFencer(i)));

                test = false;
            }
        }

        map.put("fencer", fencerResult);
        return "redirect:/tournament";
    }

    // newTournament JSP page

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

    // method adding new tournament

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

    // method generating groups after clicking button

    @RequestMapping(value="/generateGroups", method= RequestMethod.POST)
    public String generateGroups(@ModelAttribute FencerViewModel fencer, Map<String, Object> map, HttpSession session){

        Tournament tournament = tournamentService.getTournament(tournamentId);

        List<Fencer> allFencers = tournamentFencersService.getAllTournamentFencers(tournament);

        List<Integer> groupIds = new ArrayList<>();

        if (allFencers.size() < 12){

            tournamentGroupsService.add(tournament, 1, allFencers.size());
        }

        else {

            // creating groups in database

            Map<String, Integer> groupsCard = groupsCard(allFencers.size());

            int groupNum = 1;

            for (int i=0; i<groupsCard.get("groups7"); i++) {
                tournamentGroupsService.add(tournament, groupNum, 7);
                groupIds.add(tournamentGroupsService.getLastIndex());
                groupNum++;
            }

            for (int i=0; i<groupsCard.get("groups6"); i++) {
                tournamentGroupsService.add(tournament, groupNum, 6);
                groupIds.add(tournamentGroupsService.getLastIndex());
                groupNum++;
            }

            // getting lists of divided fencers

            Map<String,List<Fencer>> fencers = divideFencers(allFencers, session);
            List<Fencer> classFerncers = fencers.get("classFencers");
            List<Fencer> otherFencers = fencers.get("otherFencers");

            int numOfGroups = groupsCard.get("groups7") + groupsCard.get("groups6");

            List<Fencer>[] groupsTab = new List [numOfGroups];

            for (int i=0; i<groupsTab.length; i++)
                groupsTab[i] = new ArrayList<>();

            // asigning classified and unclassified fencers to groups

            groupsTab = assignFencers(groupsTab, classFerncers, numOfGroups);
            groupsTab = assignFencers(groupsTab, otherFencers, numOfGroups);

            // saving fencers in the database

            for(int i=0; i<groupsTab.length; i++){

                for (Fencer f:groupsTab[i]) {

                    TournamentGroups tg = tournamentGroupsService.getTournamentGroup(groupIds.get(i));
                    Fencer ff = fencerService.getFencer(f.getId());

                    groupFencersService.add(new GroupFencers(tg,ff));
                }
            }

            List<FencerViewModel> [] groups = prepareGroups(numOfGroups,tournamentGroupsService.getTournamentGroups(tournament));
            createGroupFights(groups);
        }

        return "redirect:/tournament";
    }

    // method for groups form

    @RequestMapping(value="/groupFightsResults", method= RequestMethod.POST)
    public String groupFightsResults(@ModelAttribute GroupFightsForm groupFightsForm){

        List<GroupFightsViewModel[][]> allFights = groupFightsForm.getGroupFights();

        for (int gid=0; gid<allFights.size(); gid++){

            GroupFightsViewModel[][] tempGroup = allFights.get(gid);
            int groupCard = tempGroup.length;

            for (int i=0; i<groupCard; i++){
                for (int j=i+1; j<groupCard; j++){

                    if ((tempGroup[i][j].getScore1() != null) && (!tempGroup[i][j].getScore1().isEmpty())) {

                        GroupFights fight = new GroupFights();

                        fight.setId(tempGroup[i][j].getId());
                        fight.setGroup_id(tempGroup[i][j].getGroup_id());
                        fight.setFencer1_id(tempGroup[i][j].getFencer1_id());
                        fight.setFencer2_id(tempGroup[i][j].getFencer2_id());

                        String score1 = tempGroup[i][j].getScore1();
                        String score2 = tempGroup[j][i].getScore2();

                        int score1Int = Integer.parseInt(score1.substring(1));
                        int score2Int = Integer.parseInt(score2.substring(1));

                        fight.setScore1(score1Int);
                        fight.setScore2(score2Int);

                        GroupFights tempGF = groupFightsService.getGroupFight(fight.getId());
                        fight.setGroup_id(tempGF.getGroup_id());
                        fight.setFencer1_id(tempGF.getFencer1_id());
                        fight.setFencer2_id(tempGF.getFencer2_id());

                        if (score1.charAt(0) == 'V')
                            fight.setWinner_id(fight.getFencer1_id());

                        else if (score2.charAt(0) == 'V')
                            fight.setWinner_id(fight.getFencer2_id());

                        groupFightsService.edit(fight);
                    }
                }
            }
        }

        return "redirect:/tournament";
    }

    // method defining number of 6-person and 7-person groups

    private Map<String, Integer> groupsCard(int x){

        int groups6 = 0;

        while((x%7 != 0) && (x%7 != 6)){
            groups6++;
            x -= 6;
        }

        if (x%7 == 6)
            groups6++;

        int groups7 = x/7;

        Map<String,Integer> map = new HashMap<>();
        map.put("groups6",groups6);
        map.put("groups7",groups7);

        return map;
    }

    // method diving fencers into two groups - one present on the classification list and the other one absent

    private Map<String,List<Fencer>> divideFencers(List<Fencer> allFencers, HttpSession session){

        List<Fencer> classFerncers = new ArrayList<>();
        List<Fencer> otherFencers = new ArrayList<>();
        List<Fencer> allClassFencers = readClassFencers(session);

        Boolean check = false;

        for (Fencer f1:allFencers) {
            for (Fencer f2:allClassFencers) {
                if (f1.equals(f2)){
                    classFerncers.add(f1);
                    check = true;
                }
            }

            if (!check)
                otherFencers.add(f1);

            check = false;
        }

        Map<String,List<Fencer>> map = new HashMap<>();
        map.put("classFencers", classFerncers);
        map.put("otherFencers", otherFencers);

        return map;
    }

    // method reading classified fencers from file and saving them into list

    private List<Fencer> readClassFencers(HttpSession session){

        Tournament tournament = tournamentService.getTournament(tournamentId);

        List<Fencer> classFencers = new ArrayList<>();

        String file_directory ="/resources/files/"+tournament.getList()+".xlsx";

        ServletContext context = session.getServletContext();
        String path = context.getRealPath(file_directory);

        File excelFile = new File(path);

        try {

            FileInputStream fis = new FileInputStream(excelFile);

            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIt = sheet.iterator();

            rowIt.next();

            while(rowIt.hasNext()) {
                Row row = rowIt.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                Fencer tempFencer = new Fencer();

                cellIterator.next();

                tempFencer.setSurname(cellIterator.next().toString());
                tempFencer.setName(cellIterator.next().toString());
                tempFencer.setClub(cellIterator.next().toString());

                classFencers.add(tempFencer);
            }

            workbook.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return classFencers;
    }

    // method assigning fencers to groups

    private List<Fencer>[] assignFencers(List<Fencer>[] groupsTab, List<Fencer> ocFencers, int numOfGroups){

        Iterator fencerIt = ocFencers.iterator();

        int groupId = 0;
        int loop = 1;

        while(fencerIt.hasNext()){

            Fencer fToAdd = (Fencer)fencerIt.next();

            boolean test = true;

            if (loop == 1) {
                for (Fencer f : groupsTab[groupId]) {
                    if (fToAdd.getClub().equalsIgnoreCase(f.getClub()))
                        test = false;
                }
            }

            if (test) {
                groupsTab[groupId].add(fToAdd);
                fencerIt.remove();
            }

            if (groupId+1 == numOfGroups)
                groupId = 0;

            else
                groupId++;

            if ((!fencerIt.hasNext()) && (!ocFencers.isEmpty())) {
                fencerIt = ocFencers.iterator();
                loop++;
            }
        }

        return groupsTab;
    }

    // method preparing groups to display

    private List<FencerViewModel> [] prepareGroups(int groupsNum, List<TournamentGroups> allTournamentGroups){

        // collecting fencers from database and dividing them into groups they belong

        List<FencerViewModel> [] groups = new List[groupsNum];

        for (int i=0; i<groupsNum; i++)
            groups[i] = new ArrayList<>();

        int idg = 0;

        for (TournamentGroups tg:allTournamentGroups) {

            List<Integer> tempIds = groupFencersService.getAllGroupFencers(tg);

            for (Integer id:tempIds) {
                groups[idg].add(new FencerViewModel(fencerService.getFencer(id)));
            }

            idg++;
        }

        return groups;
    }

    // method creating fights in groups

    private void createGroupFights(List<FencerViewModel> [] groups){

        Tournament tournament = tournamentService.getTournament(tournamentId);

        // creating fights in each group and puting them into n x n tab

        for (int gid=0; gid<groups.length; gid++){

            int n = groups[gid].size();
            List<TournamentGroups> groupList = tournamentGroupsService.getTournamentGroups(tournament);

            for (int i=0; i<n; i++){
                for (int j=i+1; j<n; j++){

                    Fencer f1 = fencerService.getFencer(groups[gid].get(i).getId());
                    Fencer f2 = fencerService.getFencer(groups[gid].get(j).getId());

                    groupFightsService.add(new GroupFights(groupList.get(gid),f1,f2));
                }
            }
        }
    }

    // method getting group fights for particular tournament

    private List<GroupFightsViewModel [][]> getAllFights(List<TournamentGroups> allTournamentGroups){

        List<GroupFightsViewModel [][]> allFights = new ArrayList<>();

        for (int gid=0; gid<allTournamentGroups.size(); gid++){

            List<GroupFights> fights = groupFightsService.getAllGroupFights(allTournamentGroups.get(gid));
            int n = groupFencersService.getAllGroupFencers(allTournamentGroups.get(gid)).size();

            GroupFightsViewModel [][] tempFights = new GroupFightsViewModel[n][n];

            int it = 0;

            for (int i=0; i<n; i++){
                for (int j=i+1; j<n; j++){
                    tempFights[i][j] = new GroupFightsViewModel(fights.get(it));
                    tempFights[j][i] = tempFights[i][j];
                    it++;
                }
            }

            allFights.add(tempFights);
        }

        return allFights;
    }
}
