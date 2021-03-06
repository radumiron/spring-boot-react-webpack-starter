export default function reducer(state = {
    years: [],
    months: [],
    fetching: false,
    fetched: false,
    error: null,
    selectedYear: null,
    selectedMonth: null
}, action) {
    switch (action.type) {
        case "FETCH_YEARS": {
            return {
                ...state,
                fetching: true
            }
        }
        case "FETCH_YEARS_FULFILLED": {
            return {
                ...state,
                fetching: false,
                fetched: true,
                years: action.payload
            }
        }

        case "FETCH_YEARS_REJECTED": {
            return {
                ...state,
                fetching: false,
                fetched: false,
                error: action.payload
            }
        }

        case "FETCH_MONTHS": {
            return {
                ...state,
                fetching: true
            }
        }
        case "FETCH_MONTHS_FULFILLED": {
            return {
                ...state,
                fetching: false,
                fetched: true,
                months: action.payload
            }
        }

        case "FETCH_MONTHS_REJECTED": {
            return {
                ...state,
                fetching: false,
                fetched: false,
                error: action.payload
            }
        }

        case "SELECT_YEAR":
            return {
                ...state,
                selectedYear: action.payload
            }

        case "SELECT_MONTH":
            return {
                ...state,
                selectedMonth: action.payload
            }
    }

    return state;
}