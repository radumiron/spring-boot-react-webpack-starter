/**
 * Created by Radu on 12/6/2017.
 */
// (C) Copyright 2014-2016 Hewlett Packard Enterprise Development LP

import React, { Component } from 'react';
import { connect } from 'react-redux';
import App from 'grommet/components/App';
import Split from 'grommet/components/Split';

class MyComponent extends Component {

  constructor () {
    super();
    this._onResponsive = this._onResponsive.bind(this);
  }
  _onResponsive (responsive) {
    this.props.dispatch(navResponsive(responsive));
  }

  render() {
    return (
      <App centered={false}>
        <Split priority='left' flex="right"
          onResponsive={this._onResponsive}>

        </Split>
      </App>
    );
  }
}

let select = (state) => ({
  nav: state.nav,
  session: state.session,
  status: state.status
});

export default connect(select)(MyComponent);
