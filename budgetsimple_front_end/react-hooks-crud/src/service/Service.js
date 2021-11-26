import axios from "axios";
import { useHistory } from "react-router";
const URL = "http://localhost:8080";

export const logInUser = async (username, password) => {
    try{
        const user = await axios.post(`${URL}/user/login`,
        {
            username,
            password
        });
    }
    catch(err){
        throw new Error(err.message)
    }
}

export const logoutUser = async () => {
    await axios.post(`${URL}/user/logout`);
    
}

export const createUser = async (firstName, lastName, email, address, phoneNum, dateOfBirth, username, role, password) => {
    try{
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
    catch(err){
        throw new Error(err.message)
    }
}

export const updateUser = async (id, firstName, lastName, email, address, phoneNum, dateOfBirth, username, role, password) =>{
    try{
    const editUser =  await axios.put(`${URL}/user/updateUser`,
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
    catch(err){
        throw new Error(err.message)
    }
}