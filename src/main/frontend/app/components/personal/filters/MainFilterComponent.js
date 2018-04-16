import React from "react";
import { connect } from "react-redux";


import YearFilter from "./YearFilter";
import MonthFilter from "./MonthFilter";
import ExpenseTypeFilter from "./ExpenseTypeFilter";

import { fetchYears } from "../../../actions/personal/filters/filtersActions"

@connect((store) => {
    return {
        years: store.filters.years
    };
})
export default class MainFilterComponent extends React.Component {

    componentWillMount() {

        this.props.dispatch(fetchYears());
        //let years = ["2014", "2015", "2016", "2017", "2018"];
        /*let years = [];*/
        let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        let sheets = ["Rate", "Mancare", "Casa", "Oras", "Copil", "Garsoniera", "Anexe", "Total"];
        /*this.setState({years});*/
        this.setState({months});
        this.setState({sheets});
    }

    getYears() {
        return this.props.years;
    }

    render() {
        return (
            <div>
                <YearFilter years={this.props.years} />

               {/* <MonthFilter
                    months={this.state.months}/>
                <ExpenseTypeFilter
                    sheets={this.state.sheets}/>*/}
            </div>
        );
    }
}