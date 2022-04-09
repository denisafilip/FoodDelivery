import React from "react";
import {Route, Routes } from 'react-router-dom';
import AdminHome from './Admin/AdminHome';
import AddRestaurant from './Admin/AddRestaurant';
import AddFood from './Admin/AddFood';
import ViewMenu from './Admin/ViewMenu';
import ViewOrders from "./Admin/ViewOrders";

function Admin() {
    return (
        <Routes>
            <Route path="/" element={<AdminHome/>}>
                <Route path="addRestaurant" element={<AddRestaurant/>}></Route>
                <Route path="addFood" element={<AddFood/>}></Route>
                <Route path="viewMenu" element={<ViewMenu/>}></Route>
                <Route path="viewOrders" element={<ViewOrders/>}></Route>
            </Route>
        </Routes>
    );
}

export default Admin;