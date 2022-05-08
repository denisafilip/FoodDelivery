import React, { useState } from "react";
import {Form, Button} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import AuthService from "./AuthService";
import "../css/FormStyle.css";
import axios from "axios";

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();

    function goToRegister() {
        navigate("/customer/register");
    }

    function validateForm() {
        return email.length > 0 && password.length > 0;
    }

    const loginUser = async(credentials) => {
        console.log(credentials)
        await AuthService.login(credentials).then(
            (user) => {
                console.log(user)
                if (user.role == "ROLE_ADMIN") {
                    navigate("/admin")
                } else if (user.role == "ROLE_CUSTOMER") {
                    navigate("/customer")
                }
            }
        )
        .catch((error) => {
            setError(error.response.data.message);
            localStorage.removeItem("jwt");
            console.error("There was an error!", error.response.data.message)
        });
    }    

    const doLogin = async() => {
        await loginUser({email, password});
    }

    function handleSubmit(event) {
        doLogin();   
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
                <br/>
                <text className="error-message">
                    {error}
                </text>

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