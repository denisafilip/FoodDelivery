import axios from "axios";

const API_URL = "http://localhost:8080/";


class AuthService {
    login(credentials) {
        return axios
          .post(API_URL + "login", credentials)
          .then(response => {
            if (response.data.accessToken) {
              localStorage.setItem("jwt", JSON.stringify(response.data));
            }
            return response.data;
          });
    }

  logout() {
    localStorage.removeItem("jwt");
    localStorage.removeItem("user");
  }

  register(customerDetails) {
    return axios.post(API_URL + "customer/register", customerDetails);
  }

  getJWT() {
    console.log(JSON.parse(localStorage.getItem('jwt')))
    return JSON.parse(localStorage.getItem('jwt'));
  }
}

export default new AuthService();