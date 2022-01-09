import * as Service from '../service/Service'
import React, { useState, useEffect } from "react";
import AllTransactions from "../components/AllTransactions";
import AddTransaction from "../components/AddTransaction";
import Navigation from '../components/Navigation';
import axios from "axios";
import { Link, useHistory } from "react-router-dom";

const Transactions = (props) => {

    const [transactions, setTransactions] = useState([]);

    const [walletId, setWalletId] = useState();
    const [user, setUser] = useState();
    const [error, setError] = useState([]);
    const [addTransactionSelected, setAddTransactionSelected] = useState(false);
    const [hiddenButton, setHiddenButton] = useState(false);

    const getLoggedInUser = async () => {
        const user = await axios.get("http://localhost:8080/user/me");
        setUser(user.data);
        Service.connectWebsocket(`/walletEntry/messages/${user.data.username}`);
    };

    function buttonClickHandler() {
        setAddTransactionSelected(true);
        setHiddenButton(true);
    }

    const getTransactions = async () => {
        try {
            const transactions = await Service.getTransactionsByWalletId(props.walletId);
            setWalletId(props.walletId);
            setTransactions(transactions);
        }
        catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    }
    useEffect(() => {
        getLoggedInUser();
        getTransactions();
    }, [])

    if (!transactions || !user) {
        return null;
    }
    return (


        <div className="card mdb-color lighten-2 text-center z-depth-2">
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
                                <AllTransactions transactions={transactions} walletId={walletId}></AllTransactions>
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
                </div>
    );
}
export default Transactions
