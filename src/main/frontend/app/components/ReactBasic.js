import React from "react";

import Footer from "./Footer";
import Header from "./Header";


class ReactBasic extends React.Component {

    constructor() {
        super();
        this.state = {title: "Will"};
    }

    render() {

        setTimeout(() => {
            this.setState({title: "Bob"})
        }, 3000);

        return (
            <div>
                <Header title={this.state.title}/>
                <Footer />
            </div>
          //
        );
    }
}

export default ReactBasic;