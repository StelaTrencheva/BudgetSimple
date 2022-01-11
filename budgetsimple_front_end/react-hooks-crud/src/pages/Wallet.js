import React, { useState, useEffect, useParams } from "react";
import { Link, useHistory } from "react-router-dom";
import axios from "axios";
import * as Service from '../service/Service'
import Transactions from "../components/Transactions.js";
import Statistics from "../components/Statistics.js";
import Navigation from '../components/Navigation';
import WalletInfo from "../components/WalletInfo";


const Wallet = (props) => {
    const history = useHistory();
    const walletId = props.location.state;
    const [wallet, setWallet] = useState();
    const [user, setUser] = useState();
    const [error, setError] = useState([]);
    const [viewSelected, setViewSelected] = useState(null);

    const getLoggedInUser = async () => {
        const user = await axios.get("http://localhost:8080/user/me");
        setUser(user.data);
        Service.connectWebsocket(`/walletEntry/messages/${user.data.username}`);
    };
    const getCurrentWallet = async () => {
        try {
            const wallet = await Service.getWalletById(walletId);
            setWallet(wallet);
        }
        catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    }

    useEffect(() => {
        getLoggedInUser();
        getCurrentWallet();
    }, [])

    function chosenOptionHandler(option) {
        if (option == "transactions") {
            setViewSelected(<Transactions walletId={walletId}></Transactions>)
        } else if (option == "walletInfo") {
            setViewSelected(<WalletInfo walletId={walletId}></WalletInfo>)
        }
        else {
            setViewSelected(<Statistics walletId={walletId}></Statistics>)
        }
    }

    if (!user || !wallet) {
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



                        <div className="row wallet-row">

                            <div className="col-lg-4">
                                <button onClick={() => { chosenOptionHandler("transactions") }} className="btn btn-primary" id="transactions">
                                    Transactions
                                </button>
                            </div>

                            <div className="col-lg-4">
                                <button onClick={() => { chosenOptionHandler("walletInfo") }} className="btn btn-primary" id="walletInfo">
                                    Wallet Info
                                </button>
                            </div>

                            <div className="col-lg-4">
                                <button onClick={() => { chosenOptionHandler("statistics") }} className="btn btn-primary" id="Statistics">
                                    Statistics
                                </button>
                            </div>

                        </div>
                        <br />
                        {viewSelected}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Wallet;