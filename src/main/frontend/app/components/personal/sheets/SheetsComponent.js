import React from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { withStyles } from 'material-ui/styles';
import AppBar from 'material-ui/AppBar';
import Tabs, { Tab } from 'material-ui/Tabs';
import Typography from "material-ui/Typography";

import { fetchSheets } from "../../../actions/personal/filters/sheetsActions";


function TabContainer(props) {
    return (
        <Typography component="div" style={{ padding: 8 * 3 }}>
            {props.children}
        </Typography>
    );
}

TabContainer.propTypes = {
    children: PropTypes.node.isRequired
};

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
        sheets: store.sheets.sheets
    };
})
class SheetsComponent extends React.Component {

    constructor() {
        super();
    }

    state = {
        value: 0,
    };

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

    handleActive = (tab) => {
        alert("A tab with this route property ${tab.props['data-route']} was activated.");
    }

    handleChange = (event, value) => {
        this.setState({ value });
    };

    render() {

        const { value } = this.state;

        if (this.props.fetched) {
            return (

                <div>
                    <h1 className="sheetsFetched">Sheets were fetched</h1>
                    <h2>Displaying sheets for {this.props.sheets[0].file}</h2>
                    <AppBar position="static">
                        <Tabs value={value} onChange={this.handleChange}>
                            <Tab label="Item One" />
                            <Tab label="Item Two" />
                        </Tabs>
                    </AppBar>
                    {value === 0 && <TabContainer>Item One</TabContainer>}
                    {value === 1 && <TabContainer>Item Two</TabContainer>}
                    {value === 2 && <TabContainer>Item Three</TabContainer>}
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

