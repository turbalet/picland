import axiosInstance from '../axios';


const BASE_URL = 'http://localhost:8080/api/v1/picas';

 class PicaService {


    getPicas() {
        return axiosInstance.get(BASE_URL);
    }

    getPicaById(id) {
        return axiosInstance.get(BASE_URL + "/" + id);
    }

    searchPicas(word) {
        return axiosInstance.get(BASE_URL + "/d?search=" + word);
    } 
} 
export default new PicaService();