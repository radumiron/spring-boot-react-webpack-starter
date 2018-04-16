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