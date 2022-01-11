import React from 'react';
import { Navbar, Container, Nav } from 'react-bootstrap'
import logo from "../images/logo.png";
import { NavLink } from "react-router-dom";
import * as Service from '../service/Service'
import { useState, useEffect } from "react";
import axios from "axios";


const Navigation = () => {
    const [user, setUser] = useState();

    const getLoggedInUser = async () => {
        const user = await axios.get("http://localhost:8080/user/me");
        setUser(user.data);
    };

    useEffect(() => {
        getLoggedInUser();
    }, [])

    if (!user) {
        return null;
    }
    return (
        <Navbar className="custom" expand="lg">
            <Container>
                <img className="smallLogo" src={logo} alt="logo" />
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="ml-auto">
                        <NavLink to={`/user/wallets`}>Wallets</NavLink>
                        <NavLink to={`/user/account`}>Account</NavLink>
                        {
                            user.role === "USER" ? (
                                <NavLink to={`/user/rateUs`}>Rate us</NavLink>
                            )
                                :
                                    (
                                        <NavLink to={`/allRatings`}>All ratings</NavLink>
                                    )
                        }
                        <NavLink to="/sign-in">Logout</NavLink>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}
export default Navigation