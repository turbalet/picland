import search from '../images/search-white.png';
import pin from '../images/pin.png';
import Header from './header';
import  { Component } from 'react'
import PicaService from '../services/PicaService';
import '../styles/index.css';
import '../js/decor.js';


const logo = require('D:\\Python\\picland-front\\picland\\src\\images\\pin.png');


export default class result extends Component {





    constructor(props) {
        super(props)
    
        this.state = {
             picas: []
        }
    }


    componentDidMount() {
		let word = this.props.location.search.split('=')[1];
        console.log(this.props.location.search.split('=')[1]);
		console.log(this.props);

		PicaService.searchPicas(word).then(res => {
			this.setState({
				picas: res.data
			});
		});
    }

	componentDidUpdate() {
		let word = this.props.location.search.split('=')[1];

		PicaService.searchPicas(word).then(res => {
			this.setState({
				picas: res.data
			});
		});
	}
    

    render() {
        return (
            <div>
                    <Header />
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
            </div>
        )
    }
}
