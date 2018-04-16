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