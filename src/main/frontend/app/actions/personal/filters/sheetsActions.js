import axios from "axios";

export function fetchSheets(year, month) {
    return function(dispatch) {
        dispatch({type: "FETCH_SHEETS", payload: {year, month}});

        var url = "http://localhost:8080/api/expense/year/" + year + "/" + month;
        axios.get(url)
            .then((response) => {
                dispatch({type: "FETCH_SHEETS_FULFILLED", payload: response.data});
            })
            .catch((err) => {
                dispatch({type: "FETCH_SHEETS_REJECTED", payload: err});
            })
    }
}
