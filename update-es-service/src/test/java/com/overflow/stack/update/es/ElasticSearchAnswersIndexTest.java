package com.overflow.stack.update.es;

import com.overflow.stack.commons.SoCommonsConfiguration;
import com.overflow.stack.commons.model.Answer;
import com.overflow.stack.commons.service.EsAnswerService;
import org.junit.Assert;
import org.junit.Before;
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
import java.util.Collections;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SoCommonsConfiguration.class)
@TestPropertySource(value = "classpath:application.properties")
public class ElasticSearchAnswersIndexTest {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private EsAnswerService esAnswerService;

    @Before
    public void before(){
        Answer answer = Answer.builder().build();
        answer.setAnswerId("answer1");
        answer.setQuestionId("question1");
        answer.setAnswerText("Java is a dynamic programming language");
        answer.setVoteCount(2);
        answer.setCreateTimestamp(System.currentTimeMillis());
        answer.setUpdateTimestamp(System.currentTimeMillis());
        answer.setPostedBy("user1");
        answer.setImageIds(Collections.singletonList("imageUrl1"));
        answer.setVideoIds(Collections.singletonList("videoUrl1"));
        esAnswerService.save(answer);

        answer = Answer.builder().build();
        answer.setAnswerId("answer2");
        answer.setQuestionId("question2");
        answer.setAnswerText("Node.js is a backend language based on Javascript");
        answer.setVoteCount(2);
        answer.setCreateTimestamp(System.currentTimeMillis());
        answer.setUpdateTimestamp(System.currentTimeMillis());
        answer.setPostedBy("user1");
        answer.setImageIds(Collections.singletonList("imageUrl11"));
        answer.setVideoIds(Collections.singletonList("videoUrl11"));
        answer.setParentAnswerId("answer1");
        esAnswerService.save(answer);
    }

    @Test
    public void testFindById(){
        Optional<Answer> answer = esAnswerService.findById("answer1");
        Assert.assertTrue(answer.isPresent());
        Assert.assertEquals("answer1",answer.get().getAnswerId());
    }

    @Test
    public void testFindByText(){
        Page<Answer> result = esAnswerService.findByText("language", PageRequest.of(0, 10));
        Assert.assertEquals(2L,result.getTotalElements());
    }

    @Test
    public void testDeleteById(){
        Page<Answer> result = esAnswerService.findByText("language", PageRequest.of(0, 10));
        Assert.assertEquals(2L,result.getTotalElements());

        Answer answer = result.getContent().stream()
                .findAny().get();
        esAnswerService.deleteById(answer.getAnswerId());

        result = esAnswerService.findByText("what is", PageRequest.of(0, 10));

        Assert.assertEquals(1L,result.getTotalElements());
    }

    @Test
    public void testUpdateQuestion() throws IOException {
        Page<Answer> result = esAnswerService.findByText("language", PageRequest.of(0, 10));
        Assert.assertEquals(2L,result.getTotalElements());

        Answer answer = result.getContent().stream()
                .findAny().get();
        answer.setAnswerText("Java is a runtime and dynamic programming language");
        answer.setVoteCount(3);
        esAnswerService.update(answer);

        result = esAnswerService.findByText("language", PageRequest.of(0, 10, Sort.by("updateTimestamp").descending()));
        Assert.assertEquals(2L,result.getTotalElements());
    }

    @Test
    public void testFindTopQuestions() throws IOException {
        Page<Answer> result = esAnswerService.findLatest(PageRequest.of(0, 1, Sort.by("updateTimestamp").descending()));
        Assert.assertEquals(2L,result.getTotalElements());
        Assert.assertEquals(1L,result.getContent().size());
        Answer answer = result.getContent().stream()
                .findFirst().get();
        Assert.assertEquals("answer2",answer.getAnswerId());
    }

    @Test
    public void testFindAnswersByParentAnswerId() {
        Page<Answer> result = esAnswerService.findByParentAnswerId("answer1",PageRequest.of(0, 1, Sort.by("updateTimestamp").descending()));
        Assert.assertEquals(1L,result.getTotalElements());
        Assert.assertEquals(1L,result.getContent().size());
        Answer answer = result.getContent().stream()
                .findFirst().get();
        Assert.assertEquals("answer2",answer.getAnswerId());
    }
}
