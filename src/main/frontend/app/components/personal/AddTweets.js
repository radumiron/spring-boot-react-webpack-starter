import React from "react";
import { connect } from "react-redux";

import { addTweet } from "../../actions/tweetsActions"

@connect((store) => {
    return {
        tweets: store.tweets.tweets,
    };
})
export default class AddTweets extends React.Component {

    constructor() {
        super();
        this.state = {
            tweetText: ''
        }
    }

    handleTweetTextChange = (evt) => {
        this.setState({tweetText: evt.target.value});
    }

    handleSubmit() {
        var tweetText = this.state.tweetText;
        console.log("dispatching tweet", tweetText);
        this.props.dispatch(addTweet(tweetText));
    }

    render() {
        return <div>
                    <input
                            type="text"
                            placeholder="Tweet text"
                            onChange={this.handleTweetTextChange}
                        />
                   <input type="submit" value="Submit" onClick={this.handleSubmit.bind(this)}/>
        </div>
    }
}