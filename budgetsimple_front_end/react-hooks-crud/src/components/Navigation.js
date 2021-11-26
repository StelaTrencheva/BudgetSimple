import React from 'react';
import { Navbar,Container,Nav } from 'react-bootstrap'
import logo from "../images/logo.png";
import {NavLink} from "react-router-dom";
import * as Service from '../service/Service'


const Navigation = () => {
    return (
        <Navbar className="custom" expand="lg">
            <Container>
                <img className="smallLogo" src={logo} alt="logo"/>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="ml-auto">
                        <NavLink to="/">Dashboard</NavLink>
                        <NavLink to="/">Wallets</NavLink>
                        <NavLink to={`/user/account`}>Account</NavLink>
                        <NavLink to="/">Contact</NavLink>
                        <NavLink to="/" onClick = {Service.logoutUser}>Logout</NavLink>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}
export default Navigation