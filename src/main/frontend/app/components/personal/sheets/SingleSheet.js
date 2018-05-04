import React from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { withStyles } from 'material-ui/styles';
import Typography from "material-ui/Typography";
import shortid from "shortid";

const styles = theme => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper
    }
});

function TabContainer(props) {
    return (
        <Typography variant="subheading" style={{ padding: 8 * 3 }}>
            {props.children}
        </Typography>
    );
}


TabContainer.propTypes = {
    children: PropTypes.node.isRequired
};


function SheetEntry(props) {
   return (<tr>
            <td>{props.entry.expenseName}</td>
            <td>{props.entry.expenseValue}</td>
       </tr>);
}

@connect((store) => {
    return {
        sheets: store.sheets.sheets,
        selectedSheet: store.sheets.selectedSheet
    };
})
class SingleSheet extends React.Component {


    getContentsOfASheet = () => {

        const sheet = this.props.sheets[0].sheetList[this.props.selectedSheet].sheetEntries;
        return (
            <table>
                <thead>
                    <tr>
                        <th>Expense name</th>
                        <th>Expense value</th>
                    </tr>
                </thead>
                <tbody>
                    {sheet.map((entry) =>
                        <SheetEntry key={shortid.generate()} entry={entry}/>)
                    }
                </tbody>
            </table>
        )

    }

    render() {
        return (
            <div>
                <TabContainer>Item {this.props.selectedSheet}</TabContainer>
                {this.getContentsOfASheet()}
            </div>
        );
    }
}

SingleSheet.propTypes = {
    classes: PropTypes.object.isRequired
};


export default withStyles(styles)(SingleSheet);
