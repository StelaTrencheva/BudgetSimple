package budget.simple.budgetsimple_back_end.logic.survey;

import budget.simple.budgetsimple_back_end.logic.mapper.SurveyMapper;
import budget.simple.budgetsimple_back_end.model.surveyDTOs.SurveyDTO;
import budget.simple.budgetsimple_back_end.repository.IPersistentSurveyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyManager {
    private final IPersistentSurveyData surveyData;
    private final SurveyMapper surveyMapper;

    @Autowired
    public SurveyManager(IPersistentSurveyData surveyData, SurveyMapper surveyMapper) {
        this.surveyData = surveyData;
        this.surveyMapper = surveyMapper;
    }

    public void addSurvey(SurveyDTO surveyDTO){
        // add exception for already existing title
        surveyData.save(surveyMapper.mapToModel(surveyDTO));
    }
    public List<Survey> getAllSurveys(){
        return surveyData.findAll();
    }
    public Survey getSurveyByTitle(String title){
        // add exception for not existing survey
        return surveyData.findByTitle(title);
    }
}
