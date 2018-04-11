import React from "react";

import Title from "./header/Title";

class Header extends React.Component {

    constructor() {
        super();
    }

    getName() {
        return "Radu";
    }

    render() {
        return (
          <Title title={this.props.title}/>
        );
    }
}

export default Header;