import React, { useState, useEffect } from "react";
import { Link, useHistory } from "react-router-dom";
import Navigation from "../components/Navigation";
import { Field, Form } from 'react-final-form'
import * as Service from "../service/Service"
import axios from "axios";
import { Rating } from 'react-simple-star-rating'

const Rate = () => {
    const history = useHistory();
    const [rating, setRating] = useState(0);
    const [survey, setSurvey] = useState();
    const [user, setUser] = useState();
    const [error, setError] = useState([]);


    const getLoggedInUser = async () => {
        try {
            const user = await axios.get("http://localhost:8080/user/me");
            setUser(user.data);
            Service.connectWebsocket(`/walletEntry/messages/${user.data.username}`);
        } catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    };
    const getSurveyByTitle = async () => {
        try {
            const survey = await Service.getSurveyByTitle("User Experience");
            console.log(survey);
            setSurvey(survey);
        } catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    };

    useEffect(() => {
        getLoggedInUser();
        getSurveyByTitle();
    }, [])

    async function saveSubmissionHandler(formValues) {
        setError(oldArray => []);
        if (rating == 0) {
            setError(oldArray => [...oldArray,
                "Please choose a rating!"
            ]);
        } else {
            try {
                var surveyAnswers = [];
                var surveyQuestions = [];
                survey.questions.map(question => {
                    surveyAnswers.push({
                        "answer": formValues["answer-" + question.question],
                        "question": question.question
                    })
                    surveyQuestions.push(question.question);
                })
                var surveyDTO = {
                    "title": survey.title,
                    "questions": surveyQuestions
                };

                await Service.createSurveySubmission(rating, surveyDTO, surveyAnswers)
                alert("You successfully rated your experience in the application! Thank you!");
                history.push('/user/account');
            }
            catch (err) {
                setError(oldArray => [...oldArray,
                err.message
                ]);
            }
        }
    }
    const handleRating = (rate) => {
        setRating(rate);
    }
    if (!user || !survey || user.role === "ADMIN") {
        return null;
    }
    return (

        <div>
            <Navigation />
            <div className="auth-wrapper">
                <div className="auth-inner fullLength">
                    <div className="row">
                        {error.map(error => (
                            <div className="form-group form-margin">
                                <div className="alert alert-warning" role="alert">{error}</div>
                            </div>
                        ))}
                        <div className="col-md-12">
                            <div className="card mdb-color lighten-2 text-center z-depth-2">
                                <div className="card-body">
                                    <Form
                                        onSubmit={saveSubmissionHandler}
                                    >
                                        {({ handleSubmit }) => (
                                            <form onSubmit={handleSubmit}>
                                                <div className="form-row">
                                                    <Field name="rating">
                                                        {props => (
                                                            <div className="form-group col-md-12">
                                                                <Rating onClick={handleRating} ratingValue={rating} />
                                                            </div>
                                                        )}
                                                    </Field>
                                                </div>
                                                <hr />
                                                {
                                                    survey.questions.map(question => (
                                                        <div className="form-row">
                                                            <Field name={"answer-" + question.question}>
                                                                {props => (
                                                                    <div className="form-group col-md-12">
                                                                        <label htmlFor={"answer-" + question.question}>{question.question}</label>
                                                                        <textarea {...props.input} type="text" className="form-control" id={"answer-" + question.question} placeholder="Answer" />
                                                                        <hr />
                                                                    </div>
                                                                )}
                                                            </Field>
                                                        </div>
                                                    ))
                                                }
                                                <button type="submit" className="btn btn-primary">Submit rating</button>
                                            </form>
                                        )}
                                    </Form>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    );
}

export default Rate;