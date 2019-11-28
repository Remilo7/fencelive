package com.fencelive.controller;

import com.fencelive.comparator.ClassFencerComparator;
import com.fencelive.model.entity.*;
import com.fencelive.model.viewModel.*;
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

    @Autowired
    TournamentGroupClassListService tournamentGroupClassListService;

    @Autowired
    TournamentTableauFightsService tournamentTableauFightsService;

    @Autowired
    TournamentFinalClassListService tournamentFinalClassListService;

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
        boolean groupsFinished = false;

        if (groupsNum>0){

            generated = true;

            List<FencerViewModel> [] groups = prepareGroups(groupsNum, allTournamentGroups);

            GroupFightsForm groupFightsForm = new GroupFightsForm();
            groupFightsForm.setGroupFights(getAllFights(allTournamentGroups));

            groupsFinished = checkGroupFights(groupFightsForm.getGroupFights());

            map.put("groups", groups);
            map.put("list", new ArrayList());
            map.put("fight", new GroupFightsViewModel());
            map.put("groupFightsForm", groupFightsForm);
        }

        else{
            generated = false;
        }

        // generating ranking after groups

        List<TournamentGroupClassList> groupClassification = tournamentGroupClassListService.getClassList(tournament);

        if (groupsFinished && groupClassification.isEmpty()){

            List<ClassFencerViewModel> fencersAfterGroups = new ArrayList<>();

            // assigning indicators for each fencer

            for (Fencer fencer:competingFencerList) {

                ClassFencerViewModel classFencer = new ClassFencerViewModel(fencer);
                List<GroupFights> fencerFights = groupFightsService.getAllFencerGroupFights(fencer, allTournamentGroups);

                classFencer.setInd1(calcInd1(fencer, fencerFights));
                classFencer.setInd2(calcInd23(fencer, fencerFights)[0]);
                classFencer.setInd3(calcInd23(fencer, fencerFights)[1]);

                fencersAfterGroups.add(classFencer);
            }

            // sorting fencers over ind1, ind2, ind3

            Collections.sort(fencersAfterGroups, new ClassFencerComparator().reversed());

            // classifying fencers

            int classified = competingFencerList.size()-(competingFencerList.size()*tournament.getOutamount()/100);

            for (int i=0; i<fencersAfterGroups.size(); i++) {

                if (i <= classified)
                    fencersAfterGroups.get(i).setClassified(true);

                else
                    fencersAfterGroups.get(i).setClassified(false);
            }

            // adding after-group classification to the database

            for (ClassFencerViewModel fencer:fencersAfterGroups) {

                TournamentGroupClassList tcl = new TournamentGroupClassList(tournament,fencerService.getFencer(fencer.getId()),fencer.getInd1(),
                        fencer.getInd2(),fencer.getInd3(),fencer.isClassified());

                tournamentGroupClassListService.add(tcl);
            }

            groupClassification = tournamentGroupClassListService.getClassList(tournament);
        }

        // tableau fights

        List<TournamentTableauFights> tableauFights = tournamentTableauFightsService.getAllTournamentTableFights(tournament, 256);

        if (groupsFinished) {

            List<TournamentFinalClassList> finalClassification = tournamentFinalClassListService.getFinalClassList(tournament);

            // straight after groups - there is no table

            if (finalClassification.isEmpty()) {

                createInitialFinalClassList(groupClassification);
                finalClassification = tournamentFinalClassListService.getFinalClassList(tournament);

                // finding proper first tableau

                createTable(tournament, finalClassification);
            }

            // getting further

            int tabSize = defineTableauSize(finalClassification.size());

            int minTab = tournamentTableauFightsService.getMinTable(tournament);

            if ((minTab != 0) && (minTab < tabSize))
                tabSize = minTab;

            if (tabSize != 1){

                tableauFights = tournamentTableauFightsService.getAllTournamentTableFights(tournament,tabSize);

                boolean check = true;

                for (TournamentTableauFights fight:tableauFights) {
                    if (fight.getWinner_id() == null)
                        check = false;
                }

                // all fights are finished in particular tableau

                if (check) {

                    tabSize /= 2;

                    // assigning places in classification again

                    reassignPlaces(tableauFights, finalClassification);

                    // getting classification cut to the tableau

                    List<TournamentFinalClassList> tempClassification = tournamentFinalClassListService.getTopClassList(tournament, tabSize);

                    // generating next fights

                    createTable(tournament, tempClassification);

                    // loading new tableau Fights

                    tableauFights = tournamentTableauFightsService.getAllTournamentTableFights(tournament, tabSize);
                }

                // tableau not finished

                else {

                    tableauFights = tournamentTableauFightsService.getAllTournamentTableFights(tournament, tabSize);
                }
            }

        }

        map.put("fencer", new FencerViewModel());
        map.put("fencerList", fencerList);
        map.put("tournament", tournament.getName());

        map.put("generated", generated);
        map.put("groupsFinished", groupsFinished);

        map.put("groupClassList", new TournamentGroupClassList());
        map.put("groupClassification", groupClassification);

        map.put("tableauFightsForm", new TableauFightsForm(getTransformedTableauFights(tableauFights)));

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

    // method for group fights form

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

    // method for tableau fights form

    @RequestMapping(value="/tableauFightsResults", method= RequestMethod.POST)
    public String tableauFightsResults(@ModelAttribute TableauFightsForm tableauFightsForm){

        List<GroupFightsViewModel> tableauFights = tableauFightsForm.getTableauFights();

        for (GroupFightsViewModel fight:tableauFights) {

            if ((fight.getScore1() != null) && (!fight.getScore1().isEmpty())) {

                TournamentTableauFights fightDB = tournamentTableauFightsService.getTournamentTableFight(fight.getId());

                TournamentTableauFights tempFight = new TournamentTableauFights();

                tempFight.setId(fightDB.getId());
                tempFight.setTableau(fightDB.getTableau());
                tempFight.setTournament_id(fightDB.getTournament_id());
                tempFight.setFencer1_id(fightDB.getFencer1_id());
                tempFight.setFencer2_id(fightDB.getFencer2_id());

                String score1 = fight.getScore1();
                String score2 = fight.getScore2();

                int score1Int = Integer.parseInt(score1.substring(1));
                int score2Int = Integer.parseInt(score2.substring(1));

                tempFight.setScore1(score1Int);
                tempFight.setScore2(score2Int);

                if (score1.charAt(0) == 'V')
                    tempFight.setWinner_id(fightDB.getFencer1_id());

                else if (score2.charAt(0) == 'V')
                    tempFight.setWinner_id(fightDB.getFencer2_id());

                tournamentTableauFightsService.edit(tempFight);
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

    // method getting tableau fights for particular tournament

    private List<GroupFightsViewModel> getTransformedTableauFights(List<TournamentTableauFights> tableauFights){

        List<GroupFightsViewModel> fights = new ArrayList<>();

        for (TournamentTableauFights fight:tableauFights) {

            GroupFightsViewModel tempFight = new GroupFightsViewModel(fight);

            fights.add(tempFight);
        }

        return fights;
    }

    // method checking if all group fights are done

    private boolean checkGroupFights (List<GroupFightsViewModel [][]> allGroups){

        for (GroupFightsViewModel [][] group:allGroups) {
            for (int i=0; i<group.length; i++){
                for (int j=i+1; j<group.length; j++){
                    if ((group[i][j].getScore1() == null) || (group[i][j].getScore1().isEmpty())){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // method calculating fencer's V/M indicator

    private double calcInd1(Fencer fencer, List<GroupFights> fencerFights) {

        double v = 0;
        double m = fencerFights.size();

        for (GroupFights fight:fencerFights) {
            if (fight.getWinner_id().getId() == fencer.getId())
                v++;
        }

        return v/m;
    }

    // method calculating fencer's TD-TR and TD indicators

    private int [] calcInd23(Fencer fencer, List<GroupFights> fencerFights) {

        int [] res = new int[2];

        int td = 0;
        int tr = 0;

        for (GroupFights fight:fencerFights) {

            if (fight.getFencer1_id().getId() == fencer.getId()){
                if (fight.getWinner_id().getId() == fencer.getId()){
                    td += fight.getScore1();
                    tr += fight.getScore2();
                }

                else {
                    td += fight.getScore2();
                    tr += fight.getScore1();
                }
            }

            else {
                if (fight.getWinner_id().getId() == fencer.getId()){
                    td += fight.getScore2();
                    tr += fight.getScore1();
                }

                else {
                    td += fight.getScore1();
                    tr += fight.getScore2();
                }
            }
        }

        res[0] = td-tr;
        res[1] = td;

        return res;
    }

    // method creating initial final classification list

    private void createInitialFinalClassList(List<TournamentGroupClassList> groupClassification) {

        List<TournamentFinalClassList> finalClassification = new ArrayList<>();

        for (int i=0; i<groupClassification.size(); i++) {

            TournamentGroupClassList position = groupClassification.get(i);

            if (position.isClassified()) {
                TournamentFinalClassList newPosition = new TournamentFinalClassList(position.getTournament_id(), position.getFencer_id(), i + 1);
                tournamentFinalClassListService.add(newPosition);
            }

        }
    }

    // method creating fights for tableau

    private void createTable(Tournament tournament, List<TournamentFinalClassList> classification) {

        // defining the proper tableau

        int n = defineTableauSize(classification.size());

        // creating fights in tableau and assigning fencers to them

        for (int i=0; i<n/2; i++){

            int f1 = i+1;
            int f2 = (n-f1)+1;

            Fencer fencer1 = null;
            Fencer fencer2 = null;

            for (TournamentFinalClassList place:classification) {

                if (place.getPlace() == f1)
                    fencer1 = place.getFencer_id();

                if (place.getPlace() == f2)
                    fencer2 = place.getFencer_id();
            }

            TournamentTableauFights fight = new TournamentTableauFights();

            fight.setTournament_id(tournament);
            fight.setTableau(n);

            if (fencer1 == null){
                fight.setFencer2_id(fencer2);
                fight.setWinner_id(fencer2);
            }

            else if (fencer2 == null){
                fight.setFencer1_id(fencer1);
                fight.setWinner_id(fencer1);
            }

            else {
                fight.setFencer1_id(fencer1);
                fight.setFencer2_id(fencer2);
            }

            // inserting fight to the database

            tournamentTableauFightsService.add(fight);
        }
    }

    // method defining tableau size

    private int defineTableauSize(int size){

        int n = 0;

        if (size>64 && size<=128)
            n = 128;

        else if (size>32 && size<=64)
            n = 64;

        else if (size>16 && size<=32)
            n = 32;

        else if (size>8 && size<=16)
            n = 16;

        else if (size>4 && size<=8)
            n = 8;

        else if (size>2 && size<=4)
            n = 4;

        else if (size>1 && size<=2)
            n = 2;

        return n;
    }

    // method assigning places in classification after finishing particular tableau fights

    private void reassignPlaces(List<TournamentTableauFights> fights, List<TournamentFinalClassList> classification) {

        for (TournamentTableauFights fight:fights) {

            Fencer f1 = null;
            Fencer f2 = null;

            TournamentFinalClassList place1 = null;
            TournamentFinalClassList place2 = null;

            for (TournamentFinalClassList place:classification) {

                if (fight.getWinner_id().getId() == place.getFencer_id().getId()){
                    f1 = place.getFencer_id();
                    place1 = place;
                }

                if ((fight.getFencer1_id().getId() != fight.getWinner_id().getId()) && (fight.getFencer1_id().getId() == place.getFencer_id().getId())){
                    f2 = place.getFencer_id();
                    place2 = place;
                }

                if (fight.getFencer2_id() != null) {

                    if ((fight.getFencer2_id().getId() != fight.getWinner_id().getId()) && (fight.getFencer2_id().getId() == place.getFencer_id().getId())) {
                        f2 = place.getFencer_id();
                        place2 = place;
                    }
                }
            }

            if (place2 != null) {

                if (place1.getPlace() > place2.getPlace()) {

                    int place1_int = place1.getPlace();
                    int place2_int = place2.getPlace();

                    place1.setPlace(place2_int);
                    place2.setPlace(place1_int);

                    tournamentFinalClassListService.edit(place1);
                    tournamentFinalClassListService.edit(place2);
                }
            }
        }
    }
}
