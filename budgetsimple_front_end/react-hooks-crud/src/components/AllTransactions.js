import React, { useState, useEffect, useParams } from "react";
import { Link, useHistory } from "react-router-dom";
import logo from "../images/logo.png";

const AllTransactions = (props) => {

    return (
        <div className="row">
            <hr />
            {props.transactions.map(transaction => (
                <div>
                    <div className="card border border-dark">
                        <div className="card-body flex">
                            <div className="row wallet-row">
                                <div className="col-lg-3">
                                    <Link to={{
                                        pathname: `/user/wallets/${props.walletId}/transaction/${transaction.id}`,
                                        state: {
                                            transactionId: transaction.id,
                                            walletId: props.walletId
                                        }
                                    }}><img className="smallLogo mx-auto d-block" src={logo} alt="logo" /></Link>
                                </div>
                                <div className="col-lg-3">
                                    <h5>Title: {transaction.title}</h5>
                                </div>
                                <div className="col-lg-3">
                                    <h5>Amount: {transaction.amount}</h5>
                                </div>
                                <div className="col-lg-3">
                                    <h5>Creator: {transaction.creator.firstName} {transaction.creator.lastName}</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br />
                </div>
            ))
            }
        </div>
    )
}
export default AllTransactions
