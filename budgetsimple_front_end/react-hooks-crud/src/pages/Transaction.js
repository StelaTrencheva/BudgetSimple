import React, { useState, useEffect, useParams } from "react";
import { Link, useHistory } from "react-router-dom";
import Navigation from "../components/Navigation";
import logo from "../images/logo.png";
import axios from "axios";
import * as Service from '../service/Service'
import Transactions from "../components/Transactions.js";
import { Field, Form } from 'react-final-form'
import WalletInfo from "../components/WalletInfo";


const Transaction = (props) => {
    const history = useHistory();
    const {transactionId} = props.location.state;
    const {walletId} = props.location.state;
    const [transaction, setTransaction] = useState();
    const [user, setUser] = useState();
    const [error, setError] = useState([]);
    const [viewSelected, setViewSelected] = useState(null);

    const getLoggedInUser = async () => {
        const user = await axios.get("http://localhost:8080/user/me");
        setUser(user.data);
        Service.connectWebsocket(`/walletEntry/messages/${user.data.username}`);
    };
    const getCurrentTransaction = async () => {
        try {
            const transaction = await Service.getTransactionById(transactionId);
            setTransaction(transaction);
            console.log(transaction);
        }
        catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    }

    async function deleteTransactionHandler() {
            try {
                await Service.deleteTransaction(walletId, transactionId);
                history.go(-1);
            }
            catch (err) {
                setError(oldArray => [...oldArray,
                err.message
                ]);
            }
    }
    useEffect(() => {
        getLoggedInUser();
        getCurrentTransaction();
    }, [])

    if (!user || !transaction) {
        return null;
    }
    return (
        <div className="row">
            {error.map(error => (
                <div className="form-group form-margin">
                    <div className="alert alert-warning" role="alert">{error}</div>
                </div>
            ))}
            <div className="card border">
                <div className="card-body flex">
                    <div className="row wallet-row">
                        <div className="col-lg-6">
                            <div className="card border border-dark">
                                <div className="card-body flex">
                                    <div className="row wallet-row">
                                        <div className='col-md-6'>
                                            <h6>Title: </h6>
                                        </div>
                                        <div className='col-md-6'>
                                            <p>{transaction.title}</p>
                                        </div>
                                    </div>
                                    <div className="row wallet-row">
                                        <div className='col-md-6'>
                                            <h6>Description: </h6>
                                        </div>
                                        <div className='col-md-6'>
                                            <p>{transaction.description}</p>
                                        </div>
                                    </div>
                                    <div className="row wallet-row">
                                        <div className='col-md-6'>
                                            <h6>Category: </h6>
                                        </div>
                                        <div className='col-md-6'>
                                            <p>{transaction.category}</p>
                                        </div>
                                    </div>
                                    <div className="row wallet-row">
                                        <div className='col-md-6'>
                                            <h6>Amount: </h6>
                                        </div>
                                        <div className='col-md-6'>
                                            <p>{transaction.amount}</p>
                                        </div>
                                    </div>
                                    <div className="row wallet-row">
                                        <div className='col-md-6'>
                                            <h6>Date of creation: </h6>
                                        </div>
                                        <div className='col-md-6'>
                                            <p>{transaction.dateOfCreation} k</p>
                                        </div>
                                    </div>
                                    <div className="row wallet-row">
                                        <div className='col-md-6'>
                                            <h6>Creator: </h6>
                                        </div>
                                        <div className='col-md-6'>
                                            <p>{transaction.creator.firstName} {transaction.creator.lastName}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="col-lg-6">
                            <div className="card border border-dark">
                                <div className="card-body flex">
                                    {transaction.memberAmounts.map(ma => (
                                        <div className="row wallet-row">
                                            <div className='col-md-6'>
                                                <h6>{ma.member.firstName} {ma.member.lastName}</h6>
                                            </div>
                                            <div className='col-md-6'>
                                                <h6>{ma.amount}</h6>
                                            </div>
                                        </div>
                                    ))}
                                    <hr />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br />
            < hr />
            <div className="row">
                <div className="col-lg-12">
                    <button onClick={deleteTransactionHandler} className="btn btn-primary">
                        Delete transaction
                    </button>
                </div>
            </div>
        </div>

    );
}

export default Transaction;