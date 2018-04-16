import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from "react-redux";
import '../style/style.css';
import store from "./store";

import 'react-widgets/dist/css/react-widgets.css';


import Layout from './components/personal/Layout';
import ReactBasic from './components/ReactBasic';
import MainFilterComponent from "./components/personal/filters/MainFilterComponent";

class Main extends React.Component {
    constructor(props) {
        super(props);
        this.state = {markets: []};
    }

	componentWillMount() {
        let years = ["2014", "2015", "2016", "2017", "2018"];
        let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        let sheets = ["Rate", "Mancare", "Casa", "Oras", "Copil", "Garsoniera", "Anexe", "Total"];
        this.setState({years});
        this.setState({months});
        this.setState({sheets});
    }


    render() {
    	return (
    	    <div>
                <ReactBasic />
                <hr/>
                <Layout />
                <hr/>
                <div>
                    <h1 className="testblue">App working with hot loading</h1>
                    <MainFilterComponent />
                </div>

            </div>
		);
	}
}

ReactDOM.render(
    <Provider store={store}>
        <Main />
    </Provider>,
	document.querySelector('.container'));