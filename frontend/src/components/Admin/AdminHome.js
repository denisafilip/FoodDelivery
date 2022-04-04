import React from 'react';
import { Container, Nav, Navbar, Card, NavLink } from 'react-bootstrap';
import {Outlet} from 'react-router-dom';
 
export default function AdminHome() {
    return (
    <div>
        <Navbar bg="dark" variant="dark">
            <Container>
                <Navbar.Brand href="/admin/">Administrator</Navbar.Brand>
                <Nav className="me-auto">
                    <Nav.Link href="/admin/addRestaurant">Add Restaurant</Nav.Link>
                    <Nav.Link href="/admin/viewOrders">View Orders</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
        <Outlet />
    </div>
    );
}