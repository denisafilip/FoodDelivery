import React, { useState } from "react";
import {Card, Table} from "react-bootstrap";
import "../../css/FormStyle.css";

function ViewRestaurants() {
    const [restaurants, setRestaurants] = useState(JSON.parse(localStorage.getItem("restaurants")));

    return (
        <div className="TableStyle">
        
            <br/>
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
                    {restaurants?.map(restaurant => {
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