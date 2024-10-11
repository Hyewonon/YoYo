import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./slices/authSlice";
import ocrReducer from "./slices/ocrSlice";
export const store = configureStore({
    reducer: {
        auth: authReducer,
        ocr: ocrReducer,
    },
});
export default store;
