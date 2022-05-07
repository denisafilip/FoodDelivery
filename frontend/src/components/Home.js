import React from 'react';
import '../css/Home.css';
import { Container, Nav, Navbar, Card, NavLink } from 'react-bootstrap';
import {Outlet} from 'react-router-dom';
import axios from "axios";
 
export default function Home () {

    axios
        .get('http://localhost:8080/getZones')
        .then(response => {
            localStorage.setItem('zones', JSON.stringify(response.data));
        });

    return (
    <div>
        <Navbar bg="dark" variant="dark">
            <Container>
                <Navbar.Brand href="/">Food Delivery</Navbar.Brand>
                <Nav className="me-auto">
                    <Nav.Link href="/customer/register">Register</Nav.Link>
                    <Nav.Link href="/login">Log In</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
        <Card className="Home">
            <Card.Body>Welcome to this amazing delivery app!</Card.Body>
        </Card>
        <Outlet />
    </div>
    );
}