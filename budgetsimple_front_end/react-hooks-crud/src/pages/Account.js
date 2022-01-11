import React, { useState, useEffect } from "react";
import { Link, useHistory } from "react-router-dom";
import profileImage from "../images/profilePhoto.png"
import axios from "axios";
import Field from "../components/Field"
import Navigation from '../components/Navigation';
import * as Service from '../service/Service'

const Account = () => {
    const history = useHistory();
    const [user, setUser] = useState();
    const [editable, setEditable] = useState(false);
    const [error, setError] = useState([]);
    const [initialUsername, setUsername] = useState();

    const getLoggedInUser = async () => {
        const user = await axios.get("http://localhost:8080/user/me");
        setUser(user.data);
        setUsername(user.data.username);
        Service.connectWebsocket(`/walletEntry/messages/${user.data.username}`);
    };

    const updateUser = async () => {
        try {
            await Service.updateUser(user.id, user.firstName, user.lastName, user.email, user.address, user.phoneNum, user.dateOfBirth, user.username, user.role, user.password)
        }
        catch (err) {
            setError(oldArray => [...oldArray,
            err.message
            ]);
        }
    }

    useEffect(() => {
        getLoggedInUser();
    }, [])

    function editInfoHandler(e) {

        e.preventDefault();
        setError(oldArray => []);
        if (user.firstName === "" || user.lastName === "" || user.email === "" || user.username === "" || user.address === "" || user.phoneNum === "" || user.dateOfBirth === "") {
            setError(oldArray => [...oldArray,
                "Please fill all the fields!"
            ]);
        } else {
            if (editable && initialUsername != user.username) {
                setEditable(false);
                updateUser();
                Service.logoutUser();
                history.push("/sign-in");
            } else if (editable) {
                setEditable(false);
                updateUser();
            }
            else {
                setEditable(true);
            }
        }
    }


    if (!user) {
        return null;
    }
    return (
        <div>
            <Navigation />
            <div className="auth-wrapper">
                <div className="auth-inner fullLength">
                    <div className="row">
                        <div className="col-md-6">
                            <div className="card mdb-color lighten-2 text-center z-depth-2">
                                <div className="card-body">
                                    <div className="d-flex flex-column align-items-center text-center">
                                        <img src={profileImage}
                                            className="rounded-circle small" />
                                        <div className="row">
                                            <h4>{user.firstName} {user.lastName}</h4>
                                            {user.role === "USER" ?
                                                <Link to={"/user/rateUs"} className="btn btn-primary margin-10">Rate the application
                                                </Link> : null
                                            }
                                            <Link to={"/user/wallets"} className="btn btn-primary margin-10">Go to my wallets
                                            </Link>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-6">
                            <div className="card mdb-color lighten-2 text-center z-depth-2">
                                <div className="card-body">
                                    <div className="row">
                                        {error.map(error => (
                                            <div className="form-group form-margin">
                                                <div className="alert alert-warning" role="alert">{error}</div>
                                            </div>
                                        ))}
                                    </div>
                                    <Field label="First name:" value={`${user.firstName}`} edit={editable} onChange={(value) => setUser({ ...user, firstName: value })} />
                                    <hr></hr>
                                    <Field label="First name:" value={`${user.lastName}`} edit={editable} onChange={(value) => setUser({ ...user, lastName: value })} />
                                    <hr></hr>
                                    <Field label="Email:" value={user.email} edit={editable} onChange={(value) => setUser({ ...user, email: value })} />
                                    <hr></hr>
                                    <Field label="Address:" value={user.address} edit={editable} onChange={(value) => setUser({ ...user, address: value })} />
                                    <hr></hr>
                                    <Field label="Username:" value={user.username} edit={editable} onChange={(value) => setUser({ ...user, username: value })} />
                                    <hr></hr>
                                    <Field label="Phone number:" value={user.phoneNum} edit={editable} onChange={(value) => setUser({ ...user, phoneNum: value })} />
                                    <hr></hr>
                                    <Field label="Birth date:" value={user.dateOfBirth} edit={editable} onChange={(value) => setUser({ ...user, dateOfBirth: value })} />
                                    <hr></hr>
                                    <div className="row">
                                        <button onClick={editInfoHandler} className="btn btn-primary">
                                            {editable ? 'Save info' : 'Edit info'}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    );

}
export default Account;