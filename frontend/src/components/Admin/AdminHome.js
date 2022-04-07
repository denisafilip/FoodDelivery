import React, {useState, useEffect} from 'react';
import { Container, Nav, Navbar } from 'react-bootstrap';
import {Outlet} from 'react-router-dom';
import axios from "axios";
 
export default function AdminHome() {

    axios
        .get('http://localhost:8080/admin/addFood')
        .then(response => {
            localStorage.setItem('foodCategories', JSON.stringify(response.data));
        });

    axios
        .get('http://localhost:8080/admin/addRestaurant')
        .then(response => {
            localStorage.setItem('zones', JSON.stringify(response.data));
        });

    return (
        <div>
            <Navbar bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand href="/admin/">Administrator</Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link disabled={JSON.parse(localStorage.getItem("user")).restaurant != null ? "disabled" : ""} href="/admin/addRestaurant" /*onClick={disableAddRestaurant}*/>Add Restaurant</Nav.Link>
                        <Nav.Link disabled={JSON.parse(localStorage.getItem("user")).restaurant == null ? "disabled" : ""} href="/admin/addFood">Add Food</Nav.Link>
                        <Nav.Link disabled={JSON.parse(localStorage.getItem("user")).restaurant == null ? "disabled" : ""} href="/admin/viewMenu">View Menu</Nav.Link>
                        <Nav.Link disabled={JSON.parse(localStorage.getItem("user")).restaurant == null ? "disabled" : ""} href="/admin/viewOrders">View Orders</Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
            <Outlet />
        </div>
    );
}