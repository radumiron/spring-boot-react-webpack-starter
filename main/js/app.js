'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom')
//const client = require('./client');
const client = require('./mock_client');

// end::vars[]

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {markets: []};
    }

    componentDidMount() {
        client({method: 'GET', path: 'http://localhost:9000/mock_markets/'}).done(response => {
           this.setState({markets: response.entity.markets});
        })
    }

    render() {
        return (
                <MarketList markets={this.state.markets}/>
            )
    }
}

class MarketList extends React.Component {
    render() {
        var markets = this.props.markets.map(market =>
            <Market key={market.name} market={market}/>
        );
        return (
            <table>
                <tbody>
                    <tr>
                        <th>Market</th>
                    </tr>
                    {markets}
                </tbody>
            </table>
            )
    }
}

class Market extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.market.name}</td>
            </tr>
        )
    }
}


ReactDOM.render(
<App />,
    document.getElementById('react')
)