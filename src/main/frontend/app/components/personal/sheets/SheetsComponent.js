import React from "react";
import { connect } from "react-redux";

import { fetchSheets} from "../../../actions/personal/filters/sheetsActions"

@connect((store) => {
    return {
        selectedMonth: store.filters.selectedMonth,
        selectedYear: store.filters.selectedYear,
        fetched: store.sheets.fetched,
        sheets: store.sheets.sheets
    };
})
export default class SheetsComponent extends React.Component {

    constructor() {
        super();
    }

    shouldComponentUpdate(nextProps, nextState) {
        var year = nextProps.selectedYear;
        var month = nextProps.selectedMonth;
        var fetched = nextProps.fetched;

        console.log("SheetsComponent: shouldComponentUpdate: Year", year, "month", month);
        var shouldUpdate = (year != null && month != null) && (year != this.props.selectedYear || month != this.props.selectedMonth);
        console.log("SheetsComponent: shouldComponentUpdate: shouldUpdate", shouldUpdate);

        if (shouldUpdate) {
            this.props.dispatch(fetchSheets(year, month));
            return false;
        }

        return nextProps.fetched;
    }


    render() {

        /*var year = this.props.selectedYear;
        var month = this.props.selectedMonth;
        var fetched = this.props.fetched;

        console.log("SheetsComponent: render: Year", year, "month", month);*/

        console.log("SheetsComponent: render");
        if (this.props.fetched) {
            return (
                <div>
                    <h1 className="sheetsFetched">Sheets were fetched</h1>
                    <h2>Displaying sheets for {this.props.sheets[0].file}</h2>
                </div>
            );
        }
        return (
            <div>
                <h1 className="sheetsNotFetched">Sheets not fetched yet</h1>
            </div>
        );
    }
}