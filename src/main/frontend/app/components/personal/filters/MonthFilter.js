import React from "react";
import { connect } from "react-redux";
import DrowdownList from 'react-widgets/lib/DropdownList';

import { selectMonth, fetchSheets} from "../../../actions/personal/filters/filtersActions"

@connect((store) => {
    return {
        months: store.filters.months,
        selectedYear: store.filters.selectedYear
    };
})
export default class MonthFilter extends React.Component {

    handleMonthChange = (month) => {
        this.props.dispatch(selectMonth(month));
        this.props.dispatch(fetchSheets(this.props.selectedYear, month));
    }

    render() {
        return (
            <div>
                <DrowdownList data={this.props.months} onChange = {value => this.handleMonthChange(value)}/>
            </div>
        );
    }
}