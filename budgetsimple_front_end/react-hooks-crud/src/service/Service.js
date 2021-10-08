import http from "../http-common";
import React  from "react";

const GetUserById = () => {
    return http.get("/user/1");
};

export default GetUserById()