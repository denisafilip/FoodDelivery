import { render } from '@testing-library/react';
import React, {useState, useEffect} from 'react';
import { Container, Nav, Navbar, Card, NavLink } from 'react-bootstrap';
import {Outlet} from 'react-router-dom';
 
export default function AdminHome() {
    const [admin, setAdmin] = useState();
    let foundUser = undefined;

    useEffect(() => {
        const loggedInUser = localStorage.getItem("user");
        if (loggedInUser) {
            foundUser = JSON.parse(loggedInUser);
           
            setAdmin(foundUser);
        }
        
    }, []);

    function disableAddRestaurant(e) {
        console.log(foundUser);
        if (foundUser.restaurant != null) e.preventDefault();
    }
    
    return (
        <div>
            <Navbar bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand href="/admin/">Administrator</Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link href="/admin/addRestaurant" onClick={disableAddRestaurant}>Add Restaurant</Nav.Link>
                        <Nav.Link href="/admin/viewOrders">View Orders</Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
            <Outlet />
        </div>
    );
}