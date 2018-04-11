import React from "react"

import AddTweets from "./AddTweets";
import FetchTweets from "./FetchTweets";

export default class Layout extends React.Component {

    render() {
        return (
            <div>
                <AddTweets />
                <hr />
                <FetchTweets />
            </div>
        );
    }
}