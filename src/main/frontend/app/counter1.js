import { createLogger } from "redux-logger";
import thunk from "redux-thunk";
import React from 'react';
import axios from 'axios';
import promise from 'redux-promise-middleware';
import { applyMiddleware, createStore } from 'redux';


const initialState = {
    fetching: false,
    fetched: false,
    users: [],
    error: null,
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case "FETCH_USERS_PENDING": {
            return {...state, fetching: true};
            break;
        }
        case "FETCH_USERS_REJECTED": {
            return {...state, fetching: false, error: action.payload};
            break;
        }
        case "RECEIVE_FULFILLED": {
            return {...state, fetching: false, fetched: true, users: action.payload};
            break;
        }

    }
    return state;
}

const middleware = applyMiddleware(promise(), thunk, createLogger());

const store = createStore(reducer, middleware);


//Thunk example
store.dispatch((dispatch) => {
    dispatch({type: "FETCH_USERS_PENDING"});


    axios.get("http://rest.learncode.academy/api/radu/users")
        .then((response) => {
           dispatch({type: "FETCH_USERS_FULFILLED", payload: response.data});
        })
        .catch((err) => {
            dispatch({type: "FETCH_USERS_REJECTED", payload: err});
        });
});

//Promise example
store.dispatch({
    type: "FETCH_USERS",
    payload: axios.get("http://rest.learncode.academy/api/radu/users")
});


class Counter1 extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <h1>Test</h1>
        );
    }
}

export default Counter1;