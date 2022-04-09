import React, {useState, useEffect} from "react";
import {Card, Table} from "react-bootstrap";
import "../../css/FormStyle.css";
import axios from "axios";

function ViewOrders() {
    const [admin, setAdmin] = useState(JSON.parse(localStorage.getItem("user")));
    const [orders, setOrders] = useState();

    useEffect(() => {
        axios
        .get('http://localhost:8080/admin/viewOrders', {
            params: {
                restaurantName: JSON.parse(localStorage.getItem("user")).restaurant.name
            }
        })
        .then(response => {
            console.log(response.data);
            setOrders(response.data);   
        });
    }, []);

    if (orders?.length == 0) { 
        return (
            <Card className="CardStyle">
                <Card.Body>Your restaurant has no orders yet.</Card.Body>
                <Card.Body>🍇🍇🍇🍇</Card.Body>
                <Card.Body>Make sure to promote your restaurant to gain popularity amongst the customers.</Card.Body>
            </Card>
        );
    } else {
        return (
            <div className="TableStyle">
            
                <br/>
                <br/>

                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>Customer email</th>
                            <th>Ordered foods</th>
                            <th>Status</th>
                            <th>Total Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orders?.map(order => {
                            return (
                                <tr key={order.idOrder}>
                                    <td key={order.customer.email}>{order.customer.email}</td>
                                    <td key={order.idOrder}>{order.foods?.map(food => { return(food.name + " | "); })}</td>
                                    <td key={order.status}>{order.status}</td>
                                    <td key={order.total}>{order.total}</td>
                                </tr>
                            );
                        })}
                    </tbody>
                </Table>
            </div>
        );
    }

}

export default ViewOrders;