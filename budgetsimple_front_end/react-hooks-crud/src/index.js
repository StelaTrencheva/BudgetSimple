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
import EnterWallet from './pages/EnterWallet';
import axios from "axios";
import Navigation from './components/Navigation';
axios.defaults.withCredentials = true

ReactDOM.render(
  <BrowserRouter>
    <div className="App">
      <Navigation />
      <div>
        <div className="auth-wrapper">
          <div className="auth-inner fullLength">
            <div className="col-md-12">
              <div className="card mdb-color lighten-2 text-center z-depth-2">
                <div className="card-body">
                  <Switch>
                    <Route exact path="/sign-in" component={Login} />
                    <Route exact path="/sign-up" component={SignUp} />
                    <Route exact path="/user/account" component={Account} />
                    <Route exact path="/user/wallets" component={Wallets} />
                    <Route exact path="/user/addWallet" component={AddWallet} />
                    <Route exact path="/user/wallets/:id" component={Wallet} />
                    <Route exact path="/user/wallets/code/:id" component={EnterWallet} />
                    <Route exact path="/user/wallets/:walletId/transaction/:transactionId" component={Transaction} />
                    <Route path="*">
                      <Redirect to="/sign-in" />
                    </Route>
                  </Switch>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </BrowserRouter>,
  document.getElementById('root')
);