import React, {useState, useEffect} from 'react';
import { Container, Nav, Navbar, Card } from 'react-bootstrap';
import { useNavigate } from "react-router-dom";
import {Outlet} from 'react-router-dom';
import axios from "axios";
import AuthService from '../AuthService';
import authHeader from '../AuthHeader';
 
export default function AdminHome() {

    axios
        .get('http://localhost:8080/getFoodCategories', {headers: authHeader()})
        .then(response => {
            localStorage.setItem('foodCategories', JSON.stringify(response.data));
        });

    useEffect(() => {
    axios
        .get("http://localhost:8080/admin/get", {
            headers: authHeader(),
            params: {
                adminEmail: AuthService.getJWT().email
            }
        })
        .then((response) => {
              localStorage.setItem('user', JSON.stringify(response.data));
              console.log(response.data);
        })
        .catch((error) => {
            console.error("There was an error when getting the current admin!", error)
            navigate("/");

        } );
    }, []);

    const navigate = useNavigate();

    function logOut() {
        AuthService.logout();
    }

    return (
        <div>
            <Navbar bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand href="/admin/">Administrator</Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link disabled={JSON.parse(localStorage.getItem("jwt")).hasRestaurant == true ? "disabled" : ""} href="/admin/addRestaurant" /*onClick={disableAddRestaurant}*/>Add Restaurant</Nav.Link>
                        <Nav.Link disabled={JSON.parse(localStorage.getItem("jwt")).hasRestaurant == false ? "disabled" : ""} href="/admin/addFood">Add Food</Nav.Link>
                        <Nav.Link disabled={JSON.parse(localStorage.getItem("jwt")).hasRestaurant == false ? "disabled" : ""} href="/admin/viewMenu">View Menu</Nav.Link>
                        <Nav.Link disabled={JSON.parse(localStorage.getItem("jwt")).hasRestaurant == false ? "disabled" : ""} href="/admin/viewOrders">View Orders</Nav.Link>
                        <Nav.Link href="/" onClick={logOut}>Log Out</Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
            <Outlet />
        </div>
    );
}