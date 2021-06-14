import React, { Component } from 'react'
import '../styles/index.css';
import '../styles/profile.css';
import Header from './header';
import AlbumService from '../services/AlbumService';
import UserService from '../services/UserService';

export default class Profile extends Component {


    constructor(props) {
        super(props)
    
        this.state = {
            albums: [],
            user: []        
        }
    }
    
    componentDidMount() {
        let username = window.location.pathname.split('/')[2];
        console.log(username)
        if (username == null) {
            this.setState({
                user: {
                    userId: localStorage.getItem("id"),
                    username: localStorage.getItem("username")
            }
            });
            AlbumService.getUserAlbums(localStorage.getItem("username"))
            .then(res => {
                this.setState({
                    albums: res.data
                });
            });
        } else {
            
            UserService.getUserProfile(username).then(
                res => {
                    this.setState({
                        user: res.data.user,
                        albums: res.data.albums
                    });
                }
            );

            console.log(this.state)
            AlbumService.getUserAlbums(username)
            .then(res => {
                this.setState({
                    albums: res.data
                });
            });

        }
    }



    render() {
        return (
            <div>
                <Header />
                <div className="profile">
                    <div className="albums">
                        {
                            this.state.albums.map(album =>
                                
                                    <div className="album" style={{backgroundImage: "url(" + album.albumCover + ")"}} onClick={() => {this.props.history.push("/album/" + album.albumId);}}>
                                        <div className="blur">
                                        <p>{album.albumName}</p>
                                        </div>
                                    </div>
                            

                        )}
                    </div>
                    <div className="user_data">
                    <div>
                        <div className="user_avatar" style={{backgroundImage: "url(http://127.0.0.1:8080/api/v1/profile/" + this.state.user.userId +"/image/download/)"}} />
                        <h3 className="text-center text-white">@{this.state.user.username}</h3>
                        <p className="text-center text-white">email@mail.com</p>
                    </div>
                    {/* buttons */}
                    <div className="btns mx-auto">
                        <div className="btn  btn-outline-light" onClick={() => {this.props.history.push("/new/album");}}>Create Album</div>
                        <div className="btn btn-outline-light" onClick={() => {this.props.history.push('/settings')}}>Settings</div>
                    </div>
                    </div>
                </div>
            </div>
        )
    }
}
