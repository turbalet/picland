import React, { Component } from 'react';
import '../styles/index.css';
import '../styles/pic.css';
import pin from '../images/pin.png';
import Header from './header';
import AlbumService from '../services/AlbumService';
import Footer from './footer';

export default class album extends Component {

    constructor(props) {
        super(props)
    
        this.state = {
             album: {},
             picas: [],
             author: {}
        }
    }
    
    componentDidMount() {
        let id = window.location.pathname.split('/')[2];
        console.log(id)
		AlbumService.getAlbum(id)
		.then(res => {
			this.setState({
				album: res.data,
                picas: res.data.picas,
                author: res.data.user
			});
			//console.log(res.data);
		});
        console.log("Album")
        console.log(this.state.album)
	}


    render() {
        return (
            <div>
                <Header />
                <div>
                    <div className="album-info">
                    <h2 className="text-center">{this.state.album.albumName}</h2>
                    <div onClick={() => {this.props.history.push("/profile/" + this.state.author.username);}}>
                    <div className="owner d-flex justify-content-center pl-4">
                        <img style={{width: "100x"}} src={"http://127.0.0.1:8080/api/v1/profile/" + this.state.author.userId +"/image/download"} alt="" />
                    </div>
                    <div className="d-flex justify-content-center">
                        <p className="my-auto">@{this.state.author.username}</p>
                    </div>
                    </div>
                    {
                        this.state.author.username == localStorage.getItem("username") ? (
                            <div className="d-flex justify-content-center mt-3">
                                <div className="btn btn-outline-light" onClick={() => {this.props.history.push("/edit/album/" + window.location.pathname.split('/')[2])}}>edit</div>
                            </div>
                        ) : (<div></div>) 
                    }
                    </div>
                    <div className="album-layout">
                        {this.state.picas.map(pica =>
                            <div className="element">
                                <img src={pica.url} alt="HAHA" />
                                <div className="whenhover">
                                    <div onClick={this.openCategoryList} className="pin btn" style={{backgroundImage: "url(" + pin  + ")"}} ></div>
                                        <ul className="categoryList" id="categoryList">
                                            <li>food</li>
                                            <li>hot girl stuf</li>
                                            <li>hot girl stuf</li>
                                            <li>hot girl stuf</li>
                                            <li>hot girl stuf</li>
                                            <li>hot girl stuf</li>
                                        </ul>
                                </div>
                                <div className="info">
                                    <div className="user-img" style={{backgroundImage: "url(http://127.0.0.1:8080/api/v1/profile/" + localStorage.getItem("id") +"/image/download/)"}}></div>
                                    <div className="pica-title">
                                        <p className="text-light my-auto">{pica.title}</p>
                                    </div>
                                </div>
                            </div>
                            
                        )}
                    </div>
                    <br></br>
                    <br></br>
                    <br></br>
                    <br></br>
                </div>
                
                <Footer />         
            </div>
        )
    }
}
