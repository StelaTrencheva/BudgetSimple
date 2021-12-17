package budget.simple.budgetsimple_back_end.logic.survey;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "SurveyQuestion")
@Table(
        name = "survey_questions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "id",
                        columnNames = "id"
                )

        })
public class SurveyQuestion {
    @Id
    @Column
            (
                    name = "id",
                    updatable = false,
                    columnDefinition = "varchar(255)"
            )
    @Getter @Setter private String id;
    @Column
            (
                    name = "question",
                    updatable = false,
                    columnDefinition = "text"
            )
    @Getter @Setter private String question;

//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name = "survey_id", nullable = false)
//    @Getter @Setter private Survey survey;

    public SurveyQuestion(String question){
        this.id = UUID.randomUUID().toString();;
        this.question = question;
    }

    protected SurveyQuestion() {

    }
}
