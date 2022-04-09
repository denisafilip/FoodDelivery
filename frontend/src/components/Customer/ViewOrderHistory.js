import React, {useState, useEffect} from "react";
import {Card, Table} from "react-bootstrap";
import "../../css/FormStyle.css";
import axios from "axios";

function ViewOrderHistory() {
    const [customer, setCustomer] = useState(JSON.parse(localStorage.getItem("user")));
    const [orders, setOrders] = useState();

    const PENDING = "PENDING";
    const ACCEPTED = "ACCEPTED";
    const DECLINED = "DECLINED";
    const IN_DELIVERY = "IN_DELIVERY";
    const DELIVERED = "DELIVERED";

    useEffect(() => {
        axios
        .get('http://localhost:8080/customer/viewOrderHistory', {
            params: {
                customerEmail: customer.email
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
                <Card.Body>You have no orders yet.</Card.Body>
                <Card.Body>🍇🍇🍇🍇</Card.Body>
                <Card.Body>Press the <b> View Menu</b> button to start adding food items to your cart.</Card.Body>
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
                            <th>Restaurant Name</th>
                            <th>Ordered foods</th>
                            <th>Status</th>
                            <th>Total Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orders?.map(order => {
                            return (
                                <tr className={order.status == DECLINED ? "declined" : order.status == ACCEPTED ? "finished" : "in-progress" } key={order.total}>
                                    <td>{order.restaurant.name}</td>
                                    <td>{order.foods?.map(food => { return(food.name + " | "); })}</td>
                                    <td key={order.status}><b>{order.status}</b></td>
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

export default ViewOrderHistory;