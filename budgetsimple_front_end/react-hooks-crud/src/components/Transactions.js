import * as Service from '../service/Service'
import React, { useState, useEffect } from "react";
import AllTransactions from "../components/AllTransactions";
import AddTransaction from "../components/AddTransaction";
import axios from "axios";
import { Link, useHistory } from "react-router-dom";

const Transactions = (props) => {

    const [transactions, setTransactions] = useState([]);
    const [error, setError] = useState([]);
    const [addTransactionSelected, setAddTransactionSelected] = useState(false);
    const [hiddenButton, setHiddenButton] = useState(false);

    function buttonClickHandler() {
        setAddTransactionSelected(true);
        setHiddenButton(true);
    }

    const getTransactions = async () => {
        try {
            const transactions = await Service.getTransactionsByWalletId(props.walletId);
            setTransactions(transactions);
        }
        catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    }
    useEffect(() => {
        getTransactions();
    }, [])

    if (!transactions) {
        return null;
    }
    return (
        <div className="row">
            {error.map(error => (
                <div className="form-group form-margin">
                    <div className="alert alert-warning" role="alert">{error}</div>
                </div>
            ))}
            {
                addTransactionSelected ? (
                    <AddTransaction showAddButton={setHiddenButton} changeViewStatus={setAddTransactionSelected} walletId={props.walletId}></AddTransaction>
                ) : (
                    <AllTransactions transactions={transactions}></AllTransactions>
                )
            }
            {hiddenButton ?
                null
                :
                <div>
                    <hr />
                    <div className="row wallet-row">
                        <div className="col-lg-12">
                            <button onClick={buttonClickHandler} className="btn btn-primary">
                                Add transaction
                            </button>
                        </div>
                    </div>
                </div>
            }
        </div>
    );
}
export default Transactions
