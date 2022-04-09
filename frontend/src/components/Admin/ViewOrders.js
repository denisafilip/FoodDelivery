import React, {useState, useEffect, useCallback} from "react";
import {Card, Table, Button} from "react-bootstrap";
import "../../css/FormStyle.css";
import axios from "axios";

function ViewOrders() {
    const [admin, setAdmin] = useState(JSON.parse(localStorage.getItem("user")));
    const [orders, setOrders] = useState();
    const [error, setError] = useState("");
    const [isSending, setIsSending] = useState(false);

    const PENDING = "PENDING";
    const ACCEPTED = "ACCEPTED";
    const DECLINED = "DECLINED";
    const IN_DELIVERY = "IN_DELIVERY";
    const DELIVERED = "DELIVERED";

    const acceptOrder = useCallback(async (order) => {
        if (isSending) return;
        setIsSending(true);
        await axios
          .post("http://localhost:8080/admin/order/accept", order)
          .then((response) => {
              console.info(response.data);
          })
          .catch((error) => {
              setError(error.response.data.message);
              console.error("There was an error!", error.response.data.message)
          });
          setIsSending(false);
    }, [isSending]);

    const declineOrder = useCallback(async (order) => {
        if (isSending) return
        setIsSending(true)
        await axios
          .post("http://localhost:8080/admin/order/decline", order)
          .then((response) => {
              console.info(response.data);
          })
          .catch((error) => {
              setError(error.response.data.message);
              console.error("There was an error!", error.response.data.message)
          });
          setIsSending(false);
    }, [isSending]);

    const updateOrders = async() => {
        await axios
        .get('http://localhost:8080/admin/viewOrders', {
            params: {
                restaurantName: JSON.parse(localStorage.getItem("user")).restaurant.name
            }
        })
        .then(response => {
            console.log(response.data);
            setOrders(response.data);   
        });
    }

    useEffect(() => {
        updateOrders();
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

                <br/>
                <text style={{color: 'red', justifyContent: 'center', display: 'flex'}}>
                    {error}
                </text>

                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>Customer email</th>
                            <th>Ordered foods</th>
                            <th>Status</th>
                            <th>Total Price</th>
                            <th>Accept</th>
                            <th>Decline</th>
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
                                    <td key={"accept" + order.idOrder}>
                                        <Button variant="success" disabled={order.status != PENDING ? "disabled" : ""} onClick={() => acceptOrder(order)}>
                                            Accept
                                        </Button>
                                    </td>
                                    <td key={"decline" + order.idOrder}>
                                        <Button variant="danger" disabled={order.status != PENDING ? "disabled" : ""} onClick={() => declineOrder(order)}>
                                            Decline
                                        </Button>
                                    </td>
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