package com.fencelive.controller;

import com.fencelive.model.entity.List;
import com.fencelive.services.ListService;
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
import java.util.Map;

@Controller
public class ListsController {

    @Autowired
    ListService listService;

    private static final String UPLOAD_DIRECTORY ="/resources/files";

    @RequestMapping(value = { "/lists" }, method = RequestMethod.GET)
    public String listsPage(Map<String, Object> map) {

        map.put("list", new com.fencelive.model.entity.List());
        map.put("listList", listService.getAllLists());
        return "lists";
    }

    @RequestMapping(value="/lists.do", method= RequestMethod.POST)
    public String doActions(@ModelAttribute List list, @RequestParam String action, Map<String, Object> map,
                            HttpSession session, @RequestParam(required = false) CommonsMultipartFile file) throws IOException {

        List listResult = new List();

        if ("add".equals(action.toLowerCase())) {

            listService.add(list);
            int id = ((List)listService.getAllLists().get(0)).getId();
            fileUpload(file, session, id);

        } else if ("delete".equals(action.toLowerCase())) {

            ServletContext context = session.getServletContext();
            String file_directory ="/resources/files/"+list.getId()+".xlsx";
            String path = context.getRealPath(file_directory);

            File temp_file = new File(path);
            temp_file.delete();

            listService.delete(list.getId());

        } else if ("back".equals(action.toLowerCase())) {

            return "redirect:/index";
        }

        map.put("list", listResult);
        return "redirect:/lists";
    }

    private void fileUpload(CommonsMultipartFile file, HttpSession session, int id) throws IOException {

        ServletContext context = session.getServletContext();
        String path = context.getRealPath(UPLOAD_DIRECTORY);
        String filename = id+".xlsx";

        byte[] bytes = file.getBytes();
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                new File(path + File.separator + filename)));
        stream.write(bytes);
        stream.flush();
        stream.close();
    }
}
