import React, { Component } from 'react'
import axiosInstance from '../axios';
import Header from './header';

export default class Settings extends Component {

    constructor(props) {
        super(props)
    
        this.state = {
            file: null,
            avatar: null
        }

        this.onFormSubmit = this.onFormSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.getImg = this.getImg.bind(this);
    }


    componentDidMount() {
        return axiosInstance.get("http://127.0.0.1:8080/api/v1/profile/" + localStorage.getItem("id") +"/image/download/")
        .then(res =>{
            this.setState({
                avatar: res
            });
        });
    }
    
    onFormSubmit(e){
        e.preventDefault();
        const formData = new FormData();
        formData.append('file',this.state.file);
        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        };
        axiosInstance.post("http://127.0.0.1:8080/api/v1/profile/image/upload/",formData, config)
            .then((response) => {
                alert("The file is successfully uploaded");
            }).catch((error) => {
        });
    }


    onChange(e) {
        this.setState({file:e.target.files[0]});
    }

    getImg() {
        return axiosInstance.get("http://127.0.0.1:8080/api/v1/profile/" + localStorage.getItem("id") +"/image/download/")
        .then(res =>{
            this.setState({
                avatar: res.data
            });
        });
        
    }



    render() {
        return (
            <div>
                <Header />
                <div className="profile">
                    <div className="login-box change_passw mt-5">
                    <form className="mt-5" action="${pageContext.request.contextPath}/log" method="post">
                        {/*password*/}
                        <div className="user-box">
                        <input type="password" name="password" placeholder="password" />
                        <input type="password" name="password" placeholder="retype password" />
                        <label>Change Password</label>
                        </div>
                        {/*submit or enter button*/}
                        <div className="user-box">
                        <button type="submit mx-auto">change Password</button>
                        </div>
                    </form>
                    </div>
                    <div className="login-box edit_profile mt-5">
                    <form onSubmit={this.onFormSubmit}>
                        <h4 className="text-center text-light mb-5">Edit profile</h4>
                        {/* Username */}
                        <div className="user-box">
                        <input type="text" name="text" />
                        <label>Username</label>
                        </div>
                        {/*Avatar*/}
                        <div className="user-box example-2 mb-5">
                        <div className="file-input">
                            <input type="file" id="file" name="myImage" onChange= {this.onChange} className="file" />
                            <label htmlFor="file">Choose image</label>
                        </div>
                        </div>
                        {/*submit or enter button*/}
                        <div className="user-box my-5">
                        <button type="submit mx-auto">edit profile</button>
                        </div>
                    </form>
                    </div>
                    <div className="user_data mb-5">
                    <div>
                        <div className="user_avatar" style={{backgroundImage: "url(http://127.0.0.1:8080/api/v1/profile/" + localStorage.getItem("id") +"/image/download/)"}} />
                        <h3 className="text-center text-white">@{localStorage.getItem("username")}</h3>
                        <p className="text-center text-white">email@mail.com</p>
                    </div>
                    {/* buttons */}
                    <div className="btns mx-auto">
                        <div className="btn btn-outline-light">Edit profile</div>
                        <div className="btn btn-outline-light">Change Password</div>
                    </div>
                    </div>
                </div>
            </div>
        )
    }
}
