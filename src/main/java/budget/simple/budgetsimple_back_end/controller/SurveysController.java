package budget.simple.budgetsimple_back_end.controller;

import budget.simple.budgetsimple_back_end.logic.survey.*;
import budget.simple.budgetsimple_back_end.model.surveyDTOs.SurveyDTO;
import budget.simple.budgetsimple_back_end.model.surveyDTOs.SurveySubmissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveysController {
    @Autowired
    private SurveyManager sm;
    @Autowired
    SurveySubmissionManager ssm;

    // Get all submissions
    @GetMapping("/submission/getAll")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<SurveySubmission> getAllSurveySubmissions() {
        return ssm.getAllSurveySubmissions();
    }

    // Sort all submissions by rating
    @GetMapping("/submission/getAll/sort")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<SurveySubmission> getAllSurveySubmissionsSortedByRating() {
        return ssm.getAllSurveySubmissionsSortedByRating();
    }

    // Get surveySubmission by id
    @GetMapping("/submission/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SurveySubmission getById(@PathVariable(value = "id") String id) {
        return ssm.getSurveySubmissionById(id);
    }

    // Create survey submissions
    @PostMapping("/submission/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSurveySubmission(@RequestBody SurveySubmissionDTO surveySubmissionDTO) {
        Survey survey = sm.getSurveyByTitle(surveySubmissionDTO.getSurveyDTO().getTitle());
        ssm.addSurveySubmission(surveySubmissionDTO,survey);
    }

    // Get all surveys
    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Survey> getAllSurveys() {
        return sm.getAllSurveys();
    }

    // Get survey by title
    @GetMapping("/getByTitle/{title}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Survey getByTitle(@PathVariable(value = "title") String title) {
        return sm.getSurveyByTitle(title);
    }

    // Create survey
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSurvey(@RequestBody SurveyDTO surveyDTO) {
        sm.addSurvey(surveyDTO);
    }

}
