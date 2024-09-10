import { Pressable, View } from "react-native";
import { Entypo } from "react-native-vector-icons";
import { MainStyle } from "../../constants/style";

export function CheckBox({ checked, onPress }) {
    let backgroundColor,
        checkColor = MainStyle.colors.white;
    if (checked) {
        backgroundColor = MainStyle.colors.main;
        checkColor = "#fff";
    }
    return (
        <Pressable onPress={onPress}>
            <View
                style={{
                    backgroundColor,
                    borderColor: MainStyle.colors.main,
                    borderRadius: 6,
                    borderWidth: 1,
                    width: 24,
                    height: 24,
                    justifyContent: "center",
                    alignItems: "center",
                }}
            >
                <Entypo name="check" size={20} color={MainStyle.colors.white} />
            </View>
        </Pressable>
    );
}
