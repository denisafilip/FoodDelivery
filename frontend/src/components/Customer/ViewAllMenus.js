import React, { useState, useEffect } from "react";
import {Card, Table, Button, Modal} from "react-bootstrap";
import "../../css/FormStyle.css";

function ViewAllMenus() {
    const [restaurants, setRestaurants] = useState(JSON.parse(localStorage.getItem("restaurants")));
    const [currentRestaurant, setCurrentRestaurant] = useState(restaurants[0]);
    const [foodCategories, setFoodCategories] = useState(JSON.parse(localStorage.getItem("foodCategories")));
    const [addedFoods, setAddedFoods] = useState([]);
    const [order, setOrder] = useState({
        customer: JSON.parse(localStorage.getItem("user")),
        restaurant: currentRestaurant,
        foods: []
    })

    const [modalShow, setModalShow] = useState(false);


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
        setAddedFoods(prevState => (
            [...prevState, food]
        ))
    }

    function MyVerticallyCenteredModal(props) {
        let totalPrice = 0;
        addedFoods.forEach((food) => {
            totalPrice += food.price;
        });
        console.log(totalPrice)
        return (
          <Modal
            {...props}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
          >
            <Modal.Header closeButton>
              <Modal.Title id="contained-modal-title-vcenter">
                Cart
              </Modal.Title>
            </Modal.Header>
            <Modal.Body>
              {addedFoods?.map(food =>
                <div key={food.idFood} className="row product">
                    <div className="col-md-8 product-detail">
                    <h5>{food.name}</h5>
                    </div>
                    <div className="col-md-4 cart-product-price">
                    {food.price}
                    </div>
                    <hr/>
              </div>
              )}
                <div className="row">
                    <div className="col-md-12 text-right">
                        Total: {totalPrice + " RON"}
                    </div>
                </div>
              <div className="row">
                <div className="col-md-12 text-left">
                    <button className="btn btn-success">
                    Checkout
                    </button>
                </div>
              </div>
            </Modal.Body>
            <Modal.Footer>
              <Button onClick={props.onHide}>Close</Button>
            </Modal.Footer>
          </Modal>
        );
    }

    return (
        <div key="initial" className="TableStyle">

            <Button onClick={() => setModalShow(true)}>View Cart</Button>

            <MyVerticallyCenteredModal
                show={modalShow}
                onHide={() => setModalShow(false)}
            />
        
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
                                                    <td key={food.price}>{food.price + " RON"}</td>
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