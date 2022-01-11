package budget.simple.budgetsimple_back_end.logic.survey;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "Survey")
@Table(
        name = "survey",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "id",
                        columnNames = "id"
                ),
                @UniqueConstraint(
                        name = "title",
                        columnNames = "title"
                )

        }
)
public class Survey {
    @Id
    @Column(
            name = "id",
            updatable = false,
            columnDefinition = "varchar(255)"
    )
    @Getter
    @Setter
    private String id;
    @Column(
            name = "title",
            updatable = false,
            columnDefinition = "varchar(255)"
    )
    @Getter
    @Setter
    private String title;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id")
    @Getter @Setter private List<SurveyQuestion> questions;

    public Survey(String title, List<SurveyQuestion> survey_questions)
    {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.questions = survey_questions;
    }

    protected Survey() {

    }
}
