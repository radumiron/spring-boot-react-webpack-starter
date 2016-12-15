'use strict';
import Box from 'grommet/components/Box';
import Value from 'grommet/components/Value';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
//const client = require('./client');
const client = require('./mock_client');

// end::vars[]

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {markets: []};
    }

    componentWillMount() {
        client({method: 'GET', path: 'http://localhost:9000/mock_markets/'}).done(response => {
           this.setState({markets: response.entity.markets});
        })
    }

    render() {
        return (
                <Box size="large">
                    <MarketList key="marketList" markets={this.state.markets}/>
                </Box>
            )
    }
}

class MarketList extends React.Component {
    render() {
        var markets = this.props.markets.map(market =>
            <Market key={market.name} market={market}/>
        );
        var boxes = [];
        for (var i = 0; i < markets.length; i++) {
            boxes.push(
                <Box direction="row"
                     justify="start"
                     align="center"
                     wrap={true}
                     pad="medium"
                     margin="small"
                     colorIndex="light-1" >
                    {markets[i]}
                </Box>)
        }
        return (
            <div>
                {boxes}
            </div>
        )
    }
}

class Market extends React.Component {
    render() {
        return (
            <Value value={this.props.market.name} colorIndex="light-1"/>
        )
    }
}


ReactDOM.render(
<App />,
    document.getElementById('react')
);