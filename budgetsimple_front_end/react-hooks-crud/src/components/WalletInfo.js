import * as Service from '../service/Service'
import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link, useHistory } from "react-router-dom";
import { Field, Form } from 'react-final-form'

const WalletInfo = (props) => {

    const history = useHistory();
    const [walletQrCode, setWalletQrCode] = useState();
    const [wallet, setWallet] = useState();
    const [error, setError] = useState([]);
    const [user, setUser] = useState();
    const [seeRequestsClicked, setSeeRequestsClicked] = useState(false);

    const getLoggedInUser = async () => {
        const user = await axios.get("http://localhost:8080/user/me");
        setUser(user.data);
    };

    const getCurrentWallet = async () => {
        try {
            const wallet = await Service.getWalletById(props.walletId);
            setWallet(wallet);
        }
        catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    }
    const getWalletQrCode = async () => {
        try {
            const image = await Service.getWalletQrCode(props.walletId);
            setWalletQrCode(image);
            console.log(image);
        }
        catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    }

    async function deleteWalletHandler() {
        if (wallet.creator.id == user.id) {
            try {
                await Service.deleteWallet(wallet.id);
                history.push("/user/wallets");
            }
            catch (err) {
                setError(oldArray => [...oldArray,
                err.message
                ]);
            }
        }
    }

    function seeRequestsHandler() {
        if (seeRequestsClicked) {
            setSeeRequestsClicked(false);
        } else {
            setSeeRequestsClicked(true);

        }
    }

    async function changeBudgetHandler(formValues) {
        setError(oldArray => []);
        if (formValues.newBudget == null) {
            setError(oldArray => [...oldArray,
                "Please fill all the fields!"
            ]);
        } else {
            try {
                await Service.changeWalletBudget(wallet.id, formValues.newBudget)
                history.go(0);
            }
            catch (err) {
                setError(oldArray => [...oldArray,
                err.message
                ]);
            }
        }
    }
    
    async function rejectEntryRequest(request) {
            try {
                await Service.rejectEntryRequest(wallet.id, request)
            }
            catch (err) {
                setError(oldArray => [...oldArray,
                err.message
                ]);
            }
    }
    async function acceptEntryRequest(request) {
        try {
            await Service.acceptEntryRequest(wallet.id, request)
        }
        catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
}
    function copyWalletCode() {
        navigator.clipboard.writeText("http://localhost:3000/user/wallets/code/" + wallet.generatedCode.link);
    }

    useEffect(() => {
        getCurrentWallet();
        getWalletQrCode();
        getLoggedInUser();

    }, [])

    if (!wallet || !user) {
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
                                            <p>{wallet.title}</p>
                                        </div>
                                    </div>
                                    <div className="row wallet-row">
                                        <div className='col-md-6'>
                                            <h6>Description: </h6>
                                        </div>
                                        <div className='col-md-6'>
                                            <p>{wallet.description}</p>
                                        </div>
                                    </div>
                                    <div className="row wallet-row">
                                        <div className='col-md-6'>
                                            <h6>Budget: </h6>
                                        </div>
                                        <div className='col-md-6'>
                                            <p>{wallet.budget}</p>
                                        </div>
                                    </div>
                                    <div className="row wallet-row">
                                        <div className='col-md-6'>
                                            <h6>Currency: </h6>
                                        </div>
                                        <div className='col-md-6'>
                                            <p>{wallet.currency}</p>
                                        </div>
                                    </div>
                                    <div className="row wallet-row">
                                        <div className='col-md-6'>
                                            <h6>Date of creation: </h6>
                                        </div>
                                        <div className='col-md-6'>
                                            <p>{wallet.dateOfCreation} k</p>
                                        </div>
                                    </div>
                                    <div className="row wallet-row">
                                        <div className='col-md-6'>
                                            <h6>Number of transactions: </h6>
                                        </div>
                                        <div className='col-md-6'>
                                            <p>{wallet.transactions.length}</p>
                                        </div>
                                    </div>
                                    <Form
                                        onSubmit={changeBudgetHandler}
                                    >
                                        {({ handleSubmit }) => (
                                            <form onSubmit={handleSubmit}>
                                                <div className="row wallet-row">
                                                    <Field name="newBudget">
                                                        {props => (
                                                            <div className="form-group col-md-6">
                                                                <input {...props.input} type="text" className="form-control" id="newBudget" placeholder="New budget" />
                                                            </div>
                                                        )}
                                                    </Field>
                                                    <div className='col-md-6'>
                                                        <button type="submit" className="btn btn-primary">
                                                            Change budget
                                                        </button>
                                                    </div>
                                                </div>
                                            </form>
                                        )}
                                    </Form>
                                </div>
                            </div>
                        </div>
                        <div className="col-lg-6">
                            <div className="card border border-dark">
                                <div className="card-body flex">
                                    <div className="row wallet-row">
                                        <div className='col-md-6'>
                                            <h6>Members: </h6>
                                        </div>
                                        <div className='col-md-6'>
                                            <p>{wallet.members.length}</p>
                                        </div>
                                    </div>
                                    <hr />
                                    {wallet.members.map(member => (
                                        <div className="row wallet-row">
                                            <div className='col-md-12'>
                                                <h6>{member.firstName} {member.lastName}</h6>
                                            </div>
                                        </div>
                                    ))}
                                    <hr />
                                    <div className="row">
                                        <div className="col-lg-12">
                                            <img src={walletQrCode} />
                                        </div>
                                    </div>
                                    <hr />
                                    <div className="row">
                                        <div className="col-lg-12">
                                            <button onClick={copyWalletCode} className="btn btn-primary">
                                                Copy wallet code
                                            </button>
                                        </div>
                                    </div>
                                    <hr />
                                    <div className="row">
                                        <div className="col-lg-12">
                                            <button onClick={seeRequestsHandler} className="btn btn-primary">
                                                {seeRequestsClicked ? 'Hide Requests' : 'See requests'}
                                            </button>
                                        </div>
                                    </div>
                                    <hr />
                                    <div className="row">
                                        <div className="col-lg-12">
                                            {seeRequestsClicked ?
                                                wallet.walletEntryRequests.map(request => (
                                                    <div className="row">
                                                        <div className='col-md-4'>
                                                            <h6>{request.user.firstName} {request.user.lastName}</h6>
                                                        </div>
                                                        <div className='col-md-4'>
                                                            <button onClick={ () => acceptEntryRequest(request)} className="btn btn-primary">
                                                                Accept
                                                            </button>
                                                        </div>
                                                        <div className='col-md-4'>
                                                            <button onClick={rejectEntryRequest} className="btn btn-danger">
                                                                Reject
                                                            </button>
                                                        </div>
                                                    <br></br>
                                                    </div>))

                                                : null}
                                        </div>
                                    </div>

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
                    <button onClick={deleteWalletHandler} className="btn btn-primary">
                        {(wallet.creator.id == user.id) ? 'Delete wallet' : 'Leave wallet'}
                    </button>
                </div>
            </div>
        </div>
    );
}
export default WalletInfo
