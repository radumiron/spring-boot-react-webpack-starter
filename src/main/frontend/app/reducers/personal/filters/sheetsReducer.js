export default function reducer(state = {
    sheets: [],
    fetching: false,
    fetched: false,
    error: null
}, action) {
    switch (action.type) {
        case "FETCH_SHEETS": {
            return {
                ...state,
                fetching: true,
                fetched: false
            }
        }
        case "FETCH_SHEETS_FULFILLED": {
            return {
                ...state,
                fetching: false,
                fetched: true,
                sheets: action.payload
            }
        }
        case "FETCH_SHEETS_REJECTED": {
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