import axios from "axios";

export function fetchYears() {
    return function(dispatch) {
        dispatch({type: "FETCH_YEARS"});

        axios.get("http://localhost:8080/api/expense/year")
            .then((response) => {
                dispatch({type: "FETCH_YEARS_FULFILLED", payload: response.data});
            })
            .catch((err) => {
                dispatch({type: "FETCH_YEARS_REJECTED", payload: err});
            })
    }
}

export function fetchMonths(year) {
    return function (dispatch) {
        dispatch({type: "FETCH_MONTHS"});

        var monthsURL = "http://localhost:8080/api/expense/year/" + year;

        axios.get(monthsURL)
            .then((response) => {
                dispatch({type: "FETCH_MONTHS_FULFILLED", payload: response.data});
            })
            .catch((err) => {
                dispatch({type: "FETCH_MONTHS_REJECTED", payload: err});
            })
    }
}