import axiosInstance from '../axios';


const BASE_URL = 'http://localhost:8080/api/v1/profile/';

class UserService {

    getUserProfile(username) {
        return axiosInstance.get(BASE_URL + username)
    }
    
} 
export default new UserService();