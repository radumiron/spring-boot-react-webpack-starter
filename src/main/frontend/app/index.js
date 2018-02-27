import React from 'react';
import ReactDOM from 'react-dom';
import '../style/style.css';

import 'react-widgets/dist/css/react-widgets.css';
import DrowdownList from 'react-widgets/lib/DropdownList';

class Main extends React.Component {
    constructor(props) {
        super(props);
        this.state = {markets: []};
    }

    render() {
    	return (
    		<div>
				<h1 className="testblue">App working with hot loading</h1>
				<div>
					<DrowdownList/>
				</div>
			</div>
		);
	}
}

ReactDOM.render(<Main/>,
	document.querySelector('.container'));