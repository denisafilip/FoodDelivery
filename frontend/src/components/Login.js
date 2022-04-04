import React, { useState } from "react";
import {Form, Button} from "react-bootstrap";
import { ThemeConsumer } from "react-bootstrap/esm/ThemeProvider";
import { useNavigate } from "react-router-dom";
import "../css/Login.css";
import axios from "axios";

function Login(props) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    function goToRegister() {
        navigate("/customer/register");
    }

    function validateForm() {
        return email.length > 0 && password.length > 0;
    }

    function handleSubmit(event) {
        console.log(email)
        console.log(password)
        props.login({email, password}).then((gotAdmin) => {
            setEmail("");
            setPassword("");
            if (gotAdmin) {
                navigate("/");
            } else {
                navigate("/customer");
            }
        })
        event.preventDefault();
    }

    return (
        <div className="Login">
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

let user = undefined;
let gotAdmin = undefined;

async function loginUser(credentials) {
    await axios
      .get("http://localhost:8080/login", {
        params: credentials,
      })
      .then((response) => {
        user = response.data.account;
        gotAdmin = response.data.admin;
      })
      .catch((error) => console.error("There was an error!", error));
    return gotAdmin;
}

/*function loginUser(credentials) {
    axios
      .post("http://localhost:8080/customer", credentials)
      .then((response) => console.info(response))
      .catch((error) => console.error("There was an error!", error));
}*/

export default Login;
export {user, gotAdmin, loginUser};