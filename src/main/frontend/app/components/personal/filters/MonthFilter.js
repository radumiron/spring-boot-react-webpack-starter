import React from "react";
import { connect } from "react-redux";
import DrowdownList from 'react-widgets/lib/DropdownList';

import { fetchMonths, selectMonth} from "../../../actions/personal/filters/filtersActions"

@connect((store) => {
    return {
        months: store.filters.months,
        selectedYear: store.filters.selectedYear
    };
})
export default class MonthFilter extends React.Component {

    constructor() {
        super();
    }
    shouldComponentUpdate(nextProps, nextState) {
        console.log("MonthsFilter: shouldComponentUpdate:", nextProps.selectedYear, this.props.selectedYear);
        if (nextProps.selectedYear != this.props.selectedYear) {
            this.props.dispatch(fetchMonths(nextProps.selectedYear));
            return true;
        }
        if (nextProps.months.length > 0) {
            return true;
        }

        return false;
    }

    /*componentWillMount() {
        console.log("MonthsFilter: componentWillMount: selectedYear", this.props.selectedYear);
        if (this.props.selectedYear) {
            this.props.dispatch(fetchMonths(this.props.selectedYear));
        }
    }*/

    handleMonthChange = (month) => {
        this.props.dispatch(selectMonth(month));
        //this.props.dispatch(fetchSheets(this.props.selectedYear, month));
    }

    render() {
        console.log("MonthsFilter: render: selectedYear", this.props.selectedYear);
        return (
            <div>
                <DrowdownList data={this.props.months} onChange = {value => this.handleMonthChange(value)}/>
            </div>
        );
    }
}