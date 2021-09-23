package com.example.task_2_1_2.service;

import com.example.task_2_1_2.entity.Answer;
import com.example.task_2_1_2.entity.Question;
import com.example.task_2_1_2.entity.User;
import com.example.task_2_1_2.message.ResponseApi;
import com.example.task_2_1_2.payload.AnswerDto;
import com.example.task_2_1_2.repository.AnswerRepository;
import com.example.task_2_1_2.repository.QuestionRepository;
import com.example.task_2_1_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Creating new answer
     * @param answerDto - solution, question id
     * @return ResponseApi - result message and success bit while creating
     */
    public ResponseApi create(AnswerDto answerDto) {
        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        if (optionalUser.isEmpty())
            return new ResponseApi("User is not found", false);
        Optional<Question> optionalQuestion = questionRepository.findById(answerDto.getQuestionId());
        if (optionalQuestion.isEmpty())
            return new ResponseApi("Question is not found", false);
        if (answerRepository.existsBySolutionAndQuestion_IdAndUser_Id(answerDto.getSolution(), answerDto.getQuestionId(), answerDto.getUserId()))
            return new ResponseApi("This question is already answered by you", false);
        Answer answer = new Answer(null, answerDto.getSolution(), optionalQuestion.get(), optionalUser.get());
        answerRepository.save(answer);
        return new ResponseApi("Answer is created", true);
    }

    /**
     * Reading all answers
     * @return List<Answer>
     */
    public List<Answer> getAll() {
        return answerRepository.findAll();
    }

    /**
     * Reading answer by id
     * @param id - answer id
     * @return ResponseApi - result message, success bit, Answer object while reading
     */
    public ResponseApi getById(Integer id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isEmpty())
            return new ResponseApi("Answer not found", false, null);
        return new ResponseApi("Answer is found", true, optionalAnswer.get());
    }

    /**
     * Updatind answer by id
     * @param id - answer id
     * @param answerDto - solution, question id
     * @return ResponseApi - result message and success bit while updating
     */
    public ResponseApi update(Integer id, AnswerDto answerDto) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isEmpty())
            return new ResponseApi("Answer not found", false);
        Optional<Question> optionalQuestion = questionRepository.findById(answerDto.getQuestionId());
        if (optionalQuestion.isEmpty())
            return new ResponseApi("Question is not found", false);
        if (answerRepository.existsBySolutionAndQuestion_Id(answerDto.getSolution(), answerDto.getQuestionId()))
            return new ResponseApi("This question is already answered", false);
        Answer answer = optionalAnswer.get();
        answer.setSolution(answerDto.getSolution());
        answer.setQuestion(optionalQuestion.get());
        answerRepository.save(answer);
        return new ResponseApi("Answer is updated", true);
    }

    /**
     *
     * @param id - answer id
     * @return ResponseApi - result message and success bit while deleting
     */
    public ResponseApi deleteById(Integer id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isEmpty())
            return new ResponseApi("Answer not found", false);
        answerRepository.delete(optionalAnswer.get());
        return new ResponseApi("Answer is deleted", true);
    }
}
