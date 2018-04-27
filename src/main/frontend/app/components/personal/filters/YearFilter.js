import React from "react";
import { connect } from "react-redux";
import DrowdownList from 'react-widgets/lib/DropdownList';

import { fetchYears, selectYear } from "../../../actions/personal/filters/filtersActions"

@connect((store) => {
    return {
        years: store.filters.years
    };
})
export default class YearFilter extends React.Component {

    constructor() {
        super();
    }

    componentWillMount() {
        //console.log("YearFilter: componentWillMount");
        this.props.dispatch(fetchYears());
    }

    handleYearChange = (year) => {
        this.props.dispatch(selectYear(year));
    }

    render() {
        //console.log("YearFilter: render");
        return (
            <div>
                <DrowdownList data={this.props.years} onChange = {value => this.handleYearChange(value)}/>
            </div>
        );
    }
}