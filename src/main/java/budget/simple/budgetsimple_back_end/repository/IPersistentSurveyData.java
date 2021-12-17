package budget.simple.budgetsimple_back_end.repository;

import budget.simple.budgetsimple_back_end.logic.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IPersistentSurveyData extends JpaRepository<Survey, String> {
    @Query("SELECT s FROM Survey s WHERE s.title = :title")
    Survey findByTitle(@Param("title") String title);

}
