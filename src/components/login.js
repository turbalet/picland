import React, { useState } from 'react';
import axiosInstance from '../axios';
import { useHistory } from 'react-router-dom';
import '../styles/login.css';
import '../styles/index.css';


export default function SignIn() {
	const history = useHistory();
	const initialFormData = Object.freeze({
		username: '',
		password: '',
	});

	const [formData, updateFormData] = useState(initialFormData);

	const handleChange = (e) => {
		updateFormData({
			...formData,
			[e.target.name]: e.target.value.trim(),
		});
	};

	const handleSubmit = (e) => {
		e.preventDefault();
		console.log(formData);

		axiosInstance
			.post(`signin/`, {
				username: formData.username,
				password: formData.password,
			})
			.then((res) => {
				localStorage.setItem('access_token', res.data.access);
				localStorage.setItem('refresh_token', res.data.refresh);
				localStorage.setItem('username', res.data.username);
				localStorage.setItem("id", res.data.id);
				axiosInstance.defaults.headers['Authorization'] =
					'Bearer ' + localStorage.getItem('access_token');
				history.push('/');
				//console.log(res);
				//console.log(res.data);
			});
	};



	return (
		
			<div>
        <div className="head d-flex">
          <div className="logo text-uppercase text-white">picland</div>
        </div>
        <div className="login-box">
          <form >
            {/*email*/}
            <div className="user-box">
              <input type="text" name="username" onChange={handleChange} id="username" label="Username" required/>
              <label>Username</label>
            </div>
            {/*password*/}
            <div className="user-box">
              <input type="password" name="password" onChange={handleChange} id="password" label="Password" required/>
              <label>Password</label>
            </div>
            {/*submit or enter button*/}
            <div className="user-box">
              {/* <button type="submit mx-auto" onClick={handleSubmit}>login</button> */}
			  
            </div>
			<input type="submit" value="Login" onClick={handleSubmit} />
            <div className="user-box text-center text-white  mt-5">
              <p>if you are new one</p>
              <a href="/register" className="text-white">Sign Up</a>
            </div>
          </form>
        </div>
      </div>
		
	);
}