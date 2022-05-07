import React, { useState, useEffect } from "react";
import {Card, Table, Button, Modal} from "react-bootstrap";
import "../../css/FormStyle.css";
import axios from "axios";
import authHeader from "../AuthHeader";

function ViewAllMenus() {
    const [restaurants, setRestaurants] = useState(JSON.parse(localStorage.getItem("restaurants")));
    const [currentRestaurant, setCurrentRestaurant] = useState(restaurants[0]);
    const [foods, setFoods] = useState([]);
    const [foodCategories, setFoodCategories] = useState(JSON.parse(localStorage.getItem("foodCategories")));
    const [customer, setCustomer] = useState(JSON.parse(localStorage.getItem("user")));
    const [addedFoods, setAddedFoods] = useState([]);
    const [order, setOrder] = useState({
        customer: customer,
        restaurant: currentRestaurant,
        foods: []
    })

    const [modalShow, setModalShow] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        window.addEventListener('storage', () => {
            setRestaurants(JSON.parse(localStorage.getItem("restaurants")) || []);
            setFoods(JSON.parse(localStorage.getItem("foods")) || []);
            window.location.reload();
          });
    }, []);

    function handleChange(event) {
        restaurants.forEach(restaurant => {
            if (restaurant.name == event.target.value) {
                setCurrentRestaurant(restaurant);
                getFoods(event.target.value);
                setError("");
            }
        });
        setAddedFoods([]);
    }

    function addToCart(food) {
        setAddedFoods(prevState => (
            [...prevState, food]
        ))
        setOrder(prevState => {
            return {
                ...prevState,
                foods: addedFoods
            };
        });
    }

    const getFoods = async(restaurantName) => {
        axios
        .get('http://localhost:8080/admin/viewMenu', {
            headers: authHeader(),
            params: {
                restaurantName: restaurantName
            }
        })
        .then(response => {
            console.log(response.data);
            localStorage.setItem('foods', JSON.stringify(response.data));
            setFoods(response.data);   
        })
        .catch((error) => setError(error.response.data.message));
    }


    useEffect(() => {
        console.log(currentRestaurant.name)
        getFoods(currentRestaurant.name);
    }, []);

    const updateState = async() => {
        await placeOrder();
        axios
          .get("http://localhost:8080/customer/get", {
              headers: authHeader(),
              params: {
                  customerEmail: customer.email
              }
          })
          .then((response) => {
                localStorage.setItem('user', JSON.stringify(response.data));
                console.log(response.data);
          })
          .catch((error) => console.error("There was an error when adding food!", error));
    }


    const removeFromCart = (food) => {
        let filteredArray = addedFoods.filter(foodItem => foodItem !== food)
        setAddedFoods(filteredArray);
        setOrder(prevState => {
            return {
                ...prevState,
                foods: addedFoods
            };
        });
    }

    const placeOrder = async() => {
        await axios
        .post("http://localhost:8080/customer/viewMenu", {...order, restaurant: currentRestaurant, foods: addedFoods}, {headers: authHeader()})
        .then((response) => {
              alert("You placed an order for the restaurant " + currentRestaurant.name + "!");
              setOrder({
                customer: customer,
                restaurant: currentRestaurant,
                foods: []
              });
              setError("");
              setAddedFoods([]);
            console.info(response)
        })
        .catch((error) => {
              setError(error.response.data.message);
              console.error("There was an error!", error.response.data.message)
        });
    }

    function checkoutOrder() {
        updateState();
    }

    const MyVerticallyCenteredModal = (props) => {
        let totalPrice = 0;
        addedFoods.forEach((food) => {
            totalPrice += food.price;
        });
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
                <div key={food.idFood} className="row product align-items-center">
                    <div className="col-md-6 product-detail">
                        <h5>{food.name}</h5>
                    </div>
                    <div className="col-md-2 cart-product-price">
                        {food.price + " RON"}
                    </div>
                    <Button className="col-md-2 float-right" block size="sm" variant="primary" onClick={() => removeFromCart(food)}>Remove</Button>
                    <hr/>
              </div>
              )}
                <div className="row text-right">
                    <div className="col-md-12 text-right">
                        Total: {totalPrice + " RON"}
                    </div>
                </div>
              <div className="row text-right">
                <div className="col-md-12 text-right">
                    <Button variant="primary" className="float-right" onClick={() => checkoutOrder()}>
                        Checkout
                    </Button>
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

            <Card className="CardStyle">
                <Card.Body>You can order from only one restaurant at a time!</Card.Body>
            </Card>

            <br/>
            <br/>

            <br/>
                <text className="error-message">
                    {error}
                </text>
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
                                    <th>Food Name</th>
                                    <th>Description</th>
                                    <th>Price</th>
                                    <th>Add to cart</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {JSON.parse(localStorage.getItem("foods"))?.map(food => {
                                        if (food.category.name == category.name) {
                                            return (
                                                <tr key={food.name}>
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