import React, { Component } from 'react';
import Header from './header';
import '../styles/login.css';
import axios, {post} from 'axios';

const baseURL = 'http://127.0.0.1:8080/api/v1/auth';


export default class CreateAlbum extends Component {


    constructor(props) {
        super(props)
    
        this.state = {
            albumName: "",
            file: null
        }

        this.handleSubmit = this.handleSubmit.bind(this);
    }
    
    handleSubmit(e) {
        e.preventDefault();
        const url = 'http://localhost:8080/api/v1/albums';
        const formData = new FormData();
        formData.append('file', this.state.file);
        formData.append('albumName', this.state.albumName);
        const config = {
            headers: {
                Authorization: localStorage.getItem('access_token')
                    ? 'Bearer ' + localStorage.getItem('access_token')
                    : null,
                'content-type': 'multipart/form-data'
            }
        }
        return  post(url, formData,config)


        // e.preventDefault();
        // axiosForm
        // .post('http://localhost:8080/api/v1/albums/', {
        //     album: {albumName: this.state.albumName},
        //     file: this.state.file
        // })
        // .then(res => {
        //     alert(res.data)
        // });




    }

    fileHandler = (e) => {
        this.setState({file: e.target.files[0]})
    }

    render() {
        return (
            <div>
                <Header />
                <div className="login-box">
                    <form >
                    {/*email*/}
                    <div className="user-box">
                        <input type="text" value={this.state.albumName} onChange={(e) => {this.setState({albumName: e.target.value})}}  />
                        <label>Album Name</label>
                    </div>
                    {/*password*/}
                    <div className="user-box">
                        <input type="file" onChange={this.fileHandler}/>
                        <label>Album Cover</label>
                    </div>
                    {/*submit or enter button*/}
                    <div className="user-box">
                        <button type="submit mx-auto" onClick={this.handleSubmit} >create album</button>
                    </div>
                    </form>
                </div>
            </div>
        )
    }
}
