import React, { Component } from 'react';
import axiosInstance from '../axios';
import CommentService from '../services/CommentService';
import PicaService from '../services/PicaService';
import '../styles/index.css';
import '../styles/pic.css';
import Header from './header';

export default class Pica extends Component {

    constructor(props) {
        super(props)
    
        this.state = {
             pica: {},
             author: {},
             comments: [],
             url: "",
             title: "",
             description: "",
             message: ""
        }

        this.handleSubmit = this.handleSubmit.bind(this);
    }
    
    componentDidMount() {
        let id = window.location.pathname.split('/')[2];
        PicaService.getPicaById(id)
        .then(res => {
            this.setState({
                pic: res.data,
                author: res.data.user,
                url: res.data.url,
                title: res.data.title,
                description: res.data.description
            });
        })
        console.log(this.state.url)

        CommentService.getPicaComments(id)
        .then(res => {
            this.setState({
                comments: res.data
            });
        });
    }

    handleSubmit() {
        axiosInstance.post('http://localhost:8080/api/v1/comments/pica/' + window.location.pathname.split('/')[2], 
        this.state.message
        ).then(res => {
            console.log(res.data);
        });
        CommentService.getPicaComments(window.location.pathname.split('/')[2])
        .then(res => {
            this.setState({
                comments: res.data
            });
        });
    }



    render() {
        return (
            <div>
                <Header />
                <div className="pic-cont d-flex">
                    <div className="image">
                    <img src={this.state.url} alt="dsdsda" />

                    {
                        this.state.author.username == localStorage.getItem("username") ? (
                            <div className="btns my-3 d-flex justify-content-around">
                                <div className="btn btn-outline-light">edit</div>
                                <div className="btn btn-outline-light">remove</div>
                            </div>
                        ) : (<div></div>) 
                    }
                    
                    </div>
                    <div className="info">
                    <div className="pic_info">
                        <h1>{this.state.title}</h1>
                        <p>
                        {this.state.description}
                        </p>
                        <div className="user d-flex">
                        <img src={this.state.author.imgPath} alt="" />
                        <p>@{this.state.author.username}</p>
                        </div>
                    </div>
                    <form className="my-3 new_comment d-flex justify-content-around">
                        <input type="text" placeholder="leave your comment" value={this.state.message} onChange={(e) => {this.setState({message: e.target.value})}} />
                        <button className="btn btn-outline-light" onClick={this.handleSubmit} >Send</button>
                    </form>
                    <div className="comments">
                    {
                        this.state.comments.length == 0 ? (<div>No Comments. Wanna be first?</div>) :
                    (
                        this.state.comments.map(comment =>
                        <div className="comment">
                        <img src={"http://127.0.0.1:8080/api/v1/profile/" + comment.user.userId +"/image/download/"} alt="" onClick={() => {this.props.history.push("/profile/" + comment.user.username);}} />
                        <div className="text">
                            <strong>{comment.user.username}</strong>
                            <p>{comment.message}</p>
                        </div>
                        </div>
                    )
                    )
                }    
                    </div>
                    </div>
                </div>
            </div>
        )
    }
}
