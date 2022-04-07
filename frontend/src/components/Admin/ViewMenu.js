import React, { useState } from "react";
import {Card, Table} from "react-bootstrap";
import "../../css/FormStyle.css";

function ViewMenu() {
    const [foods, setFoods] = useState(JSON.parse(localStorage.getItem("user")).restaurant.foods);
    const [foodCategories, setFoodCategories] = useState(JSON.parse(localStorage.getItem("foodCategories")));

    return (
        <div className="TableStyle">
        
            <br/>
            <br/>

            <div>
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
                                    </tr>
                                </thead>
                                <tbody>
                                    {foods?.map(food => {
                                        if (food.category.name == category.name) {
                                            return (
                                                <tr key={food.name}>
                                                    <td key={food.idFood}>{food.idFood}</td>
                                                    <td key={food.name}>{food.name}</td>
                                                    <td key={food.description}>{food.description}</td>
                                                    <td key={food.price}>{food.price}</td>
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

export default ViewMenu;