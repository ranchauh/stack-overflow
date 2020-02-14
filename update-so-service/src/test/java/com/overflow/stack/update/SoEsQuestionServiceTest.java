package com.overflow.stack.update;

import com.overflow.stack.update.UpdateStackOverflowApplication;
import com.overflow.stack.update.entity.Question;
import com.overflow.stack.update.service.persistence.QuestionService;
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

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(value = "classpath:application.properties")
@ContextConfiguration(classes = UpdateStackOverflowApplication.class)
@Slf4j
public class SoEsQuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Before
    public void before() {
        Question question = questionService.save(Question.builder()
                .questionId(123L)
                .questionTitle("Question-1")
                .questionDescription("Question one description")
                .postedBy("test")
                .voteCount(0L)
                .build());
        Assert.assertNotNull(question);
        Assert.assertNotNull(question.getQuestionId());
    }

    @After
    public void after(){
        questionService.delete(123L);
    }

    @Test
    public void testUpdateAndFind(){
        Optional<Question> questionOptional = questionService.find(123L);
        Assert.assertTrue(questionOptional.isPresent());
        Question question = questionOptional.get();
        //Assert the findings
        Assert.assertEquals("Question-1",question.getQuestionTitle());
        Assert.assertEquals("Question one description",question.getQuestionDescription());
        Assert.assertNotEquals(0L,question.getCreateTimestamp());
        Assert.assertEquals(0L,question.getUpdateTimestamp());
        Assert.assertEquals("test",question.getPostedBy());
        Assert.assertEquals(Long.valueOf(0),question.getVoteCount());

        //Update the question
        question.setQuestionTitle("Updated-Question-1");
        question.setQuestionDescription("Updated-Question-1-Description");
        question.setVoteCount(1L);
        questionService.update(question.getQuestionId(),question);

        //Find the updates
        questionOptional = questionService.find(123L);

        //Assert the updates
        Assert.assertTrue(questionOptional.isPresent());
        question = questionOptional.get();
        Assert.assertEquals("Updated-Question-1",question.getQuestionTitle());
        Assert.assertEquals("Updated-Question-1-Description",question.getQuestionDescription());
        Assert.assertNotEquals(0L,question.getCreateTimestamp());
        Assert.assertNotEquals(0L,question.getUpdateTimestamp());
        Assert.assertEquals("test",question.getPostedBy());
        Assert.assertEquals(Long.valueOf(0),question.getVoteCount());
    }

    @Test
    public void testDelete(){
        questionService.delete(123L);
        //Try finding the question
        Assert.assertFalse(questionService.find(123L).isPresent());
    }
}
