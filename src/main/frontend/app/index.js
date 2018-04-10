import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from "react-redux";
import '../style/style.css';
import store from "./store";

import 'react-widgets/dist/css/react-widgets.css';
import DrowdownList from 'react-widgets/lib/DropdownList';

import Layout from './components/Layout';

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
                <Layout />

                <div>
                    <h1 className="testblue">App working with hot loading</h1>
                    <div>
                        <DrowdownList
                        data={this.state.years}/>
                        <DrowdownList
                            data={this.state.months}/>
                        <DrowdownList
                            data={this.state.sheets}/>
                    </div>
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