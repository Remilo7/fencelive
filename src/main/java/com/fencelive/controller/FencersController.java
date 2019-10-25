package com.fencelive.controller;

import com.fencelive.model.entity.Fencer;
import com.fencelive.model.viewModel.FencerViewModel;
import com.fencelive.services.FencerService;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class FencersController {

    @Autowired
    FencerService fencerService;

    private static final String UPLOAD_DIRECTORY ="/resources/files";

    // method opening fencers JSP page

    @RequestMapping(value = { "/fencers" }, method = RequestMethod.GET)
    public String fencerPage(Map<String, Object> map) {

        List<FencerViewModel> fencerList = new ArrayList<>();
        List<Fencer> fencers = fencerService.getAllFencers();

        for (Fencer fencer:fencers) {
            fencerList.add(new FencerViewModel(fencer));
        }

        map.put("fencer", new FencerViewModel());
        map.put("fencerList", fencerList);
        return "fencers";
    }

    // method redirecting to the newFencer JSP page

    @RequestMapping(value = { "/newFencer" }, method = RequestMethod.GET)
    public String newFencer(Map<String, Object> map) {

        map.put("fencer", new Fencer());
        return "newFencer";
    }

    // method for fencers JSP page form

    @RequestMapping(value="/fencers.do", method= RequestMethod.POST)
    public String doActions(@ModelAttribute FencerViewModel fencer, @RequestParam String action, Map<String, Object> map,
                            HttpSession session, @RequestParam(required = false) CommonsMultipartFile file) throws IOException {

        FencerViewModel fencerResult = new FencerViewModel();

        if ("add".equals(action.toLowerCase())) {

            return "redirect:/newFencer";

        } else if ("import".equals(action.toLowerCase())) {

            fileUpload(file, session);

            String file_directory ="/resources/files/fencers.xlsx";

            ServletContext context = session.getServletContext();
            String path = context.getRealPath(file_directory);

            File excelFile = new File(path);
            FileInputStream fis = new FileInputStream(excelFile);

            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIt = sheet.iterator();

            rowIt.next();

            boolean flag = true;

            while(rowIt.hasNext() && flag) {
                Row row = rowIt.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                Fencer tempFencer = new Fencer();

                tempFencer.setSurname(cellIterator.next().toString());
                tempFencer.setName(cellIterator.next().toString());
                double tempYear = ParseDouble(cellIterator.next().toString());

                if ((tempYear != -1) && (tempYear != 0)) {

                    tempFencer.setYear((int) tempYear);
                    tempFencer.setSex(cellIterator.next().toString().charAt(0));
                    tempFencer.setClub(cellIterator.next().toString());
                    tempFencer.setCountry(cellIterator.next().toString());

                    List<Fencer> tempEqualFencers = fencerService.getEqualFencer(tempFencer.getName(), tempFencer.getSurname(), tempFencer.getYear());

                    if (!tempEqualFencers.isEmpty())
                        fencerService.delete(tempEqualFencers.get(0).getId());

                    fencerService.add(tempFencer);
                }

                else
                    flag = false;
            }

            workbook.close();
            fis.close();

            File temp_file = new File(path);
            temp_file.delete();

        } else if ("delete".equals(action.toLowerCase())) {

            List<Integer> fencersToDeleteList = fencer.getIdList();

            for (Integer i : fencersToDeleteList) {
                fencerService.delete(i);
            }

        } else if ("back".equals(action.toLowerCase())) {

            return "redirect:/index";
        }

        map.put("fencer", fencerResult);
        return "redirect:/fencers";
    }

    // method adding new fencers to the database

    @RequestMapping(value="/addFencer", method= RequestMethod.POST)
    public String addFencer(@ModelAttribute Fencer fencer, @RequestParam String action, Map<String, Object> map) {

        Fencer fencerResult = new Fencer();

        if ("add".equals(action.toLowerCase())) {

            fencerService.add(new Fencer(fencer.getName(), fencer.getSurname().toUpperCase(), fencer.getYear(),
                    fencer.getSex(), fencer.getClub().toUpperCase(), fencer.getCountry().toUpperCase()));
        }

        map.put("fencer", fencerResult);
        return "redirect:/fencers";
    }

    // method for uploading excel files

    private void fileUpload(CommonsMultipartFile file, HttpSession session) throws IOException {

        ServletContext context = session.getServletContext();
        String path = context.getRealPath(UPLOAD_DIRECTORY);
        String filename = "fencers.xlsx";

        byte[] bytes = file.getBytes();
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                new File(path + File.separator + filename)));
        stream.write(bytes);
        stream.flush();
        stream.close();
    }

    // method parsing double from string

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }
}
