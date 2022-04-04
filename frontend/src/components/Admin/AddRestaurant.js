import React, { useState, useEffect } from "react";
import {Form, Button} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../css/FormStyle.css";

function AddRestaurant(props) {
    const [addressState, setAddressState] = useState({
        street: "",
        number: "",
        city: "",
        zone: {},
        country: "",
        postalCode: ""
    })
    const [restaurantInfo, setRestaurantInfo] = useState({
        name: "",
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
        deliveryZones: [],
        administrator: {}
    });
    const [zones, setZones] = useState([]);

    useEffect(() => {
        const loggedInUser = localStorage.getItem("user");
        if (loggedInUser) {
            const foundUser = JSON.parse(loggedInUser);
            setRestaurantInfo(prevState => {
                return {
                    ...prevState,
                    administrator: foundUser
                };
            })
        }
        
        console.log(restaurantInfo.administrator);
      }, []);

    useEffect(() => {
        fetch('http://localhost:8080/admin/addRestaurant')
          .then(response => response.json())
          .then(setZones);
        }, []);

    const navigate = useNavigate();

    function handleChange(event) {
        const {name, value} = event.target
        setRestaurantInfo(prevState => {
            return {
              ...prevState,
              [name]: value
            };
        })
        console.log(restaurantInfo);
    }

    function handleChangeAddress(event) {
        const {name, value} = event.target
        setAddressState(prevState => {
            return {
              ...prevState,
              [name]: value
            };
        })
        setRestaurantInfo(prevState => {
            return {
              ...prevState,
              address: addressState
            };
        })
        console.log(restaurantInfo)
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
        
        setRestaurantInfo(prevState => {
            return {
              ...prevState,
              address: addressState
            };
        })
        console.log(addressState);
        console.log(restaurantInfo);
    }

    function handleDeliveryZoneAddition(event) {
        let value = Array.from(
          event.target.selectedOptions,
          (option) => option.value
        );
        setRestaurantInfo(prevState => {
            return {
              ...prevState,
              deliveryZones: value
            };
        })
    }

    function addRestaurant(restaurantInfo) {
        axios
          .post("http://localhost:8080/admin/addRestaurant", restaurantInfo)
          .then((response) => console.info(response))
          .catch((error) => console.error("There was an error!", error));
    }

    function handleSubmit(event) {
        addRestaurant(restaurantInfo);
        console.log(restaurantInfo);
        setRestaurantInfo({
            name: "",
            address: {},
            zones: [],
            administrator: {}
            
        })
        setAddressState({
            street: "",
            number: "",
            city: "",
            zone: {},
            country: "",
            postalCode: ""
        })
        navigate("/admin");
        event.preventDefault();
    }

    return (
        <div className="FormStyle">
            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="formBasicName" className="mb-3">
                    <Form.Label>Restaurant Name</Form.Label>
                        <Form.Control autoFocus name="name" type="text" value={restaurantInfo.name} onChange={handleChange} />
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

                <div className="select-container">
                    <select multiple={true} value={restaurantInfo.deliveryZones} onChange={handleDeliveryZoneAddition}>
                        {zones.map(zone =>
                            <option value={zone.idZone} key={zone.idZone}>{zone.name}</option>
                        )}
                    </select>
                </div>

                <Button variant="primary" block size="lg" type="submit">
                    Add Restaurant
                </Button>
            </Form>
        </div>
    );
}

export default AddRestaurant;