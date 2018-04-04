import React from 'react';
import { applyMiddleware, createStore } from 'redux';

const reducer = (state = 0, action) => {
    switch (action.type) {
        case 'INCREMENT':
            return state + 1;
        case 'DECREMENT':
            return state - 1;
        case 'E':
            throw new Error("error");
        default:
            return state;
    }
}

const logger = (store) => (next) => (action) => {
    console.log("action fired", action);
    next(action);
}

const error = (store) => (next) => (action) => {
    try {
        next(action);
    } catch (e) {
        console.log("ERROR", e);
    }
}

const middleware = applyMiddleware(logger, error);

const store = createStore(reducer, 0, middleware);


store.subscribe(() => {
    console.log("store changed", store.getState());
});

store.dispatch({type: "INCREMENT", payload: 0});
store.dispatch({type: "INCREMENT", payload: 0});
store.dispatch({type: "INCREMENT", payload: 0});
store.dispatch({type: "INCREMENT", payload: 0});
store.dispatch({type: "INCREMENT", payload: 0});
store.dispatch({type: "E", payload: 0});

class Counter extends React.Component {

    constructor(props) {
        super(props);
        this.setState({value : store.getState()});
    }

    render() {
        return (
          <h1>Test {store.getState()}</h1>
        );
    }
}

export default Counter;