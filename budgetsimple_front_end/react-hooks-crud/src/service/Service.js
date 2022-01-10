import axios from "axios";

import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

const URL = "http://localhost:8080";
let socket = null;


export const logInUser = async (username, password) => {
    try {
        const user = await axios.post(`${URL}/user/login`,
            {
                username,
                password
            });
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const logoutUser = async () => {
    await axios.post(`${URL}/user/logout`);

}

export const createUser = async (firstName, lastName, email, address, phoneNum, dateOfBirth, username, role, password) => {
    try {
        const user = await axios.post(`${URL}/user/createUser`,
            {
                firstName,
                lastName,
                email,
                address,
                phoneNum,
                dateOfBirth,
                username,
                role,
                password
            });
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const updateUser = async (id, firstName, lastName, email, address, phoneNum, dateOfBirth, username, role, password) => {
    try {
        const editUser = await axios.put(`${URL}/user/updateUser`,
            {
                id,
                firstName,
                lastName,
                email,
                address,
                phoneNum,
                dateOfBirth,
                username,
                role,
                password
            });
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const createWallet = async (creator, budget, title, description, currency) => {
    try {
        const wallet = await axios.post(`${URL}/wallet/create`,
            {
                creator,
                budget,
                title,
                description,
                currency

            });
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const createTransaction = async (walletId, creator, amount, title, description, category, memberAmountDTO) => {
    try {
        var date = Date;
        const transaction = await axios.post(`${URL}/wallet/${walletId}/transaction/create`,
            {
                walletId,
                creator,
                amount,
                title,
                description,
                category,
                memberAmountDTO

            });
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const addWalletEntryRequest = async (walletId, user) => {
    try {
        const transaction = await axios.post(`${URL}/wallet/${walletId}/addWalletEntryRequest`,
            {
                "address": user.address,
                "dateOfBirth": user.dateOfBirth,
                "email": user.email,
                "firstName": user.firstName,
                "id": user.id,
                "lastName": user.lastName,
                "password": user.password,
                "phoneNum": user.phoneNum,
                "role": user.role,
                "username": user.username
            }

        );
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const acceptEntryRequest = async (walletId, request) => {
    try {
        const transaction = await axios.post(`${URL}/wallet/${walletId}/acceptWalletEntryRequest`,
            {
                "id": request.id,
                "user": request.user

            }
        );
    }
    catch (err) {
        throw new Error(err.message)
    }
}
export const rejectEntryRequest = async (walletId, request) => {
    try {
        const transaction = await axios.post(`${URL}/wallet/${walletId}/rejectWalletEntryRequest`,
            {
                "id": request.id,
                "user": request.user
            }
        );
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const getWalletById = async (walletId) => {
    try {
        const wallet = await axios.get(`${URL}/wallet/${walletId}`);
        return wallet.data;
    }
    catch (err) {
        throw new Error(err.message)
    }
}
export const getWalletByCode = async (code) => {
    try {
        const wallet = await axios.get(`${URL}/wallet/code/${code}`);
        return wallet.data;
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const getWalletQrCode = async (walletId) => {
    try {
        const walletQrCode = await axios.get(`${URL}/wallet/qrCode/${walletId}`);
        return `data:image/png;base64,${walletQrCode.data}`
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const getTransactionsByWalletId = async (walletId) => {
    try {
        const transactions = await axios.get(`${URL}/wallet/${walletId}/transaction/getAll`);
        return transactions.data;
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const deleteWallet = async (walletId) => {
    try {
        const result = await axios.delete(`${URL}/wallet/${walletId}/delete`);
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const leaveWallet = async (walletId, user) => {
    try {
        const result = await axios.post(`${URL}/wallet/${walletId}/removeWalletMember`,{
            
            "address": user.address,
            "dateOfBirth": user.dateOfBirth,
            "email": user.email,
            "firstName": user.firstName,
            "id": user.id,
            "lastName": user.lastName,
            "password": user.password,
            "phoneNum": user.phoneNum,
            "role": user.role,
            "username": user.username
        }
        );
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const deleteTransaction = async (walletId,transactionId) => {
    try {
         await axios.delete(`${URL}/wallet/${walletId}/transaction/${transactionId}/delete`);
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const changeWalletBudget = async (walletId, budget) => {
    try {
        await axios.put(`${URL}/wallet/${walletId}?budget=${budget}`);
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const getTransactionCategories = async () => {
    try {
        const transactionCategories = await axios.get(`${URL}/wallet/transaction/categories`);
        return transactionCategories.data;
    }
    catch (err) {
        throw new Error(err.message)
    }
}
export const getTransactionById = async (transactionId) => {
    try {
        const transaction = await axios.get(`${URL}/wallet/transaction/${transactionId}`);
        return transaction.data;
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const getSpendingPerMember = async (walletId) => {
    try {
        const data = await axios.get(`${URL}/wallet/${walletId}/spendingPerMember`);
        return data.data;
        }
    catch (err) {
        throw new Error(err.message)
    }
}

export const getSpendingPerCategory = async (walletId) => {
    try {
        const data = await axios.get(`${URL}/wallet/${walletId}/spendingPerCategory`);
        return data.data;
        }
    catch (err) {
        throw new Error(err.message)
    }
}
export const getTotalSpendInAccount = async (user) => {
    try {
        const data = await axios.get(`${URL}/wallet/totalSpent`,
        user
        );
        return data.data;
        }
    catch (err) {
        throw new Error(err.message)
    }
}

export const getSurveyByTitle = async (title) => {
    try {
        const survey = await axios.get(`${URL}/survey/getByTitle/${title}`);
        return survey.data;
        }
    catch (err) {
        throw new Error(err.message)
    }
}
export const getAllSubmissions = async () => {
    try {
        const submission = await axios.get(`${URL}/survey/submission/getAll`);
        return submission.data;
        }
    catch (err) {
        throw new Error(err.message)
    }
}
export const getCurrentSubmission = async (submissionId) => {
    try {
        const submission = await axios.get(`${URL}/survey/submission/${submissionId}`);
        return submission.data;
        }
    catch (err) {
        throw new Error(err.message)
    }
}
export const createSurvey = async (title, questions) => {
    try{
        const survey = await axios.post(`${URL}/survey/create`,
        {
            title,
            questions
        })
    }catch(err){
        throw new Error(err.message)
    }
}
export const createSurveySubmission = async (rating, surveyDTO, surveyAnswers) => {
    try{
        const submission = await axios.post(`${URL}/survey/submission/create`,
        {
            rating,
            surveyDTO,
            surveyAnswers
        })
    }catch(err){
        throw new Error(err.message)
    }
}

export const connectWebsocket = function (subscribtion) {
    const ENDPOINT = `${URL}/websocket`;
    if (socket != null) {
        socket.close();
        socket = null;
    }
    socket = SockJS(ENDPOINT);
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
      stompClient.subscribe(subscribtion, (data) => {
        alert(JSON.parse(data.body).content);
      });
    });
}
export const disconnectWebsocket = function () {
    if (socket != null) {
        socket.close();
        socket = null;
    }
}

