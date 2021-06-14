import  { Component } from 'react'
import PicaService from './services/PicaService';
import './styles/index.css';
import './js/decor.js';
import search from './images/search-white.png';
import pin from './images/pin.png';
import Header from './components/header';

const logo = require('D:\\Python\\picland-front\\picland\\src\\images\\pin.png');

export default class App extends Component {


	constructor(props) {
		super(props)
	
		this.state = {
			 picas: [],
			 showCategoryLustBool : false
		}


		this.openCategoryList = this.openCategoryList.bind(this)
	}
	
	componentDidMount() {
		PicaService.getPicas()
		.then(res => {
			this.setState({
				picas: res.data
			});
			console.log(res.data);
		});
	}

	openCategoryList() {
		if (this.state.showCategoryLustBool) {
			document.getElementById("categoryList").style.opacity = '1';
			this.setState({
				showCategoryLustBool: false
			});
		} else {
			document.getElementById("categoryList").style.opacity = '0';
			this.setState({
				showCategoryLustBool: true
			});
		}
	}


	render() {
		return (
				<div>
					<Header />
					<div className="album-layout">
					{this.state.picas.map(pica =>
						<div className="element"  >
							<img src={pica.url} alt="HAHA" onClick={() => {this.props.history.push("/pica/" + pica.picaId);}} />
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
				</div>
		)
	}
}

