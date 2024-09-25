import { useFonts } from "expo-font";

export default function useFontsLoader() {
    const [fontsLoaded] = useFonts({
        OleoBold: require("../assets/fonts/OleoScriptSwashCaps-Bold.ttf"),
        NotoRegular: require("../assets/fonts/NotoSansKR-Regular.ttf"),
    });

    return fontsLoaded;
}
