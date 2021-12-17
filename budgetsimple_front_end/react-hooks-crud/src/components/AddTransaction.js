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
        props.changeViewStatus(false);
        props.showAddButton(false);

        setError(oldArray => []);
        if (formValues.title == null || formValues.description == null || formValues.amount == null) {
            setError(oldArray => [...oldArray,
                "Please fill all the fields!"
            ]);
        } else {
            try {
                var dateOfCreation = new Date();
                var memberAmounts = [];
                var amountPerMember = formValues.amount / wallet.members.length;
                console.log(amountPerMember);
                wallet.members.map(member => (
                    memberAmounts.push({
                        "user" : member,
                        "amount" : amountPerMember
                    })
                ));
                await Service.createTransaction(wallet.id, user, formValues.amount, formValues.title, formValues.description, formValues.category, dateOfCreation, memberAmounts);
                history.go(0);
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
                    <hr></hr>
                    <button type="submit" className="btn btn-primary">Save transaction</button>
                </form>
            )}
        </Form>
    )
}
export default AddTransaction
