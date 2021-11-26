import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Switch, Route, Redirect } from "react-router-dom"
import './index.css'
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import Account from "./pages/Account";
import axios from "axios";
axios.defaults.withCredentials = true

ReactDOM.render(
  <BrowserRouter>
      <div className="App">
        <Switch>
            <Route exact path="/sign-in" component={Login} />
            <Route exact path="/sign-up" component={SignUp} />
            <Route exact path="/user/account" component={Account} />
            <Route path="*">
                <Redirect to="/sign-in" />
            </Route>
        </Switch>
      </div>
  </BrowserRouter>,
  document.getElementById('root')
);