import React, { Component, useState, useEffect } from "react";
import logo from "../images/logo.png"
import { Link, useHistory } from "react-router-dom";
import * as Service from '../service/Service'
import axios from "axios";


const Login = () => {
    const history = useHistory();
    const [username, setUsername] = useState(null);
    const [password, setPassword] = useState(null);
    const [error, setError] = useState([]);
    const [user, setUser] = useState();

    const getLoggedInUser = async () => {
        const user = await axios.get("http://localhost:8080/user/me");
        console.log(user);
        if(user.data != ''){
            Service.logoutUser();
            Service.disconnectWebsocket();
        }
    };
    const logInUser = async () => {
        try {
            await Service.logInUser(username, password);
            Service.connectWebsocket(`/walletEntry/messages/${username}`);
            if (localStorage.getItem("redirect_path") === null) {
                history.push("/user/account");

            } else {
                let redirect_path = localStorage.getItem("redirect_path");
                localStorage.removeItem("redirect_path");
                history.push(redirect_path);
            }

        } catch (err) {
            setError(currentErrors => [...currentErrors, err.message]);
        }
    }

    useEffect(() => {
        getLoggedInUser();
        if (localStorage.getItem("redirect_path") != null) {
            setError(currentErrors => [...currentErrors, "You need to login in order to enter a wallet!"]);
        }
    }, [])

    function submitHandler(e) {
        e.preventDefault();
        setError(oldArray => []);
        if (username === "" || password === "") {
            setError(oldArray => [...oldArray,
                "Please fill all the fields!"
            ]);
        } else {
            logInUser();

        }
    }

    return (

        <div className="auth-inner">
            <div className="card mdb-color lighten-2 text-center z-depth-2">
                <form>
                    <img className="logo" src={logo} alt="logo" />
                    {error.map(error => (
                        <div className="form-group form-margin">
                            <div className="alert alert-warning" role="alert">{error}</div>
                        </div>
                    ))}
                    <div className="form-group form-margin">
                        <label>Username</label>
                        <input type="email" className="form-control" placeholder="Enter username" onChange={(u) => setUsername(u.target.value)} />
                    </div>

                    <div className="form-group form-margin">
                        <label>Password</label>
                        <input type="password" className="form-control" placeholder="Enter password" onChange={(p) => setPassword(p.target.value)} />
                    </div>

                    <div className="form-group form-margin">
                        <div className="custom-control custom-checkbox">
                            <input type="checkbox" className="custom-control-input" id="customCheck1" />
                            <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                        </div>
                    </div>

                    <button type="submit" className="btn btn-primary" onClick={submitHandler}>Sign In</button>
                    <button className="btn btn-google"><a href="#"><img src="https://img.icons8.com/color/16/000000/google-logo.png" /> Sign in with Google</a> </button>
                    <p className="forgot-password text-access">
                        Forgot password?
                    </p>
                    <p className="forgot-password">or</p>
                    <p>
                        <Link to={"/sign-up"}>Don't have an account?</Link>
                    </p>
                </form>
            </div>
        </div>
    );
}
export default Login;