import React, { useState } from "react";
import {Form, Button} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import "../css/FormStyle.css";
import axios from "axios";

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    function goToRegister() {
        navigate("/customer/register");
    }

    function validateForm() {
        return email.length > 0 && password.length > 0;
    }

    function loginUser(credentials) {
        axios
          .post("http://localhost:8080/login", credentials)
          .then((response) => {
              console.info(response.data);
              localStorage.setItem('user', JSON.stringify(response.data));
          })
          .catch((error) => console.error("There was an error!", error));
    }    

    function handleSubmit(event) {
        loginUser({email, password});
        const loggedInUser = localStorage.getItem("user");
        console.log(loggedInUser)
        if (loggedInUser) {
            setEmail("");
            setPassword("");
            const foundUser = JSON.parse(loggedInUser);
            if (foundUser.hasOwnProperty('restaurant') && foundUser.restaurant == null) {
                navigate("/admin/addRestaurant");
            } else if (foundUser.hasOwnProperty('restaurant')) {
                navigate('/admin')
            } else {
                navigate("/customer");
            }
        } else {
            console.log("here without user");
        } 
        event.preventDefault();
    }

    return (
        <div className="FormStyle">
            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="formBasicEmail" className="mb-3">
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                        autoFocus
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    </Form.Group>
                <Form.Group size="lg" className="mb-3" controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>
                <Button variant="primary" block size="lg" type="submit" disabled={!validateForm()}>
                    Login
                </Button>
                <Button variant="primary" block size="lg" onClick={goToRegister}>
                    Register
                </Button>
            </Form>
        </div>
    );
}

export default Login;