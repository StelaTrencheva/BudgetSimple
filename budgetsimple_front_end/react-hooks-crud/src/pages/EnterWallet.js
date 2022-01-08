import React, { useState, useEffect } from "react";
import { Link, useHistory } from "react-router-dom";
import Navigation from "../components/Navigation";
import { Field, Form } from 'react-final-form'
import * as Service from "../service/Service"
import axios from "axios";
function checkWalletMembers(wallet, user) {
    wallet.members.map(member => {
        if (member.username === user.username) {
            return true;
        }
        return false;
    })
}
const EnterWallet = () => {
    const history = useHistory();
    const [user, setUser] = useState();
    const [wallet, setWallet] = useState();
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
            localStorage.setItem("redirect_path", window.location.pathname)
            history.push("/sign-in")
        }
    };
    const getWalletByCode = async () => {
        try {
            const walletche = await Service.getWalletByCode(window.location.pathname.split('/').pop())
            if (walletche == "") {
                history.push("/sign-in");
            }
            setWallet(walletche)
        } catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
            history.push("/sign-in")

        }
    };

    useEffect(() => {
        getLoggedInUser()
        getWalletByCode()
    }, [])

    async function askToJoinHandler() {
        try {
            await Service.addWalletEntryRequest(wallet.id, user)
            alert("You successfully requested to join this wallet!");
        }
        catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    }

if (!user) {
    return null;
}
if (wallet) {
    wallet.members.map(member => {
        if (member.username === user.username) {
            history.push('/user/wallets')
        }
    })
}
if (!wallet) {
    return null;
}
return (
    <div className="row">
        {error.map(error => (
            <div className="form-group form-margin">
                <div className="alert alert-warning" role="alert">{error}</div>
            </div>
        ))}
        <div className="col-md-12">
            <div className="card mdb-color lighten-2 text-center z-depth-2">
                <div className="card-body">
                    <div className="row wallet-row">
                        <div className='col-md-6'>
                            <h6>Title: </h6>
                        </div>
                        <div className='col-md-6'>
                            <p>{wallet.title}</p>
                        </div>
                    </div>
                    <hr />
                    <div className="row wallet-row">
                        <button onClick={askToJoinHandler} className='btn btn-primary'>Ask to join!</button>
                    </div>
                </div>
            </div>
        </div>
    </div>


);
}

export default EnterWallet;