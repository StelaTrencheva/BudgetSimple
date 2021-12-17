package budget.simple.budgetsimple_back_end.logic.mapper;

import budget.simple.budgetsimple_back_end.logic.survey.Survey;
import budget.simple.budgetsimple_back_end.logic.survey.SurveyQuestion;
import budget.simple.budgetsimple_back_end.model.surveyDTOs.SurveyDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SurveyMapper {
    public SurveyMapper() {
    }

    public Survey mapToModel(SurveyDTO surveyDTO) {
        List<SurveyQuestion> survey_questions = new ArrayList<>();
        for (String question:surveyDTO.getQuestions()
        ) {
            survey_questions.add(new SurveyQuestion(question));
        }
        return new Survey(surveyDTO.getTitle(), survey_questions);
    }

    public SurveyDTO mapToDTO(Survey survey) {
        SurveyDTO surveyDTO = new SurveyDTO();
        surveyDTO.setTitle(survey.getTitle());
        List<String> questions = new ArrayList<>();
        for (SurveyQuestion question:survey.getQuestions()
        ) {
            questions.add(question.getQuestion());
        }
        surveyDTO.setQuestions(questions);
        return surveyDTO;
    }
}
