import React, { useState, useEffect, useParams } from "react";
import { Link, useHistory } from "react-router-dom";
import axios from "axios";
import * as Service from '../service/Service'
import Transactions from "../components/Transactions.js";
import Statistics from "../components/Statistics.js";
import Navigation from '../components/Navigation';
import WalletInfo from "../components/WalletInfo";


const Submission = (props) => {
    const history = useHistory();
    const submissionId = props.location.state;
    const [submission, setSubmission] = useState();
    const [user, setUser] = useState();
    const [error, setError] = useState([]);
    const [viewSelected, setViewSelected] = useState(null);

    const getLoggedInUser = async () => {
        const user = await axios.get("http://localhost:8080/user/me");
        setUser(user.data);
        Service.connectWebsocket(`/walletEntry/messages/${user.data.username}`);
    };
    const getCurrentSubmission = async () => {
        try {
            const submission = await Service.getCurrentSubmission(submissionId);
            setSubmission(submission);
        }
        catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    }

    useEffect(() => {
        getLoggedInUser();
        getCurrentSubmission();
    }, [])

    if (!user || !submission) {
        return null;
    }
    return (
        <div>
            <Navigation />
            <div className="auth-wrapper">
                <div className="auth-inner fullLength">
                    <div className="card mdb-color lighten-2 text-center z-depth-2"></div>

                    <div className="card mdb-color lighten-2 text-center z-depth-2">
                        <div className="row">
                            {error.map(error => (
                                <div className="form-group form-margin">
                                    <div className="alert alert-warning" role="alert">{error}</div>
                                </div>
                            ))}

                            <div className="card border">
                                <div className="card-body flex">
                                    {
                                        submission.surveyAnswers.map(answer => (
                                            <div className="row wallet-row">
                                                <div className="col-lg-12">
                                                    <h6>Question: {answer.question.question}</h6>
                                                    <h6>Answer: {answer.answer}</h6>
                                                </div>
                                                <hr />
                                            </div>
                                        ))
                                    }

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Submission;