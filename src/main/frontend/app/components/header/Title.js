import React from "react";

class Title extends React.Component {

    constructor() {
        super();
    }

    getName() {
        return "Radu";
    }

    render() {
        return (
            <h1>This is {this.props.title}</h1>
        );
    }
}

export default Title;