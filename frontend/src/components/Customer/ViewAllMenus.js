import React, { useState, useEffect } from "react";
import {Card, Table, Button} from "react-bootstrap";
import "../../css/FormStyle.css";

function ViewAllMenus() {
    const [restaurants, setRestaurants] = useState(JSON.parse(localStorage.getItem("restaurants")));
    const [currentRestaurant, setCurrentRestaurant] = useState(restaurants[0]);
    const [foodCategories, setFoodCategories] = useState(JSON.parse(localStorage.getItem("foodCategories")));

    useEffect(() => {
        window.addEventListener('storage', () => {
            setRestaurants(JSON.parse(localStorage.getItem("restaurants")) || [])   
            window.location.reload();
          });
        console.log(localStorage.getItem("restaurants"))
    }, []);

    function handleChange(event) {
        restaurants.forEach(restaurant => {
            if (restaurant.name == event.target.value) {
                setCurrentRestaurant(restaurant);
            }
        })
    }

    function addToCart(food) {

    }

    return (
        <div key="initial" className="TableStyle1">
        
            <br/>
            <br/>

            <div key="first">
                <div key="second">
                    <select className="select-container" key={currentRestaurant.idRestaurant} value={currentRestaurant.name} onChange={handleChange}>
                        {restaurants?.map(restaurant =>
                            <option value={restaurant.name} key={restaurant.idRestaurant}>{restaurant.name}</option>
                        )}
                    </select>
                </div>
                {foodCategories?.map(category => {
                    return (
                        <div key={category.name}>
                            <Card className="CardStyle">
                                <Card.Body>{category.name}</Card.Body>
                            </Card>
                            <Table striped bordered hover key={category.name}>
                                <thead>
                                    <tr>
                                    <th>#</th>
                                    <th>Food Name</th>
                                    <th>Description</th>
                                    <th>Price</th>
                                    <th>Add to cart</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {currentRestaurant.foods?.map(food => {
                                        if (food.category.name == category.name) {
                                            return (
                                                <tr key={food.name}>
                                                    <td key={food.idFood}>{food.idFood}</td>
                                                    <td key={food.name}>{food.name}</td>
                                                    <td key={food.description}>{food.description}</td>
                                                    <td key={food.price}>{food.price}</td>
                                                    <td key={"button" + food.idFood}><Button onClick={() => addToCart(food)}>Add</Button></td>
                                                </tr>
                                            );
                                        }
                                    })}
                                </tbody>
                            </Table>
                            <br/>
                            <br/>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}

export default ViewAllMenus;