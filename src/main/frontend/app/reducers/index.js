import { combineReducers } from "redux";
import tweets from "./tweetReducer";
import user from "./userReducer";
import filters from "./personal/filters/filterReducer";

export default combineReducers({
    tweets,
    user,
    filters
})