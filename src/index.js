import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { Route, BrowserRouter as Router, Switch } from 'react-router-dom';
import App from './App';
import Register from './components/register';
import Login from './components/login';
import Logout from './components/logout';
import Profile from './components/profile';
import Settings from './components/settings';
import result from './components/result';
import album from './components/album';
import CreateAlbum from './components/create_album';
import EditAlbum from './components/edit_album';
import Pica from './components/pica';




const routing = (
	<Router>
		<React.StrictMode>
			<Switch>
				<Route exact path="/" component={App} />
				<Route path="/register" component={Register} />
				<Route path="/login" component={Login} />
				<Route path="/logout" component={Logout} />
				<Route path="/profile" component={Profile} />
				<Route path="/settings" component={Settings} />
				<Route path="/search" component={result} />
				<Route path="/album" component={album} />
				<Route path="/new/album" component={CreateAlbum} />
				<Route path="/edit/album" component={EditAlbum} />
				<Route path="/pica" component={Pica} />
			</Switch>
		</React.StrictMode>
	</Router>
);

ReactDOM.render(routing, document.getElementById('root'));

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
