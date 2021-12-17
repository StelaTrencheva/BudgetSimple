package budget.simple.budgetsimple_back_end.logic.survey;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "SurveySubmission")
@Table(
    name = "survey_submissions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "id",
                        columnNames = "id"
                )

        }
)
public class SurveySubmission {
    @Id
    @Column(
            name = "id",
            updatable = false,
            columnDefinition = "varchar(255)"
    )
    @Getter @Setter private String id;
    @Column(
            name = "rating",
            updatable = false,
            columnDefinition = "double"
    )
    @Getter @Setter private double rating;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "survey_id", nullable = false)
    @Getter @Setter private Survey survey;

    @ElementCollection
    @CollectionTable(name = "survey_submission_answers", joinColumns = @JoinColumn(name = "survey_submission_id"))
    @Getter @Setter private List<SurveyAnswer> surveyAnswers;

    public SurveySubmission(double rating, Survey survey, List<SurveyAnswer> surveyAnswers){
        this.id = UUID.randomUUID().toString();
        this.rating = rating;
        this.survey = survey;
        this.surveyAnswers = surveyAnswers;
    }

    protected SurveySubmission() {

    }
}
