package budget.simple.budgetsimple_back_end.logic.survey;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
public class SurveyAnswer {
    @Getter @Setter private String answer;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "survey_question_id", nullable = false)
    @Getter @Setter private SurveyQuestion question;

    public SurveyAnswer(String answer, SurveyQuestion question){

        this.answer = answer;
        this.question = question;
    }
    protected SurveyAnswer() {

    }
}
