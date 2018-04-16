import React from "react";
import { connect } from "react-redux";

import { fetchTweets } from "../../actions/tweetsActions"

@connect((store) => {
    return {
        tweets: store.tweets.tweets,
    };
})
export default class FetchTweets extends React.Component {
    constructor() {
        super();
    }

    fetchTweetsLocal() {
        this.props.dispatch(fetchTweets())
    }

    render() {
        const {tweets} = this.props;

        if (!tweets.length) {
            return (
                <div>
                    <h1>List of tweets</h1>
                    <button onClick={this.fetchTweetsLocal.bind(this)}>Load tweets</button>
                </div>
                )
        }
        const mappedTweets = tweets.map(tweet => <li key={tweet.id}>{tweet.text}</li>)

        return <div>
            <h1>List of tweets</h1>
            <ul>{mappedTweets}</ul>
         </div>
    }
}