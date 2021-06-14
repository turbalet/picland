import axiosInstance from '../axios';


const BASE_URL = 'http://localhost:8080/api/v1/comments';

 class CommentService {


    getPicaComments(id) {
        return axiosInstance.get(BASE_URL + "/pica/" + id);
    }

} 
export default new CommentService();