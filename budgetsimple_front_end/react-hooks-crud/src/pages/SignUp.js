import React, {Component, useState} from "react";
import logo from "../images/logo.png"
import {Link, useHistory} from "react-router-dom";
import axios from "axios";

const SignUp = () => {
    localStorage.clear();
    const history = useHistory();
    const [firstName,setFirstName] = useState(null);
    const [lastName,setLastName] = useState(null);
    const [email,setEmail] = useState(null);
    const [address,setAddress] = useState(null);
    const [phoneNum,setPhoneNum] = useState(null);
    const [username,setUsername] = useState(null);
    const [password,setPassword] = useState(null);
    const [dateOfBirth,setDateOfBirth] = useState(null);

    function submitHandler(e) {
        e.preventDefault();
        axios.post("http://localhost:8080/user/createUser",
            {
                firstName: firstName,
                lastName: lastName,
                email: email,
                address: address,
                phoneNum: phoneNum,
                dateOfBirth: dateOfBirth,
                username: username,
                position: "USER",
                password: password
            }
        ).then((data) => {
            console.log(data);
            localStorage.setItem(
                "id" , data.data
            )
            history.push(`/user/account/${ data.data }`)
        });
    }

        return (
            <div className="auth-wrapper">
                <div className="auth-inner">
                    <form>
                        <img className="logo" src={logo} alt="logo"/>

                        <div className="form-group form-margin">
                            <label>First name</label>
                            <input onChange = {(fn) => setFirstName(fn.target.value)} type="firstName" className="form-control" placeholder="Enter first name" />
                        </div>

                        <div className="form-group form-margin">
                            <label>Last name</label>
                            <input onChange = {(ln) => setLastName(ln.target.value)} type="lastName" className="form-control" placeholder="Enter last name" />
                        </div>

                        <div className="form-group form-margin">
                            <label>Email</label>
                            <input onChange = {(e) => setEmail(e.target.value)} type="email" className="form-control" placeholder="Enter email" />
                        </div>

                        <div className="form-group form-margin">
                            <label>Address</label>
                            <input onChange = {(a) => setAddress(a.target.value)} type="address" className="form-control" placeholder="Enter address" />
                        </div>

                        <div className="form-group form-margin">
                            <label>Phone number</label>
                            <input onChange = {(pn) => setPhoneNum(pn.target.value)} type="phoneNumber" className="form-control" placeholder="Enter phone number" />
                        </div>

                        <div className="form-group form-margin">
                            <label>Username</label>
                            <input onChange = {(u) => setUsername(u.target.value)} type="username" className="form-control" placeholder="Enter username" />
                        </div>

                        <div className="form-group form-margin">
                            <label>Password</label>
                            <input onChange = {(p) => setPassword(p.target.value)} type="password" className="form-control" placeholder="Enter password" />
                        </div>

                        <div className="form-group form-margin">
                            <label>Date of birth</label>
                            <input onChange = {(db) => setDateOfBirth(db.target.value)} type="dateOfBirth" className="form-control" placeholder="Enter date of birth" />
                        </div>

                        <div className="form-group form-margin">
                            <label>Profile photo</label>
                            <input type="photo" className="form-control" placeholder="Enter photo" />
                        </div>

                        <div className="form-group form-margin">
                            <div className="custom-control custom-checkbox">
                                <input type="checkbox" className="custom-control-input" id="customCheck1" />
                                <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                            </div>
                        </div>

                        <button type="submit" className="btn btn-primary" onClick={submitHandler}>Sign Up</button>
                        <p className="forgot-password">or</p>
                        <p>
                            <Link to={"/sign-in"}>Already have an account?</Link>
                        </p>
                    </form>
                </div>
            </div>
        );
}
export default SignUp;