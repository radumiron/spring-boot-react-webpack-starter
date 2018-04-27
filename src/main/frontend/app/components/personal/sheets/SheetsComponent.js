import React from "react";
import { connect } from "react-redux";
import Tabs from 'material-ui/Tabs';
import Tab from 'material-ui/Tabs';

import { fetchSheets } from "../../../actions/personal/filters/sheetsActions";

import baseTheme from 'material-ui/styles/baseThemes/lightBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';



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

        this.constructor.childContextTypes = {
            muiTheme: React.PropTypes.object.isRequired
        };
    }


    styles = {
        headline: {
            fontSize: 24,
            paddingTop: 16,
            marginBottom: 12,
            fontWeight: 400,
        },
    };

    getChildContext() {
        return { muiTheme: getMuiTheme(baseTheme) };
    }



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

    render() {

        if (this.props.fetched) {
            return (
                <div>
                    <h1 className="sheetsFetched">Sheets were fetched</h1>
                    <h2>Displaying sheets for {this.props.sheets[0].file}</h2>
                    <Tabs>
                        <Tab label="Item One" >
                            <div>
                                <h2 style={this.styles.headline}>Tab One</h2>
                                <p>
                                    This is an example tab.
                                </p>
                                <p>
                                    You can put any sort of HTML or react component in here. It even keeps the component state!
                                </p>
                                {/*<Slider name="slider0" defaultValue={0.5} />*/}
                            </div>
                        </Tab>
                        <Tab label="Item Two" >
                            <div>
                                <h2 style={this.styles.headline}>Tab Two</h2>
                                <p>
                                    This is another example tab.
                                </p>
                            </div>
                        </Tab>
                    </Tabs>
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

SheetsComponent.childContextTypes = {
    muiTheme: React.PropTypes.object.isRequired
};
