package com.overflow.stack.update;

import com.overflow.stack.update.entity.Answer;
import com.overflow.stack.update.entity.Question;
import com.overflow.stack.update.service.persistence.AnswerService;
import com.overflow.stack.update.service.persistence.QuestionService;
import com.overflow.stack.user.service.entity.User;
import com.overflow.stack.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("api/v1/bulk/questions/answers")
public class InsertBulkQuestionsAndAnswers {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void insertBulkQuestionAndAnswer(){
        User user = new User();
        user.setUserId("test");
        user.setEmailId("test.user@test.com");
        user.setPassword("Secrete@123!!");
        user.setDisplayName("Test User");
        userService.save(user);
        for(int i=0; i < 50; i++){
            Question question = questionService.save(Question.builder()
                    .questionTitle("Sample Question " + (i+1))
                    .questionDescription(String.format("Sample question %s description.",i+1))
                    .tags(Arrays.asList("tag1","tag2","tag3"))
                    .postedBy("test")
                    .build());
            Answer answer = answerService.save(question.getQuestionId(), Answer.builder()
                    .answerText(String.format("Sample answer to question %s", (i + 1)))
                    .questionId(question.getQuestionId())
                    .build());
            answerService.save(question.getQuestionId(), Answer.builder()
                    .answerText(String.format("Sample child answer to the answer of question %s", (i + 1)))
                    .questionId(question.getQuestionId())
                    .parentAnswerId(answer.getAnswerId())
                    .build());
        }
    }
}
