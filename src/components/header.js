import React, { Component } from 'react';
import { withRouter } from 'react-router';
import search from '../images/search-white.png';

class Header extends Component {



	constructor(props) {
		super(props)
	
		this.state = {
			 value: "",
		}

		this.handleSubmit = this.handleSubmit.bind(this);
	}
	

	handleSubmit(e) {
		e.preventDefault();
		console.log(this.props);
		this.props.history.push("/search?word=" + this.state.value);
	}




	render() {
		return (
			<div>
				<div className="head d-flex justify-content-between">
					<a href="/" className="logo text-uppercase text-white">picland</a>
					<form className="search d-none d-sm-flex justify-content-center">
						<button type="submit" onClick={(e) => {this.handleSubmit(e)}} ><img src={search} /></button>
						<input type="text" value={this.state.value} onChange={(e) => {this.setState({value: e.target.value})}}  placeholder="Search" />
					</form>
					<a href="/profile" className="user-img"  style={{backgroundImage: "url(http://127.0.0.1:8080/api/v1/profile/" + localStorage.getItem("id") +"/image/download/)"}} />
					</div>
					<div className="head d-flex d-sm-none mx-auto">
					<form className="search mx-auto d-flex justify-content-center">
						<button type="submit"><img src={"https://cpng.pikpng.com/pngl/s/54-544986_icon-free-image-search-button-png-white-clipart.png"} alt="HAHA" />Search</button>
						<input type="text" placeholder="Search" />
					</form>
				</div>
			</div>
		)
	}
}

export default withRouter(Header);
