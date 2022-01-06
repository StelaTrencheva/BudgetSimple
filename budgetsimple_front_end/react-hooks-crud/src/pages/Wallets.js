import React, { useState, useEffect } from "react";
import { Link, useHistory } from "react-router-dom";
import Navigation from "../components/Navigation";
import logo from "../images/logo.png";
import axios from "axios";
import Field from "../components/Field"
import * as Service from '../service/Service'
import AddWallet from "../pages/AddWallet"

function getWalletTotalSpent(wallets) {
    let totalSpent = 0;
    wallets.map(wallet => wallet.transactions.map(transaction => totalSpent += transaction.amount));
    return totalSpent;
}
const Wallets = () => {
    const history = useHistory();
    const [user, setUser] = useState();
    const [wallets, setWallets] = useState([]);
    const [error, setError] = useState([]);

    const getUserAndWallets = async () => {
        try {
            const loggedInUser = await axios.get("http://localhost:8080/user/me");
            console.log(loggedInUser.data);
            const foundWallets = await axios.post("http://localhost:8080/wallet/getAll",
                loggedInUser.data
            );
            setUser(loggedInUser.data);
            setWallets(foundWallets.data);
            console.log(foundWallets.data);
        } catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    };

    useEffect(() => {
        getUserAndWallets();
    }, [])

    if (!user) {
        return null;
    }
    return (


        <div className="row">
            {error.map(error => (
                <div className="form-group form-margin">
                    <div className="alert alert-warning" role="alert">{error}</div>
                </div>
            ))}
            {wallets.map(wallet => (
                <>
                    <div className="card border border-dark">
                        <div className="card-body flex">
                            <div className="row wallet-row">
                                <div className="col-lg-3">
                                    <Link to={{
                                        pathname: `/user/wallets/${wallet.id}`,
                                        state: wallet.id
                                    }}><img className="smallLogo mx-auto d-block" src={logo} alt="logo" /></Link>
                                </div>
                                <div className="col-lg-3">
                                    <h5>Title: {wallet.title}</h5>
                                </div>
                                <div className="col-lg-3">
                                    <h5>Members: {wallet.members.length}</h5>
                                </div>
                                <div className="col-lg-3">
                                    <h5>Spent amount: {getWalletTotalSpent(wallets)}</h5>
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
                    <Link to="/user/addWallet" className="btn btn-primary">
                        Add wallet
                    </Link>
                </div>
            </div>
        </div>

    );
}

export default Wallets;