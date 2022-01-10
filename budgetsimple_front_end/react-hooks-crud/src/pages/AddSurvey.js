import React, { useState, useEffect } from "react";
import { Link, useHistory } from "react-router-dom";
import Navigation from "../components/Navigation";
import { Field, Form } from 'react-final-form'
import * as Service from "../service/Service"
import axios from "axios";

const AddSurvey = () => {
    const history = useHistory();
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
    useEffect(() => {
        getLoggedInUser();
    }, [])

    async function saveSurveyHandler(formValues) {
        setError(oldArray => []);
        if (formValues.title == null) {
            setError(oldArray => [...oldArray,
                "Please fill all the fields!"
            ]);
        } else {
            try {
                var questions = [];
                Array.from(Array(5), (e, i) => {
                    questions.push(formValues["question" + (i + 1)]);
                })
                await Service.createSurvey(formValues.title, questions);
                history.push("/allRatings");

            }
            catch (err) {
                setError(oldArray => [...oldArray,
                err.message
                ]);
            }
        }
    }
    if(!user || user.role === "USER"){
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
                                        onSubmit={saveSurveyHandler}
                                    >
                                        {({ handleSubmit }) => (
                                            <form onSubmit={handleSubmit}>
                                                <div className="form-row">
                                                    <Field name="title">
                                                        {props => (
                                                            <div className="form-group col-md-12">
                                                                <label htmlFor="title">Title</label>
                                                                <input {...props.input} type="text" className="form-control" id="title" placeholder="Title" />
                                                            </div>
                                                        )}
                                                    </Field>
                                                    <hr />
                                                </div>

                                                {
                                                    Array.from(Array(5), (e, i) => (
                                                        <div className="form-row">
                                                            <Field name={"question" + (i + 1)}>
                                                                {props => (
                                                                    <div className="form-group col-md-12">
                                                                        <label htmlFor={"question" + (i + 1)}>Question {(i + 1)}</label>
                                                                        <textarea {...props.input} type="text" className="form-control" id={"question" + (i + 1)} placeholder={"Question " + (i + 1)} />
                                                                    </div>
                                                                )}
                                                            </Field>
                                                            <hr />
                                                        </div>
                                                    )
                                                    )
                                                }
                                                <button type="submit" className="btn btn-primary">Save survey</button>
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

export default AddSurvey;