import React, { Component } from 'react';
import Header from './header';
import '../styles/login.css';
import AlbumService from '../services/AlbumService';

export default class EditAlbum extends Component {


    constructor(props) {
        super(props)
    
        this.state = {
            album: {},
            file: null
        }
    }
    
    fileHandler = (e) => {
        this.setState({file: e.target.files[0]})
    }

    componentDidMount() {
        let id = window.location.pathname.split('/')[3];
        console.log(id)
		AlbumService.getAlbum(id)
		.then(res => {
			this.setState({
				album: res.data,
                // picas: res.data.picas,
                // author: res.data.user
			});
			//console.log(res.data);
		});
    }


    render() {
        return (
            <div>
                <Header />
                <div className="login-box">
                    <form >
                    {/*email*/}
                    <div className="user-box">
                        <input type="text" name value={this.state.album.albumName} />
                        <label>Album Name</label>
                    </div>
                    {/*password*/}
                    <div className="user-box">
                    
                        <div><img src={this.state.file != null? URL.createObjectURL(this.state.file) : this.state.album.albumCover} style={{width: "150px"}} /></div>
                        <input type="file" onChange={this.fileHandler} />
                        <label >Album Cover</label>

                    </div>
                    {/*submit or enter button*/}
                    <div className="user-box">
                        <button type="submit mx-auto">Edit Album</button>
                    </div>
                    </form>
                </div>
            </div>
        )
    }
}
