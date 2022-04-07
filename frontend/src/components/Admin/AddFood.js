import React, { useState, useEffect } from "react";
import {Form, Button, Card} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../css/FormStyle.css";

function AddFood() {
    const [foodInfo, setFoodInfo] = useState({
        name: "",
        price: 0,
        description: "",
        category: {},
        restaurantDTO: {}
    });
    const [foodCategories, setFoodCategories] = useState([]);
    const [admin, setAdmin] = useState({});

    useEffect(() => {
        const loggedInUser = localStorage.getItem("user");
        if (loggedInUser) {
            const foundUser = JSON.parse(loggedInUser);
            setAdmin(foundUser);
            setFoodInfo(prevState => {
                return {
                  ...prevState,
                  restaurantDTO: foundUser.restaurant
                };
            })
        }
      }, []);

    useEffect(() => {
        fetch('http://localhost:8080/admin/addFood')
          .then(response => response.json())
          .then(setFoodCategories);
        localStorage.setItem('foodCategories', JSON.stringify(foodCategories));
    }, []);

    const navigate = useNavigate();

    function handleChange(event) {
        const {name, value} = event.target
        setFoodInfo(prevState => {
            return {
              ...prevState,
              [name]: value
            };
        })
        console.log(foodInfo);
    }

    function handleSelect(event) {
        const {name, value} = event.target
        console.log(value);
        setFoodInfo(prevState => {
            return {
                ...prevState,
                category: {
                  idCategory: value,
                  name: ""
              }
            };
        })
        console.log(foodInfo);
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

    function addFood() {
        axios
          .post("http://localhost:8080/admin/addFood", foodInfo)
          .then((response) => console.info(response))
          .catch((error) => console.error("There was an error when adding food!", error));
    }

    function handleSubmit(event) {
        addFood();
        console.log(foodInfo);
        setFoodInfo({
            name: "",
            price: 0,
            description: "",
            category: {}
        })
        navigate("/admin/addFood");
        event.preventDefault();
    }

    return (
        <div className="FormStyle">
            <Card className="CardStyle">
                <Card.Body>Please select a category and start adding foods to your menu!</Card.Body>
            </Card>

            <br/>
            <br/>

            <div className="select-container">
                <select value={foodInfo.category.idCategory} onChange={handleSelect}>
                    {foodCategories?.map(category =>
                        <option value={category.idCategory} key={category.idCategory}>{category.name}</option>
                    )}
                </select>
            </div>

            <br/>
            <br/>

            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="foodName" className="mb-3">
                    <Form.Label>Food Name</Form.Label>
                        <Form.Control autoFocus name="name" type="text" value={foodInfo.name} onChange={handleChange} />
                </Form.Group>

                <Form.Group size="lg" controlId="foodPrice" className="mb-3">
                    <Form.Label>Price</Form.Label>
                        <Form.Control autoFocus name="price" type="number" value={foodInfo.price} onChange={handleChange} />
                </Form.Group>

                <Form.Group size="lg" controlId="foodDescription" className="mb-3">
                    <Form.Label>Description</Form.Label>
                        <Form.Control autoFocus name="description" type="text" value={foodInfo.description} onChange={handleChange} />
                </Form.Group>

                <Button variant="primary" block size="lg" type="submit">
                    Add Food
                </Button>
            </Form>
        </div>
    );
}

export default AddFood;