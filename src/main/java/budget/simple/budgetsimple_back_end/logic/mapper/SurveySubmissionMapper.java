package budget.simple.budgetsimple_back_end.logic.mapper;

import budget.simple.budgetsimple_back_end.logic.survey.*;
import budget.simple.budgetsimple_back_end.model.surveyDTOs.SurveyAnswerDTO;
import budget.simple.budgetsimple_back_end.model.surveyDTOs.SurveySubmissionDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SurveySubmissionMapper {
    public SurveySubmissionMapper() {
    }
    public SurveySubmission mapToModel(SurveySubmissionDTO surveySubmissionDTO, Survey survey) {
        List<SurveyAnswer> answers = new ArrayList<>();
        for (SurveyQuestion question : survey.getQuestions()
        ) {
            for (SurveyAnswerDTO answerDTO : surveySubmissionDTO.getSurveyAnswers()
            ) {
                if(answerDTO.getQuestion().equals(question.getQuestion())){
                answers.add(new SurveyAnswer(answerDTO.getAnswer(), question));
            }
        }}
        return new SurveySubmission(surveySubmissionDTO.getRating(), survey, answers);
    }
    public SurveySubmissionDTO mapToDTO(SurveySubmission surveySubmission) {
        SurveySubmissionDTO surveySubmissionDTO = new SurveySubmissionDTO();
        surveySubmissionDTO.setRating(surveySubmission.getRating());
        surveySubmissionDTO.setSurveyDTO(new SurveyMapper().mapToDTO(surveySubmission.getSurvey()));
        List<SurveyAnswerDTO> answers = new ArrayList<>();
        for (SurveyAnswer answer : surveySubmission.getSurveyAnswers()
        ) {
            SurveyAnswerDTO surveyAnswerDTO = new SurveyAnswerDTO();
            surveyAnswerDTO.setAnswer(answer.getAnswer());
            surveyAnswerDTO.setQuestion(answer.getQuestion().getQuestion());
            answers.add(surveyAnswerDTO);
        }
        return surveySubmissionDTO;
    }
}
