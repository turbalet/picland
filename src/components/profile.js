import React, { Component } from 'react'
import '../styles/index.css';
import '../styles/profile.css';
import Header from './header';
import AlbumService from '../services/AlbumService';
import { useHistory } from 'react-router';

export default class Profile extends Component {


    constructor(props) {
        super(props)
    
        this.state = {
            albums: []         
        }
    }
    
    componentDidMount() {
        AlbumService.getUserAlbums(localStorage.getItem("username"))
        .then(res => {
            this.setState({
                albums: res.data
            });
        });
    }



    render() {
        return (
            <div>
                <Header />
                <div className="profile">
                    <div className="albums">
                        {
                            this.state.albums.map(album =>
                                <div className="album" style={{backgroundImage: "url(" + album.albumCover + ")"}}>
                                    <div className="blur">
                                    <p>{album.albumName}</p>
                                    </div>
                                </div>

                        )}
                    </div>
                    <div className="user_data">
                    <div>
                        <div className="user_avatar" style={{backgroundImage: "url(http://127.0.0.1:8080/api/v1/profile/" + localStorage.getItem("id") +"/image/download/)"}} />
                        <h3 className="text-center text-white">@{localStorage.getItem("username")}</h3>
                        <p className="text-center text-white">email@mail.com</p>
                    </div>
                    {/* buttons */}
                    <div className="btns mx-auto">
                        <div className="btn  btn-outline-light">Create Album</div>
                        <div className="btn btn-outline-light" onClick={() => {this.props.history.push('/settings')}}>Settings</div>
                    </div>
                    </div>
                </div>
            </div>
        )
    }
}
