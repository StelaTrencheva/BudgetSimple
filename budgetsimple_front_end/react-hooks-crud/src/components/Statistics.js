import * as Service from '../service/Service'
import { useState, useEffect } from "react";
import axios from "axios";
import { Link, useHistory } from "react-router-dom";
import React, { PureComponent } from 'react';
import { BarChart, Bar, PieChart, Pie, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

function getWalletTotalSpent(wallet) {
  let totalSpent = 0;
  wallet.transactions.map(transaction => totalSpent += transaction.amount);
  return totalSpent;
}
const Statistics = (props) => {
  const history = useHistory();
  const [maxYValueMember, setMaxYValueMember] = useState(0);
  const [maxYValueCategory, setMaxYValueCategory] = useState(0);
  const [totalSpendPerMember, setTotalSpendPerMember] = useState([]);
  const [totalSpendPerCategory, setTotalSpendPerCategory] = useState([]);
  const [error, setError] = useState([]);
  const [user, setUser] = useState();
  const [wallet, setWallet] = useState();

  const getLoggedInUser = async () => {
    const user = await axios.get("http://localhost:8080/user/me");
    setUser(user.data);
    Service.connectWebsocket(`/walletEntry/messages/${user.data.username}`);

  };
  const getCurrentWallet = async () => {
    const wallet = await Service.getWalletById(props.walletId);
    setWallet(wallet);
  }
  const getSpendingPerMember = async () => {
    try {
      const data = await Service.getSpendingPerMember(props.walletId, user);
      let spending = [];
      Object.keys(data).map(key => {
        var value = data[key];
        if (value > maxYValueMember) {
          setMaxYValueMember(Number(value.toFixed(0)));
        }
        spending.push(
          {
            name: key, spending: value
          });
      })
      setTotalSpendPerMember(spending);
    }
    catch (err) {
      setError(oldArray => [...oldArray,
      err.message
      ]);
    }
  }
  const getSpendingPerCategory = async () => {
    try {
      const data = await Service.getSpendingPerCategory(props.walletId, user);
      let spending = [];
      Object.keys(data).map(key => {
        var value = data[key];
        if (value > maxYValueCategory) {
          setMaxYValueCategory(Number(value.toFixed(0)));
        }
        spending.push(
          {
            name: key, value: value
          });
      })
      setTotalSpendPerCategory(spending);
    }
    catch (err) {
      setError(oldArray => [...oldArray,
      err.message
      ]);
    }
  }
  useEffect(() => {
    getLoggedInUser();
    getCurrentWallet();
    getSpendingPerMember();
    getSpendingPerCategory();


  }, [])

  if (!user || !wallet) {
    return null;
  }
  return (

    <div className="card mdb-color lighten-2 text-center z-depth-2">
      <div className="card border">
        <div className="card-body flex">
          <div className="row wallet-row">
            <div className="col-lg-12">
              <hr />{getWalletTotalSpent(wallet) > wallet.budget ?
                (
                  <h2 className="text-danger">Spent: {getWalletTotalSpent(wallet)} / Budget: {wallet.budget}</h2>
                )
                :
                (
                  <h2 className="text-success">Spent: {getWalletTotalSpent(wallet)} / Budget: {wallet.budget}</h2>
                )
              }
            </div>
          </div>
          <div className="row wallet-row">
            <div className="col-lg-12">
              <hr />
              <h6>Total spending in wallet per member:</h6>
              <ResponsiveContainer className="container" width="99%" aspect={3}>
                <BarChart
                  data={totalSpendPerMember}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" />
                  <YAxis type="number" domain={[0, maxYValueMember]} />
                  <Tooltip />
                  <Legend />
                  <Bar dataKey="spending" fill="#076B8B" />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </div>
          <div className="row wallet-row">
            <div className="col-lg-12">
              <hr />
              <h6>Total spending in wallet per category:</h6>
              <ResponsiveContainer className="container" width="99%" aspect={3}>
                <PieChart>
                  <Pie
                    dataKey="value"
                    isAnimationActive={true}
                    data={totalSpendPerCategory}
                    outerRadius={180}
                    fill="#8884d8"
                    label
                  />
                  <Tooltip />
                </PieChart>
              </ResponsiveContainer>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
export default Statistics

