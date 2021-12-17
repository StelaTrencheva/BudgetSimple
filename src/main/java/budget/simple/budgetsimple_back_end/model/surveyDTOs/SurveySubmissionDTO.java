package budget.simple.budgetsimple_back_end.model.surveyDTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SurveySubmissionDTO {

    @Getter @Setter private double rating;
    @Getter @Setter private SurveyDTO surveyDTO;
    @Getter @Setter private List<SurveyAnswerDTO> surveyAnswers;
}
