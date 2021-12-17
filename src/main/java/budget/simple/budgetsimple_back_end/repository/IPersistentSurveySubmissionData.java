package budget.simple.budgetsimple_back_end.repository;

import budget.simple.budgetsimple_back_end.logic.survey.SurveySubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersistentSurveySubmissionData extends JpaRepository<SurveySubmission, String> {
}
