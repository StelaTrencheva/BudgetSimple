import React, { useState, useEffect } from "react";
import { Link, useHistory } from "react-router-dom";
import Navigation from "../components/Navigation";
import { Field, Form } from 'react-final-form'
import * as Service from "../service/Service"
import axios from "axios";

const AddWallet = () => {
    const history = useHistory();
    const [user, setUser] = useState();
    const [error, setError] = useState([]);


    const getLoggedInUser = async () => {
        try {
            const user = await axios.get("http://localhost:8080/user/me");
            setUser(user.data);
        } catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    };

    useEffect(() => {
        getLoggedInUser();
    }, [])

    async function saveWalletHandler(formValues) {
        setError(oldArray => []);
        if (formValues.title == null || formValues.description == null || formValues.currency == null || formValues.budget == null) {
            setError(oldArray => [...oldArray,
                "Please fill all the fields!"
            ]);
        } else {
            try {
                var dateOfCreation = new Date();
                await Service.createWallet(user, formValues.budget, formValues.title, formValues.description, formValues.currency, dateOfCreation)
                history.push("/user/wallets");
            }
            catch (err) {
                setError(oldArray => [...oldArray,
                err.message
                ]);
            }
        }
    }

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
            <div className="col-md-12">
                <div className="card mdb-color lighten-2 text-center z-depth-2">
                    <div className="card-body">
                        <Form
                            onSubmit={saveWalletHandler}
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
                                        <Field name="budget">
                                            {props => (
                                                <div className="form-group col-md-6">
                                                    <label htmlFor="budget">Budget</label>
                                                    <input {...props.input} type="budget" className="form-control" id="budget" placeholder="Budget" />
                                                </div>
                                            )}
                                        </Field>
                                        <Field name="currency">
                                            {props => (
                                                <div className="form-group col-md-6">
                                                    <label htmlFor="Currency">Currency</label>
                                                    <input {...props.input} type="currency" className="form-control" id="currency" placeholder="Currency" />
                                                </div>
                                            )}
                                        </Field>
                                    </div>
                                    <hr></hr>
                                    <button type="submit" className="btn btn-primary">Save wallet</button>
                                </form>
                            )}
                        </Form>
                    </div>
                </div>
            </div>
        </div>


    );
}

export default AddWallet;