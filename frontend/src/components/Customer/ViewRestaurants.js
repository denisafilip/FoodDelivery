import React, { useState } from "react";
import {Card, Table, Form} from "react-bootstrap";
import "../../css/FormStyle.css";

function ViewRestaurants() {
    const [restaurants, setRestaurants] = useState(JSON.parse(localStorage.getItem("restaurants")));
    const [filter, setFilter] = useState("");

    return (
        <div className="TableStyle">
        
            <br/>
            <br/>

            <Form>
                <Form.Group className="mb-3" controlId="formBasicText">
                    <Form.Text className="text-muted">
                        🔎 Filter the restaurants by their name.
                    </Form.Text>
                    <br/>
                    <Form.Control type="text" placeholder="restaurant name" value={filter} onChange={(e) => setFilter(e.target.value)}/>
                </Form.Group>
            </Form>

            <br/>

            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Restaurant Name</th>
                        <th>Address</th>
                        <th>Delivery Zones</th>
                    </tr>
                </thead>
                <tbody>
                    {restaurants?.filter(restaurant => restaurant.name.startsWith(filter) || filter === "")
                    .map(restaurant => {
                        return (
                            <tr key={restaurant.name}>
                                <td key={restaurant.name}>{restaurant.name}</td>
                                <td key={restaurant.address}>{restaurant.address.street + " " + restaurant.address.number + ", " + restaurant.address.city}</td>
                                <td key={restaurant.deliveryZones}>{" | "}
                                    {restaurant.deliveryZones?.map(zone => { return(
                                        zone.name + " | ");
                                    })}
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </Table>
        </div>
    );
}

export default ViewRestaurants;