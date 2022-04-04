import React, { useState } from "react";
import { BrowserRouter as Router, HashRouter, Route, Routes } from 'react-router-dom';
import {Form, Button} from "react-bootstrap";
import { ThemeConsumer } from "react-bootstrap/esm/ThemeProvider";
import { useNavigate } from "react-router-dom";
import "../css/Login.css";
import axios from "axios";
import AdminHome from './Admin/AdminHome';
import AddRestaurant from './Admin/AddRestaurant';

function Admin(props) {
    return (
        <Routes>
            <Route path="/" element={<AdminHome/>}>
                <Route path="addRestaurant" element={<AddRestaurant/>}></Route>
            </Route>
        </Routes>
    );
}

export default Admin;