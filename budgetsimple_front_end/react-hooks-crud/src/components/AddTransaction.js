import * as Service from '../service/Service'
import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link, useHistory } from "react-router-dom";
import { Field, Form } from 'react-final-form'
const AddTransaction = (props) => {

    const history = useHistory();
    const [wallet, setWallet] = useState();
    const [user, setUser] = useState();
    const [error, setError] = useState([]);
    const [transactionCategories, setTransactionCategories] = useState([]);


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
    const getTransactionCategories = async () => {
        try {
            const transactionCategories = await Service.getTransactionCategories();
            setTransactionCategories(transactionCategories);
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
        getTransactionCategories();
    }, [])

    async function saveTransactionHandler(formValues) {
        var memberAmounts = [];
        var amountPerMember;
        setError(oldArray => []);
        if (formValues.title == null || formValues.description == null || formValues.amount == null) {
            setError(oldArray => [...oldArray,
                "Please fill all the fields!"
            ]);
        } else {
            try {
                let notFilledAmountsPerMember = 0;
                var totalAmountOfAmountsPerMember = 0;
                wallet.members.map(member => {
                    if (formValues["split_amount_" + member.username] == null) {
                        notFilledAmountsPerMember++;
                    } else {
                        totalAmountOfAmountsPerMember = totalAmountOfAmountsPerMember + Number(formValues["split_amount_" + member.username]);
                    }
                })
                if (notFilledAmountsPerMember == 0 && (totalAmountOfAmountsPerMember > formValues.amount || totalAmountOfAmountsPerMember < formValues.amount)) {
                    setError(oldArray => [...oldArray,
                        "Please fill amounts of members so that together they match the total amount!"
                    ]);
                } else
                    if (notFilledAmountsPerMember > 0 && notFilledAmountsPerMember < wallet.members.length) {
                        setError(oldArray => [...oldArray,
                            "Please fill amounts for all members or for noone!"
                        ]);
                    } else {
                        props.changeViewStatus(false);
                        props.showAddButton(false);
                        wallet.members.map(member => {
                            memberAmounts.push(...memberAmounts,
                                {
                                    'user': member,
                                    'amount': formValues["split_amount_" + member.username]
                                });
                        })
                        if(formValues.category == null){
                            formValues.category = "Groceries"
                        }
                        var dateOfCreation = new Date();
                        await Service.createTransaction(wallet.id, user, formValues.amount, formValues.title, formValues.description, formValues.category, dateOfCreation, memberAmounts);
                        history.go(0);
                    }
            }
            catch (err) {
                setError(oldArray => [...oldArray,
                err.message
                ]);
            }
        }

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
                        <Form
                            onSubmit={saveTransactionHandler}
                        >
                            {({ handleSubmit }) => (
                                <form onSubmit={handleSubmit}>
                                    <div className="form-row">
                                        <Field name="title">
                                            {props => (
                                                <div className="form-group col-md-12">
                                                    <label htmlFor="title">Title</label>
                                                    <input {...props.input} type="text" className="form-control" id="title" placeholder="Title" />
                                                </div>
                                            )}
                                        </Field>
                                    </div>
                                    <hr />
                                    <div className="form-row">
                                        <Field name="description">
                                            {props => (
                                                <div className="form-group col-md-12">
                                                    <label htmlFor="description">Description</label>
                                                    <textarea {...props.input} type="text" className="form-control" id="description" placeholder="Description" />
                                                </div>
                                            )}
                                        </Field>
                                    </div>
                                    <hr></hr>
                                    <div className="row">
                                        <Field name="amount">
                                            {props => (
                                                <div className="form-group col-md-6">
                                                    <label htmlFor="amount">Amount</label>
                                                    <input {...props.input} type="amount" className="form-control" id="amount" placeholder="Amount" />
                                                </div>
                                            )}
                                        </Field>
                                        <Field name="category">
                                            {props => (
                                                <div className="form-group col-md-6">
                                                    <label htmlFor="Category">Category</label>
                                                    <select {...props.input} className="form-control">
                                                        {transactionCategories.map(transactionCategory => (
                                                            transactionCategory === "Other" ?
                                                                <option selected type="text" className="form-control" id={transactionCategory} >{transactionCategory}</option>
                                                                :
                                                                <option type="text" className="form-control" id={transactionCategory} >{transactionCategory}</option>
                                                        ))}
                                                    </select>
                                                </div>
                                            )}
                                        </Field>
                                    </div>
                                    <hr />
                                    <div className="row">
                                        {wallet.members.map(member => (
                                            <Field name={"split_amount_" + member.username}>
                                                {props => (
                                                    <div className="form-group col-md-6">
                                                        <label htmlFor={"split_amount_" + member.username}>{member.firstName} {member.lastName}</label>
                                                        <input {...props.input} type={"split_amount_" + member.username} className="form-control" id={"split_amount_" + member.username} placeholder={"Amount of " + member.firstName} />
                                                    </div>
                                                )}
                                            </Field>
                                        ))}
                                    </div>
                                    <hr />
                                    <button type="submit" className="btn btn-primary">Save transaction</button>
                                </form>
                            )}
                        </Form>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default AddTransaction
