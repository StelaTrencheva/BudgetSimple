package budget.simple.budgetsimple_back_end.model.surveyDTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SurveyDTO {
    @Getter @Setter private String title;
    @Getter @Setter private List<String> questions;
}
