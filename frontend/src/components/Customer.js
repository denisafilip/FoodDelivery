import React from "react";
import {Route, Routes } from 'react-router-dom';
import CustomerHome from './Customer/CustomerHome';
import ViewRestaurants from './Customer/ViewRestaurants';
import ViewAllMenus from "./Customer/ViewAllMenus";
import ViewOrderHistory from "./Customer/ViewOrderHistory";

function Customer() {
    return (
        <Routes>
            <Route path="/" element={<CustomerHome/>}>
                <Route path="viewRestaurants" element={<ViewRestaurants/>}></Route>
                <Route path="viewMenu" element={<ViewAllMenus/>}></Route>
                <Route path="viewOrderHistory" element={<ViewOrderHistory/>}></Route>
            </Route>
        </Routes>
    );
}

export default Customer;