import React from "react"

import YearFilter from "./YearFilter";
import MonthFilter from "./MonthFilter";
import ExpenseTypeFilter from "./ExpenseTypeFilter";

export default class MainFilterComponent extends React.Component {

    componentWillMount() {
        let years = ["2014", "2015", "2016", "2017", "2018"];
        let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        let sheets = ["Rate", "Mancare", "Casa", "Oras", "Copil", "Garsoniera", "Anexe", "Total"];
        this.setState({years});
        this.setState({months});
        this.setState({sheets});
    }

    render() {
        return (
            <div>
                <YearFilter
                    years={this.state.years}/>
                <MonthFilter
                    months={this.state.months}/>
                <ExpenseTypeFilter
                    sheets={this.state.sheets}/>
            </div>
        );
    }
}