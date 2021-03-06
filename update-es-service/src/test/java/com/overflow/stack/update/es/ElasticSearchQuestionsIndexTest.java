package com.overflow.stack.update.es;

import com.overflow.stack.commons.SoCommonsConfiguration;
import com.overflow.stack.commons.model.Question;
import com.overflow.stack.commons.service.EsQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SoCommonsConfiguration.class)
@TestPropertySource(value = "classpath:application.properties")
@Slf4j
public class ElasticSearchQuestionsIndexTest {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private EsQuestionService esQuestionService;

    @Before
    public void before(){
        //elasticsearchOperations.deleteIndex(Question.class);
        //elasticsearchOperations.createIndex(Question.class);

        Question question1 = Question.builder().build();
        question1.setQuestionId("question1");
        question1.setQuestionTitle("What is java?");
        question1.setQuestionDescription("Please tell me what is java");
        question1.setTags(Arrays.asList("java","core-java","programming"));
        question1.setVoteCount(2);
        question1.setCreateTimestamp(System.currentTimeMillis());
        question1.setUpdateTimestamp(System.currentTimeMillis());
        esQuestionService.save(question1);

        Question question = Question.builder().build();
        question.setQuestionId("question2");
        question.setQuestionTitle("What is node.js?");
        question.setQuestionDescription("Please tell me what is node.js");
        question.setTags(Arrays.asList("node.js","node","programming"));
        question.setVoteCount(3);
        question.setCreateTimestamp(System.currentTimeMillis());
        question.setUpdateTimestamp(System.currentTimeMillis());
        esQuestionService.save(question);

    }

    @Test
    @Ignore
    public void deleteIndex(){
        elasticsearchOperations.deleteIndex(Question.class);
    }

    @Test
    public void testFindAllTags(){
        List<? extends Terms.Bucket> tags = esQuestionService.findAllTags();
        log.info("Printing unique tags");
        for (Terms.Bucket bucket : tags) {
            log.info(bucket.getKeyAsString());
        }
    }

    @Test
    public void testFindById(){
        Optional<Question> question = esQuestionService.findById("question1");
        Assert.assertTrue(question.isPresent());
        Assert.assertEquals("question1",question.get().getQuestionId());
    }

    @Test
    public void testFindByText(){
        Page<Question> result = esQuestionService.findByText("what is", PageRequest.of(0, 10));

        Assert.assertEquals(2L,result.getTotalElements());
    }

    @Test
    public void testFindByTags(){
        Page<Question> result = esQuestionService.findByTag("java", PageRequest.of(0, 10));

        Assert.assertEquals(1L,result.getTotalElements());

        Question question = result.getContent().stream()
                .filter(q -> q.getQuestionId().equalsIgnoreCase("question1"))
                .findAny().get();

        esQuestionService.deleteById(question.getQuestionId());

        result = esQuestionService.findByText("what is", PageRequest.of(0, 10));

        Assert.assertEquals(1L,result.getTotalElements());
    }

    @Test
    public void testDeleteById(){
        Page<Question> result = esQuestionService.findByText("what is", PageRequest.of(0, 10));
        Assert.assertEquals(2L,result.getTotalElements());

        Question question = result.getContent().stream()
                .filter(q -> q.getQuestionId().equalsIgnoreCase("question1"))
                .findAny().get();
        esQuestionService.deleteById(question.getQuestionId());

        result = esQuestionService.findByText("what is", PageRequest.of(0, 10));

        Assert.assertEquals(1L,result.getTotalElements());
    }

    @Test
    public void testUpdateQuestion() throws IOException {
        Page<Question> result = esQuestionService.findByText("what is", PageRequest.of(0, 10));
        Assert.assertEquals(2L,result.getTotalElements());

        Question question = result.getContent().stream()
                .filter(q -> q.getQuestionId().equalsIgnoreCase("question1"))
                .findAny().get();
        question.setQuestionTitle("What is java?");
        question.setQuestionDescription("Can anyone tell me what is java?");
        question.setTags(Arrays.asList("java","core-java","jv"));
        question.setVoteCount(3);
        esQuestionService.update(question);

        result = esQuestionService.findByText("what is", PageRequest.of(0, 10, Sort.by("updateTimestamp").descending()));
        Assert.assertEquals(2L,result.getTotalElements());
    }

    @Test
    public void testFindTopQuestions() throws IOException {
        Page<Question> result = esQuestionService.findLatest(PageRequest.of(0, 1, Sort.by("updateTimestamp").descending()));
        Assert.assertEquals(2L,result.getTotalElements());
        Assert.assertEquals(1L,result.getContent().size());
        Question question = result.getContent().stream()
                .findFirst().get();
        Assert.assertEquals("question2",question.getQuestionId());
    }
}
