import React, {useState, useEffect } from "react";
import {Link} from "react-router-dom";
import Navigation from "../components/Navigation";
import profileImage from "../images/profilePhoto.png"
import axios from "axios";
import Field from "../components/Field"

const Account = () =>  {
        const [isLoading,setIsLoading] = useState(true);
        const [user,setUser] = useState();
        const [editable,setEditable] = useState(false);


        useEffect(() => {
            setIsLoading(true);
            axios.get("http://localhost:8080/user/" + localStorage.getItem("id")).
            then((data) => {
                setIsLoading(false);
                const foundUser = data.data;
                setUser(
                    foundUser
                );
            })

        },[])

    console.log("User:");
        console.log(user);
        function editInfoHandler(e) {
            e.preventDefault();
            if(editable){
                setEditable(false);
                console.log(user);
                axios.put("http://localhost:8080/user/updateUser",
                    {
                        "address": user.address,
                        "email": user.email,
                        "firstName": user.firstName,
                        "id": user.id,
                        "lastName": user.lastName,
                        "password": user.password,
                        "phoneNum": user.phoneNum,
                        "position": user.position,
                        "username": user.username,
                        "dateOfBirth": user.dateOfBirth
                    }
                );
            }else{
                setEditable(true);
            }
        }

        if(isLoading){
            return (
                <section>
                    <p>
                        Loading...
                    </p>
                </section>
            )
        }
        else {
            if(!user){
                return null;
            }
            return (
                <div>
                    <Navigation></Navigation>
                    <div className="auth-wrapper">
                        <div className="auth-inner fullLength">

                            <div className="row">
                                <div className="col-md-6">
                                    <div className="card mdb-color lighten-2 text-center z-depth-2">
                                        <div className="card-body">
                                            <div className="d-flex flex-column align-items-center text-center">
                                                <img src={profileImage}
                                                     className="rounded-circle small"/>
                                                <div className="mt-3">
                                                    <h4>{user.firstName} {user.lastName}</h4>
                                                    <Link className="btn btn-primary margin-10">My bonuses</Link>
                                                    <Link to={"/sign-in"} className="btn btn-primary margin-10">Go to my
                                                        wallets</Link>
                                                    <button to={"/sign-in"} className="btn btn-primary margin-10">Change
                                                        profile picture
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="col-md-6">
                                    <div className="card mdb-color lighten-2 text-center z-depth-2">
                                        <div className="card-body">
                                            {/*body*/}
                                            <Field label="First name:" value={`${user.firstName}`} edit={editable} onChange = {(value) => setUser({...user, firstName: value})} />
                                            <hr></hr>
                                            <Field label="First name:" value={`${user.lastName}`} edit={editable} onChange = {(value) => setUser({...user, lastName: value})} />
                                            <hr></hr>
                                            <Field label="Email:" value={user.email} edit={editable} onChange = {(value) => setUser({...user, email: value})}/>
                                            <hr></hr>
                                            <Field label="Address:" value={user.address} edit={editable} onChange = {(value) => setUser({...user, address: value})}/>
                                            <hr></hr>
                                            <Field label="Username:" value={user.username} edit={editable}  onChange = {(value) => setUser({...user, username: value})}/>
                                            <hr></hr>
                                            <Field label="Phone number:" value={user.phoneNum} edit={editable}  onChange = {(value) => setUser({...user, phoneNum: value})}/>
                                            <hr></hr>
                                            <Field label="Birth date:" value={user.dateOfBirth} edit={editable}  onChange = {(value) => setUser({...user, dateOfBirth: value})} />
                                            <hr></hr>
                                            <div className="row">
                                                <button onClick={editInfoHandler} className="btn btn-primary">
                                                    { editable ? 'Save info' : 'Edit info' }
                                                </button>
                                            </div>
                                            <hr></hr>
                                            <div className="row">
                                                <Link to={"/"} className="btn btn-primary">Change password</Link>
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
}
export default Account;