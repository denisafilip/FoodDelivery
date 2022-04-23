import axios from "axios";
import React, { useState, useEffect } from "react";
import {Card, Table, Button} from "react-bootstrap";
import "../../css/FormStyle.css";

function ViewMenu() {
    const [foods, setFoods] = useState([]);
    const [foodCategories, setFoodCategories] = useState(JSON.parse(localStorage.getItem("foodCategories")));

    useEffect(() => {
        console.log(JSON.parse(localStorage.getItem("user")).restaurant.name)
        axios
        .get('http://localhost:8080/admin/viewMenu', {
            params: {
                restaurantName: JSON.parse(localStorage.getItem("user")).restaurant.name
            }
        })
        .then(response => {
            console.log(response.data);
            setFoods(response.data);   
        });
    }, []);

    const exportToPDF = async() => {
        axios
        .request('http://localhost:8080/admin/viewMenu/export', {
            params: {
                restaurantName: JSON.parse(localStorage.getItem("user")).restaurant.name
            }
        })
        .then(response => {
            console.log(response.data);  
        });
    }

    function handleSubmit(event) {
        exportToPDF();
        event.preventDefault();
    }

    return (
        <div className="TableStyle">
        
            <br/>
            <br/>

            <Button variant="primary" block size="lg" type="submit" onClick={handleSubmit}>
                Export to PDF
            </Button>

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
                                                    <td key={food.name}>{food.name}</td>
                                                    <td key={food.description}>{food.description}</td>
                                                    <td key={food.price}>{food.price + " RON"}</td>
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