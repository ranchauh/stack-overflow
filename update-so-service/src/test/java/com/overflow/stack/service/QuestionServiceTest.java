package com.overflow.stack.service;

import com.overflow.stack.es.model.Question;
import com.overflow.stack.update.UpdateStackOverflowApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(value = "classpath:application.properties")
@ContextConfiguration(classes = UpdateStackOverflowApplication.class)
@Slf4j
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Before
    public void before() {
        Question question = questionService.save(Question.builder()
                .questionId("question1")
                .questionTitle("Question-1")
                .questionDescription("Question one description")
                .createTimestamp(System.currentTimeMillis())
                .postedBy("test")
                .tags(Arrays.asList("q1Tag1","q1Tag2","q1Tag3"))
                .voteCount(0)
                .build());
        Assert.assertNotNull(question);
        Assert.assertNotNull(question.getQuestionId());
    }

    @After
    public void after(){
        questionService.delete("question1");
    }

    @Test
    public void testUpdateAndFind(){
        Optional<Question> questionOptional = questionService.find("question1");
        Assert.assertTrue(questionOptional.isPresent());
        Question question = questionOptional.get();
        //Assert the findings
        Assert.assertEquals("Question-1",question.getQuestionTitle());
        Assert.assertEquals("Question one description",question.getQuestionDescription());
        Assert.assertNotEquals(0L,question.getCreateTimestamp());
        Assert.assertEquals(0L,question.getUpdateTimestamp());
        Assert.assertEquals("test",question.getPostedBy());
        Assert.assertNotNull(question.getTags());
        Assert.assertTrue(question.getTags().containsAll(Arrays.asList("q1Tag1","q1Tag2","q1Tag3")));
        Assert.assertEquals(0L,question.getVoteCount());

        //Update the question
        question.setQuestionTitle("Updated-Question-1");
        question.setQuestionDescription("Updated-Question-1-Description");
        question.setTags(Arrays.asList("q1Tag1-update","q1Tag2-update","q1Tag3-update"));
        question.setVoteCount(1L);
        question.setUpdateTimestamp(System.currentTimeMillis());
        questionService.update(question);

        //Find the updates
        questionOptional = questionService.find("question1");

        //Assert the updates
        Assert.assertTrue(questionOptional.isPresent());
        question = questionOptional.get();
        Assert.assertEquals("Updated-Question-1",question.getQuestionTitle());
        Assert.assertEquals("Updated-Question-1-Description",question.getQuestionDescription());
        Assert.assertNotEquals(0L,question.getCreateTimestamp());
        Assert.assertNotEquals(0L,question.getUpdateTimestamp());
        Assert.assertEquals("test",question.getPostedBy());
        Assert.assertNotNull(question.getTags());
        Assert.assertTrue(question.getTags().containsAll(Arrays.asList("q1Tag1-update","q1Tag2-update","q1Tag3-update")));
        Assert.assertEquals(1L,question.getVoteCount());
    }

    @Test
    public void testDelete(){
        questionService.delete("question1");
        //Try finding the question
        Assert.assertFalse(questionService.find("question1").isPresent());
    }
}
