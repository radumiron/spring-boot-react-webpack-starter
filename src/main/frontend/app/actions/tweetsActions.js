import axios from "axios";

export function fetchTweets() {
    return function(dispatch) {
        dispatch({type: "FETCH_TWEETS"});

        /*
         http://rest.learncode.academy is a public test server, so another user's experimentation can break your tests
         If you get console errors due to bad data:
         - change "reacttest" below to any other username
         - post some tweets to http://rest.learncode.academy/api/yourusername/tweets
         */
        axios.get("http://rest.learncode.academy/api/radu/tweets")
            .then((response) => {
                dispatch({type: "FETCH_TWEETS_FULFILLED", payload: response.data})
            })
            .catch((err) => {
                dispatch({type: "FETCH_TWEETS_REJECTED", payload: err})
            })
    }
}

export function addTweet(text) {
    return function (dispatch) {
        dispatch({type: "ADD_TWEET"});

        axios.post("http://rest.learncode.academy/api/radu/tweets", {
            text: text
        })
            .then((response) => {
                console.log("added tweet", text);
                dispatch(fetchTweets());
            })
            .catch((err) => {
                console.error("cannot add tweet", text);
            })
    }
}

export function updateTweet(id, text) {
    return {
        type: 'UPDATE_TWEET',
        payload: {
            id,
            text,
        },
    }
}

export function deleteTweet(id) {
    return {
        type: 'DELETE_TWEET',
        payload: id
    }
}