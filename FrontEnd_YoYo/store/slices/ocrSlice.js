import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    ocrData: [],
};

const ocrSlice = createSlice({
    name: "ocr",
    initialState,
    reducers: {
        setOcrData(state, action) {
            state.ocrData = action.payload;
        },
        setOneOcrData(state, action) {
            const { idx, newData } = action.payload;
            if (idx >= 0 && idx < state.ocrData.length) {
                state.ocrData[idx] = newData;
            }
        },
    },
});
export const { setOcrData, setOneOcrData } = ocrSlice.actions;

export default ocrSlice.reducer;
