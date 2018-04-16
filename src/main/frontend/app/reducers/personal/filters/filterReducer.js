export default function reducer(state = {
    years: [],
    fetching: false,
    fetched: false,
    error: null
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
    }

    return state;
}