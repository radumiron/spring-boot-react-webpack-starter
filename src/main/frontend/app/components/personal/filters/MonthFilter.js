import React from "react";
import { connect } from "react-redux";
import DrowdownList from 'react-widgets/lib/DropdownList';

@connect((store) => {
    return {
        months: store.filters.months
    };
})
export default class MonthFilter extends React.Component {

    handleMonthChange = (month) => {

    }

    render() {
        return (
            <div>
                <DrowdownList data={this.props.months}/>
            </div>
        );
    }
}