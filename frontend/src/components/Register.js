import React, { useState, useEffect } from "react";
import {Form, Button, Label, Dropdown, DropdownButton} from "react-bootstrap";
import { ThemeConsumer } from "react-bootstrap/esm/ThemeProvider";
import { useNavigate } from "react-router-dom";
import "../css/Register.css";
import axios from "axios";

function Register(props) {
    const [addressState, setAddressState] = useState({
        street: "",
        number: "",
        city: "",
        zone: {},
        country: "",
        postalCode: ""
    })
    const [userInfo, setUserInfo] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confirmPassword: "",
        address: {
            street: "",
            number: "",
            city: "",
            zone: {
                idZone: 0,
                name: ""
            },
            country: "",
            postalCode: ""
        },
    });
    const [zones, setZones] = useState([]);

    //canot read map?
    useEffect(() => {
        fetch('http://localhost:8080/customer/register')
          .then(response => response.json())
          .then(setZones);
        }, []);

    const navigate = useNavigate();

    function goToLogIn() {
        navigate("/login");
    }

    function validateForm() {
        return userInfo.email.length > 0 && userInfo.password.length > 0 && userInfo.confirmPassword != userInfo.password;
    }

    function handleChange(event) {
        // get name and value properties from event target
        const {name, value} = event.target
        console.log(value)
        setUserInfo(prevState => {
            // update your 'list' property
            return {
              // spread old values into this object so you don't lose any data
              ...prevState,
              // update this field's value
              [name]: value
            };
        })
        console.log(userInfo);
    }

    function handleChangeAddress(event) {
        const {name, value} = event.target
        setAddressState(prevState => {
            return {
              ...prevState,
              [name]: value
            };
        })
        setUserInfo(prevState => {
            return {
              ...prevState,
              address: addressState
            };
        })
        console.log(userInfo)
    }

    function handleSelect(event) {
        const {name, value} = event.target
        console.log(value);
        setAddressState(prevState => {
            return {
              ...prevState,
              "zone": {
                  idZone: value,
                  name: ""
              }
            };
        })
        
        setUserInfo(prevState => {
            return {
              ...prevState,
              address: addressState
            };
        })
        console.log(addressState);
        console.log(userInfo);
    }

    function handleSubmit(event) {
        props.register(userInfo);
        console.log(userInfo);
        setUserInfo({
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            confirmPassword: "",
            address: {},
            
        })
        setAddressState({
            street: "",
            number: "",
            city: "",
            zone: {},
            country: "",
            postalCode: ""
        })
        navigate("/login");
        event.preventDefault();
    }

    return (
        <div className="Register">
            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="formBasicFirstName" className="mb-3">
                    <Form.Label>First Name</Form.Label>
                        <Form.Control autoFocus name="firstName" type="text" value={userInfo.firstName} onChange={handleChange} />
                </Form.Group>

                <Form.Group size="lg" controlId="formBasicLastName" className="mb-3">
                    <Form.Label>Last Name</Form.Label>
                    <Form.Control name="lastName" type="text" value={userInfo.lastName} onChange={handleChange} />
                </Form.Group>

                <Form.Group size="lg" controlId="formBasicEmail" className="mb-3">
                    <Form.Label>Email</Form.Label>
                    <Form.Control name="email" type="email" value={userInfo.email} onChange={handleChange}/>
                </Form.Group>

                <Form.Group size="lg" className="mb-3" controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control name="password" type="password" value={userInfo.password} onChange={handleChange}/>
                </Form.Group>

                <Form.Group size="lg" className="mb-3" controlId="formBasicConfirmPassword">
                    <Form.Label>Confirm Password</Form.Label>
                    <Form.Control name="confirmPassword" type="password" value={userInfo.confirmPassword} onChange={handleChange}/>
                </Form.Group>

                <Form.Text>Address</Form.Text>{' '}

                <Form.Group size="lg" className="mb-3" controlId="formBasicStreet">
                    <Form.Label>Street</Form.Label>
                    <Form.Control name="street" type="text" value={addressState.street} onChange={handleChangeAddress}/>
                </Form.Group>

                <Form.Group size="lg" className="mb-3" controlId="formBasicNumber">
                    <Form.Label>Number</Form.Label>
                    <Form.Control name="number" type="text" value={addressState.number} onChange={handleChangeAddress}/>
                </Form.Group>

                <Form.Group size="lg" className="mb-3" controlId="formBasicCity">
                    <Form.Label>City</Form.Label>
                    <Form.Control name="city" type="text" value={addressState.city} onChange={handleChangeAddress}/>
                </Form.Group>

                <Form.Group size="lg" className="mb-3" controlId="formBasicCountry">
                    <Form.Label>Country</Form.Label>
                    <Form.Control name="country" type="text" value={addressState.country} onChange={handleChangeAddress}/>
                </Form.Group>

                <Form.Group size="lg" className="mb-3" controlId="formBasicPostalCode">
                    <Form.Label>PostalCode</Form.Label>
                    <Form.Control name="postalCode" type="text" value={addressState.postalCode} onChange={handleChangeAddress}/>
                </Form.Group>

                <div className="select-container">
                    <select value={addressState.zone.idZone} onChange={handleSelect}>
                        {zones.map(zone =>
                            <option value={zone.idZone} key={zone.idZone}>{zone.name}</option>
                        )}
                    </select>
                </div>

                <Button variant="primary" block size="lg" type="submit" /*disabled={!validateForm()}*/>
                    Register
                </Button>
                <Button variant="primary" block size="lg" onClick={goToLogIn}>
                    Log In
                </Button>
            </Form>
        </div>
    );
}

/*
/*<DropdownButton name="zone" id="dropdown-basic-button" title="Address Zone" value={addressState.zone} onSelect={handleSelect}>
    {zones.map(zone =>
        <div key={zone.idZone}>
            <Dropdown.Item eventKey={zone.name}>{zone.name}</Dropdown.Item>
        </div>
    )}
</DropdownButton>
*/

function registerCustomer(userInfo) {
    axios
      .post("http://localhost:8080/customer/register", userInfo)
      .then((response) => console.info(response))
      .catch((error) => console.error("There was an error!", error));
}

export default Register;
export {registerCustomer};