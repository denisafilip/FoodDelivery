import React, { useState, useEffect } from "react";
import {Form, Button,} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import "../css/FormStyle.css";
import axios from "axios";

function Register() {
    const [zones, setZones] = useState(JSON.parse(localStorage.getItem("zones")));
    const [addressState, setAddressState] = useState({
        street: "",
        number: "",
        city: "",
        zone: zones[0],
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
            zone: zones[0],
            country: "",
            postalCode: ""
        },
    });
    
    const [error, setError] = useState("");

    const navigate = useNavigate();

    function goToLogIn() {
        navigate("/login");
    }

    function validateForm() {
        if (userInfo.password != userInfo.confirmPassword) {
            setError("Your passwords do not match!");
        }
        return userInfo.email.length > 0 && userInfo.password.length > 0 && 
            userInfo.firstName.length > 0 && userInfo.lastName.length > 0 &&
            userInfo.address.street.length > 0 && userInfo.address.country.length > 0 &&
            userInfo.address.number.length > 0 && userInfo.address.postalCode.length > 0 &&
            userInfo.address.city.length > 0 && userInfo.confirmPassword == userInfo.password;
    }

    function handleChange(event) {
        // get name and value properties from event target
        const {name, value} = event.target
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

    const registerCustomer = async(userInfo) => {
        await axios
          .post("http://localhost:8080/customer/register", userInfo)
          .then((response) => {
              console.info(response);
              goToLogIn();
          })
          .catch((error) => {
            setError(error.response.data.message);
            console.error("There was an error!", error.response.data.message)
          });
    }

    function handleSubmit(event) {
        registerCustomer(userInfo);
        console.log(userInfo);
        event.preventDefault();
    }

    return (
        <div className="FormStyle">
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

                <div>
                    <select value={addressState.zone.idZone} onChange={handleSelect}>
                        {zones.map(zone =>
                            <option value={zone.idZone} key={zone.idZone}>{zone.name}</option>
                        )}
                    </select>
                </div>

                <br/>

                <text className="error-message">
                    {error}
                </text>

                <Button variant="primary" block size="lg" type="submit" disabled={!validateForm()}>
                    Register
                </Button>
                <Button variant="primary" block size="lg" onClick={goToLogIn}>
                    Log In
                </Button>
            </Form>
        </div>
    );
}

export default Register;