import React, { useState, useEffect } from "react";
import {Form, Button, Card, Alert} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../css/FormStyle.css";

function AddFood() {
    const [foodCategories, setFoodCategories] = useState(JSON.parse(localStorage.getItem("foodCategories")));
    const [admin, setAdmin] = useState(JSON.parse(localStorage.getItem("user")));
    const [foodInfo, setFoodInfo] = useState({
        name: "",
        price: 0,
        description: "",
        category: foodCategories[0],
        restaurantDTO: JSON.parse(localStorage.getItem("user")).restaurant
    });
    const [error, setError] = useState("");

    const navigate = useNavigate();

    function validateForm() {
        return foodInfo.name.length > 0 && foodInfo.price > 0 && foodInfo.description.length > 0;
    }

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

    const addFood = async() => {
        await axios
          .post("http://localhost:8080/admin/addFood", foodInfo)
          .then((response) => {
                alert("You added a " + foodInfo.name + " under the category " + foodInfo.category.name + "!");
                setFoodInfo({
                    name: "",
                    price: 0,
                    description: "",
                    category: {},
                    restaurantDTO: JSON.parse(localStorage.getItem("user")).restaurant
                })
              console.info(response)
          })
          .catch((error) => {
                setError(error.response.data.message);
                console.error("There was an error!", error.response.data.message)
          });
    }

    const updateState = async() => {
        console.log(admin.email);
        await addFood();
        axios
          .get("http://localhost:8080/admin/get", {
              params: {
                  adminEmail: admin.email
              }
          })
          .then((response) => {
                localStorage.setItem('user', JSON.stringify(response.data));
                console.log(response.data);
                navigate("/admin/addFood");
          })
          .catch((error) => console.error("There was an error when adding food!", error));
    }

    function handleSubmit(event) {
        updateState();
        event.preventDefault();
    }

    return (
        <div className="FormStyle">
            <Card className="CardStyle">
                <Card.Body>Please select a category and start adding foods to your menu!</Card.Body>
            </Card>

            <br/>
            <br/>

            <div>
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

                <br/>
                <text style={{color: 'red', justifyContent: 'center', display: 'flex'}}>
                    {error}
                </text>

                <Button variant="primary" block size="lg" type="submit" disabled={!validateForm()}>
                    Add Food
                </Button>
            </Form>
        </div>
    );
}

export default AddFood;