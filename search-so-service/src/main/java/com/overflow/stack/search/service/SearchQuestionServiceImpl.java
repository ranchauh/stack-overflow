package com.overflow.stack.search.service;

import com.overflow.stack.es.model.Question;
import com.overflow.stack.es.service.QuestionService;
import com.overflow.stack.search.model.SearchTagResult;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearchQuestionServiceImpl implements SearchQuestionService {

    @Autowired
    private QuestionService questionService;

    public Page<Question> getTopQuestions(int pageNumber, int size){
        return questionService.findLatest(PageRequest.of(pageNumber, size,
                Sort.by("updateTimestamp").descending()));
    }

    public Page<Question> searchQuestionsByText(String searchText, int pageNumber, int size){
        return questionService.findByText(searchText,PageRequest.of(pageNumber, size));
    }

    public Page<Question> searchQuestionsByTag(String tag, int pageNumber, int size) {
        return questionService.findByTag(tag, PageRequest.of(pageNumber, size));
    }

    public Optional<Question> getQuestionById(String questionId){
        return questionService.findById(questionId);
    }

    public SearchTagResult searchTag(){
        List<String> tags = questionService.findAllTags().stream()
                .map(MultiBucketsAggregation.Bucket::getKeyAsString)
                .collect(Collectors.toList());
        return SearchTagResult.builder()
                .tags(tags)
                .build();
    }

    public SearchTagResult searchTag(String query){
        List<String> tags = questionService.findAllTags().stream()
                .map(MultiBucketsAggregation.Bucket::getKeyAsString)
                .filter(t -> match(t,"*"+query+"*"))
                .collect(Collectors.toList());
        return SearchTagResult.builder()
                .tags(tags)
                .build();
    }

    private boolean match(String text, String pattern) {
        return text.matches(pattern.replace("?", ".?").replace("*", ".*?"));
    }


}
