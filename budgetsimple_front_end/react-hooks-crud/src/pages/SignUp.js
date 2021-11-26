import React, {Component, useState} from "react";
import logo from "../images/logo.png"
import {Link, useHistory} from "react-router-dom";
import * as Service from '../service/Service'

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
    
    const [error,setError] = useState([]);

    const logInUser = async () => {
        try {
            await Service.logInUser(username, password);
        } catch (err) {
            setError(currentErrors => [...currentErrors, err]);
        }
    }
       const createUser = async () => {
        try{
            await Service.createUser(firstName, lastName, email, address, phoneNum, dateOfBirth, username, "USER", password)
            logInUser(username, password);
        }
        catch(err){
            setError(oldArray => [...oldArray,
                err.message
            ]);
            }
        }
       

    function submitHandler(e) {
        e.preventDefault();
        setError(oldArray => []);
        if(firstName === null || lastName === null || email === null || address === null || phoneNum === null || username === null || password === null)
            {setError(oldArray => [...oldArray, 
               "Please fill all the fields!"
            ]);
        }else{
            createUser();
        }
    }

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
                        <button className="btn btn-google"><a href="#"><img src="https://img.icons8.com/color/16/000000/google-logo.png"/> Sign up with Google</a> </button>
                
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
