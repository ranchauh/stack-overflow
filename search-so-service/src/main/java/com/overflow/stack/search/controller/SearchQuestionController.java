package com.overflow.stack.search.controller;

import com.overflow.stack.search.service.SearchAnswerService;
import com.overflow.stack.search.service.SearchQuestionService;
import com.overflow.stack.es.model.Answer;
import com.overflow.stack.es.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping(value = "api/v1/questions")
@RestController
public class SearchQuestionController {

    @Autowired
    private SearchQuestionService searchQuestionService;

    @Autowired
    private SearchAnswerService searchAnswerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Question> getTopQuestions(@RequestParam(value = "query", required = false) String query,
                                          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                          @RequestParam(value = "size", required = false, defaultValue = "100") int size) {
        if(StringUtils.isEmpty(query)) {
            return searchQuestionService.getTopQuestions(page,size);
        }else {
            return searchQuestionService.searchQuestionsByText(query,page,size);
        }
    }

    @GetMapping(value = "/tagged/{tagId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Question> getTaggedQuestions(@PathVariable("tagId") String tagId,
                                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "50") int size){
        return searchQuestionService.searchQuestionsByTag(tagId,page,size);
    }

    @GetMapping(value = "/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Question getQuestion(@PathVariable("questionId") String questionId) {
        return searchQuestionService.getQuestionById(questionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @GetMapping(value = "/{questionId}/answers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Answer> getAnswerByQuestionId(@PathVariable("questionId") String questionId,
                                              @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                              @RequestParam(value = "size", required = false, defaultValue = "50") int size){
        return searchAnswerService.getAnswerByQuestionId(questionId,page, size);
    }

}
