import axiosInstance from '../axios';


const BASE_URL = 'http://localhost:8080/api/v1/albums';

 class AlbumService {


    getAlbums() {
        return axiosInstance.get(BASE_URL);
    }

    getAlbum(id) {
        return axiosInstance.get(BASE_URL + "/" + id);
    }

    getUserAlbums(username) {
        return axiosInstance.get(BASE_URL + "/user/" + username)
    }

} 
export default new AlbumService();