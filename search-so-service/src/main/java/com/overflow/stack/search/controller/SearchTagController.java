package com.overflow.stack.search.controller;

import com.overflow.stack.search.model.SearchTagResult;
import com.overflow.stack.search.service.SearchQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "api/v1/tags")
@RestController
public class SearchTagController {

    @Autowired
    private SearchQuestionService searchQuestionService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SearchTagResult searchTags(@RequestParam(value = "query", required = false) String query){
        if(StringUtils.isEmpty(query)){
            return searchQuestionService.searchTag();
        }else {
            return searchQuestionService.searchTag(query);
        }
    }

}
