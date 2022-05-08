import React, {useState, useEffect} from 'react';
import { Container, Nav, Navbar, Button } from 'react-bootstrap';
import { useNavigate } from "react-router-dom";
import {Outlet} from 'react-router-dom';
import AuthService from '../AuthService';
import axios from "axios";
import authHeader from "../AuthHeader";
 
export default function CustomerHome() {
    axios
        .get('http://localhost:8080/getRestaurants', {headers: authHeader()})
        .then(response => {
            localStorage.setItem('restaurants', JSON.stringify(response.data));
        });
    
    useEffect(() => {
        axios
            .get("http://localhost:8080/customer/get", {
                headers: authHeader(),
                params: {
                    customerEmail: AuthService.getJWT().email
                }
            })
            .then((response) => {
                localStorage.setItem('user', JSON.stringify(response.data));
                console.log(response.data);
            })
            .catch((error) => {
                console.error("There was an error when getting the current user!", error)
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
                    <Navbar.Brand href="/customer/">Customer</Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link href="/customer/viewRestaurants">View Restaurants</Nav.Link>
                        <Nav.Link href="/customer/viewMenu">View Menu</Nav.Link>
                        <Nav.Link href="/customer/viewOrderHistory">View Order History</Nav.Link>
                        <Nav.Link href="/" onClick={logOut}>Log Out</Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
            <Outlet />
        </div>
    );
}