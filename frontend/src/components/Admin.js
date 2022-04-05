import React, { useState } from "react";
import {Route, Routes } from 'react-router-dom';
import AdminHome from './Admin/AdminHome';
import AddRestaurant from './Admin/AddRestaurant';
import AddFood from './Admin/AddFood';

function Admin() {
    return (
        <Routes>
            <Route path="/" element={<AdminHome/>}>
                <Route path="addRestaurant" element={<AddRestaurant/>}></Route>
                <Route path="addFood" element={<AddFood/>}></Route>
            </Route>
        </Routes>
    );
}

export default Admin;