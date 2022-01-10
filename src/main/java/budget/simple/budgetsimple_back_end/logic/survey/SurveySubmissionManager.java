package budget.simple.budgetsimple_back_end.logic.survey;

import budget.simple.budgetsimple_back_end.logic.mapper.SurveySubmissionMapper;
import budget.simple.budgetsimple_back_end.model.surveyDTOs.SurveySubmissionDTO;
import budget.simple.budgetsimple_back_end.repository.IPersistentSurveySubmissionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveySubmissionManager {
    private final IPersistentSurveySubmissionData surveySubmissionData;
    private final SurveySubmissionMapper surveySubmissionMapper;

    @Autowired
    public SurveySubmissionManager(IPersistentSurveySubmissionData surveySubmissionData, SurveySubmissionMapper surveySubmissionMapper) {
        this.surveySubmissionData = surveySubmissionData;
        this.surveySubmissionMapper = surveySubmissionMapper;
    }
    public void addSurveySubmission(SurveySubmissionDTO surveySubmissionDTO, Survey survey){
        // add exception for already existing title
        surveySubmissionData.save(surveySubmissionMapper.mapToModel(surveySubmissionDTO,survey));
    }
    public List<SurveySubmission> getAllSurveySubmissions(){
       return surveySubmissionData.findAll();
    }
    public List<SurveySubmission> getAllSurveySubmissionsSortedByRating(){
        return surveySubmissionData.findAll(Sort.by("rating"));
    }
    public SurveySubmission getSurveySubmissionById(String id){
        return surveySubmissionData.findById(id).orElse(null);
    }
}
