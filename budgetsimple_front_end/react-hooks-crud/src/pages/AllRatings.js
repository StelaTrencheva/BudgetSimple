import React, { useState, useEffect } from "react";
import { Link, useHistory } from "react-router-dom";
import Navigation from "../components/Navigation";
import logo from "../images/logo.png";
import axios from "axios";
import * as Service from '../service/Service'
import { Rating } from 'react-simple-star-rating'

function getWalletTotalSpent(wallet) {
    let totalSpent = 0;
    wallet.transactions.map(transaction => totalSpent += transaction.amount);
    return totalSpent;
}
const AllRatings = () => {
    const history = useHistory();
    const [user, setUser] = useState();
    const [submissions, setSubmissions] = useState([]);
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
    const getAllSubmissions = async () => {
        try {
            const submissions = await Service.getAllSubmissions();
            setSubmissions(submissions);
        } catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    };
    useEffect(() => {
        getLoggedInUser();
        getAllSubmissions();
    }, [])

    if (!user ) {
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
                        {submissions.map(submission => (
                            <>
                                <div className="card border border-dark">
                                    <div className="card-body flex">
                                        <div className="row wallet-row">
                                            <div className="col-lg-3">
                                                <Link to={{
                                                    pathname: `/submissions/${submission.id}`,
                                                    state: submission.id
                                                }}><img className="smallLogo mx-auto d-block" src={logo} alt="logo" /></Link>
                                            </div>
                                            <div className="col-lg-3">
                                                <h5>Survey: {submission.survey.title}</h5>
                                            </div>
                                            <div className="col-lg-3">
                                                <Rating readonly ratingValue={submission.rating} />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <hr />
                            </>
                        ))}
                        <hr />
                        <div className="row">
                            <div className="col-lg-12">
                                <Link to="/addSurvey" className="btn btn-primary">
                                    Add survey
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AllRatings;