import React from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { withStyles } from 'material-ui/styles';
import AppBar from 'material-ui/AppBar';
import Tabs, { Tab } from 'material-ui/Tabs';
import Typography from "material-ui/Typography";
import SingleSheet from "./SingleSheet";

import { fetchSheets, handleSelectSheet} from "../../../actions/personal/filters/sheetsActions";


var counter = 0;

const styles = theme => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper
    }
});

@connect((store) => {
    return {
        selectedMonth: store.filters.selectedMonth,
        selectedYear: store.filters.selectedYear,
        fetched: store.sheets.fetched,
        sheets: store.sheets.sheets,
        selectedSheet: store.sheets.selectedSheet
    };
})
class SheetsComponent extends React.Component {

    constructor() {
        super();
    }

    styles = {
        headline: {
            fontSize: 24,
            paddingTop: 16,
            marginBottom: 12,
            fontWeight: 400,
        },
    };

    shouldComponentUpdate(nextProps, nextState) {
        var year = nextProps.selectedYear;
        var month = nextProps.selectedMonth;
        var fetched = nextProps.fetched;

        //console.log("SheetsComponent: shouldComponentUpdate: Year", year, "month", month);
        var shouldUpdate = (year != null && month != null) && (year != this.props.selectedYear || month != this.props.selectedMonth);
        //console.log("SheetsComponent: shouldComponentUpdate: shouldUpdate", shouldUpdate);

        if (shouldUpdate) {
            this.props.dispatch(fetchSheets(year, month));
            return false;
        }

        return nextProps.fetched;
    }

    getListOfSheets = () => {
        const sheetsList = this.props.sheets[0].sheetList;
        console.log("counter", counter++, "my", sheetsList);

        return sheetsList;
    }

    handleChange = (event, sheet) => {
        this.props.dispatch(handleSelectSheet(sheet));
    };

    render() {

        if (this.props.fetched) {
            return (

                <div>
                    <h1 className="sheetsFetched">Sheets were fetched</h1>
                    <h2>Displaying sheets for {this.props.sheets[0].file}</h2>
                    <AppBar position="static">
                        <Tabs onChange={this.handleChange}>
                            {this.getListOfSheets().map((sheet) =>
                                <Tab key={sheet.name} label={sheet.name} />
                                )}
                        </Tabs>
                        <SingleSheet/>
                    </AppBar>
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

SheetsComponent.propTypes = {
    classes: PropTypes.object.isRequired
};

export default withStyles(styles)(SheetsComponent);

