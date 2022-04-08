import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, HashRouter, Route, Routes } from 'react-router-dom';
import Home from "./components/Home";
import Login from "./components/Login";
//import {loginUser, user} from "./components/Login";
import Register from "./components/Register";
import Admin from "./components/Admin";
import Customer from "./components/Customer";


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/admin/*" element={<Admin/>}/>
        <Route path="/customer/register" element={<Register/>}/>
        <Route path="/customer/*" element={<Customer/>}/>
        <Route path="/" element={<Home/>}>
          <Route path="login" element={<Login/>}/>
          <Route path="customer/register" element={<Register/>}/>
        </Route>
        
      </Routes>
    </Router> 
  );
}

export default App;
