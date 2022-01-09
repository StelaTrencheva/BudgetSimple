import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Switch, Route, Redirect } from "react-router-dom"
import './index.css'
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import Account from "./pages/Account";
import Wallets from "./pages/Wallets";
import AddWallet from './pages/AddWallet';

import Wallet from './pages/Wallet';
import Transaction from './pages/Transaction';
import Submission from './pages/Submission';
import Rate from './pages/Rate';
import AddSurvey from './pages/AddSurvey.js';
import AllRatings from './pages/AllRatings';
import EnterWallet from './pages/EnterWallet';
import axios from "axios";
axios.defaults.withCredentials = true

ReactDOM.render(
  <BrowserRouter>
    <div className="App">
          <Switch>
          
            <Route exact path="/sign-in" component={Login} />
            <Route exact path="/sign-up" component={SignUp} />
            <Route exact path="/user/account" component={Account} />
            <Route exact path="/user/wallets" component={Wallets} />
            <Route exact path="/user/addWallet" component={AddWallet} />
            <Route exact path="/submissions/:id" component={Submission} />
            <Route exact path="/user/wallets/:id" component={Wallet} />
            <Route exact path="/user/wallets/code/:id" component={EnterWallet} />
            <Route exact path="/addSurvey" component={AddSurvey} />
            <Route exact path="/user/rateUs" component={Rate} />
            <Route exact path="/allRatings" component={AllRatings} />
            <Route exact path="/user/wallets/:walletId/transaction/:transactionId" component={Transaction} />
            <Route path="*">
              <Redirect to="/sign-in" />
            </Route>
          </Switch>
        </div>
  </BrowserRouter>,
  document.getElementById('root')
);