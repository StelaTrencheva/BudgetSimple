import React, { useState, useEffect, useParams } from "react";

const AllTransactions = (props) => {

    return (
        <div className="row">
        <hr/>
            {props.transactions.map(transaction => (
                <div>
                <div className="card border border-dark">
                    <div className="card-body flex">
                        <div className="row wallet-row">
                            <div className="col-lg-4">
                                <h5>Title: {transaction.title}</h5>
                            </div>
                            <div className="col-lg-4">
                                <h5>Amount: {transaction.amount}</h5>
                            </div>
                            <div className="col-lg-4">
                                <h5>Creator: {transaction.creator.firstName} {transaction.creator.lastName}</h5>
                            </div>
                        </div>
                    </div>
                </div>
                <br/>
                </div>
            ))
}
        </div>
    )
}
export default AllTransactions
