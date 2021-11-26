import React, { Component } from "react";
import logo from "../images/logo.png"
import {Link, useHistory} from "react-router-dom";
import {useState} from "react";
import * as Service from '../service/Service'

const Login = () => {
            const history = useHistory();
            const [username,setUsername] = useState(null);
            const [password,setPassword] = useState(null);
            const [error,setError] = useState([]);
            const logInUser = async () => {
                try {
                    await Service.logInUser(username, password);
                    history.push("/user/account");
                } catch (err) {
                    setError(currentErrors => [...currentErrors, err.message]);
                }
            }

            function submitHandler(e) {
                e.preventDefault();
                setError(oldArray => []);
                if(username === "" || password === ""){
                    setError(oldArray => [...oldArray, 
                        "Please fill all the fields!"
                     ]);
                }else{
                logInUser();
            }}

        return (
            <div className="auth-wrapper">
                <div className="auth-inner">
            <form>
                <img className="logo" src={logo} alt="logo"/>
                {error.map(error => (
                    <div className="form-group form-margin">
                    <div className="alert alert-warning" role="alert">{error}</div>
                    </div>
                ))}
                <div className="form-group form-margin">
                    <label>Username</label>
                    <input type="email" className="form-control" placeholder="Enter username" onChange = {(u) => setUsername(u.target.value)}/>
                </div>

                <div className="form-group form-margin">
                    <label>Password</label>
                    <input type="password" className="form-control" placeholder="Enter password" onChange = {(p) => setPassword(p.target.value)}/>
                </div>

                <div className="form-group form-margin">
                    <div className="custom-control custom-checkbox">
                        <input type="checkbox" className="custom-control-input" id="customCheck1" />
                        <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                    </div>
                </div>

                <button type="submit" className="btn btn-primary" onClick={submitHandler}>Sign In</button>
                <button className="btn btn-google"><a href="#"><img src="https://img.icons8.com/color/16/000000/google-logo.png"/> Sign in with Google</a> </button>
                <p className="forgot-password text-right">
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