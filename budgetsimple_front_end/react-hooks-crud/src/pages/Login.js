import React, { Component } from "react";
import logo from "../images/logo.png"
import {Link, useHistory} from "react-router-dom";
import {useState} from "react";
import axios from "axios";

const Login = () => {
            localStorage.clear();
            const history = useHistory();
            const [username,setUsername] = useState(null);
            const [password,setPassword] = useState(null);

            function submitHandler(e) {
                e.preventDefault();
                axios.post("http://localhost:8080/user/login",
                    {
                        username: username,
                        password: password
                    }
                ).then(res => {
                    // set token in local storage
                    localStorage.setItem(
                        "token" , res.headers.authorization
                    )
                    // make request to /users/me which is going to return the logged in user
                        axios.get("http://localhost:8080/user/me",
                            {
                                headers:{
                                    'Authorization' : `${localStorage.getItem("token")}`
                                }
                            }
                            ).
                        then(data => {
                            localStorage.setItem(
                                "user" , JSON.stringify(data.data)
                            )
                        }, (error) => {
                            console.log(error);
                        });

                    history.push(`/user/account`)
                }, (error) => {
                    console.log(error);
                });
            }

        return (
            <div className="auth-wrapper">
                <div className="auth-inner">
            <form>
                <img className="logo" src={logo} alt="logo"/>


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