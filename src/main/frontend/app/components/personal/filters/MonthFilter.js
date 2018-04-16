import React from "react";
import DrowdownList from 'react-widgets/lib/DropdownList';

export default class MonthFilter extends React.Component {

    render() {
        return (
            <div>
                <DrowdownList data={this.props.months}/>
            </div>
        );
    }
}