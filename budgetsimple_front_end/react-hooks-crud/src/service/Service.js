import axios from "axios";
import { useHistory } from "react-router";
const URL = "http://localhost:8080";

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

export const createWallet = async (creator, budget, title, description, currency, dateOfCreation) => {
    try {
        const wallet = await axios.post(`${URL}/wallet/create`,
            {
                creator,
                budget,
                title,
                description,
                currency,
                dateOfCreation

            });
    }
    catch (err) {
        throw new Error(err.message)
    }
}

export const createTransaction = async (walletId, creator, amount, title, description, category, dateOfCreation, memberAmountDTO) => {
    try {
        const transaction = await axios.post(`${URL}/wallet/${walletId}/transaction/create`,
            {
                walletId,
                creator,
                amount,
                title,
                description,
                category,
                dateOfCreation,
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
                request
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
        return walletQrCode;
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

