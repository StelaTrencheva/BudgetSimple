import React from 'react';
import { Navbar,Container,Nav } from 'react-bootstrap'
import logo from "../images/logo.png";
import {NavLink} from "react-router-dom";

const Navigation = () => {
    return (
        <Navbar className="custom" >
            <Container>
                <img className="smallLogo" src={logo} alt="logo"/>
                <Nav>
                    <NavLink to="/">Dashboard</NavLink>
                    <NavLink to="/">Wallets</NavLink>
                    <NavLink to={`/user/account/${localStorage.getItem("id")}`}>Account</NavLink>
                    <NavLink to="/">Contact</NavLink>
                    <NavLink to="/sign-in">Logout</NavLink>
                </Nav>
            </Container>
        </Navbar>
    );
}
export default Navigation
