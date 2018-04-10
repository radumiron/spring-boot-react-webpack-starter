import React from "react"
import { connect } from "react-redux"

import { fetchUser } from "../actions/userActions"
import { addTweet, fetchTweets } from "../actions/tweetsActions"


@connect((store) => {
    return {
        user: store.user.user,
        userFetched: store.user.fetched,
        tweets: store.tweets.tweets,
    };
})
export default class Layout extends React.Component {
    componentWillMount() {
        this.props.dispatch(fetchUser())
    }

    fetchTweets() {
        this.props.dispatch(fetchTweets())
    }

    addTweet() {
        this.props.dispatch(addTweet(this.refs.tweetText));
    }

    render() {
        const { user, tweets } = this.props;

        return <div>
            <form onSubmit={this.addTweet()}>
                <label>Tweet text </label>
                <input type="text" ref="tweetText"/>
                <input type="submit" />
            </form>

        </div>

       /* if (!tweets.length) {
            return <button onClick={this.fetchTweets.bind(this)}>load tweets</button>
        }*/

        /*const mappedTweets = tweets.map(tweet => <li key={tweet.id}>{tweet.text}</li>)*/

       /* return <div>
            <h1>{user.name}</h1>
            <h1>List of tweets</h1>
           {/!* <ul>{mappedTweets}</ul>*!/}
        </div>*/
    }
}