import { View, StyleSheet } from "react-native";
import YoYoText from "../../../constants/YoYoText";
import YoYoIcon from "../../../assets/svg/YoYoIcon.svg";
import { MainStyle } from "../../../constants/style";
import format from "../../../util/format";
export default function MoneyCard({ account, money }) {
    return (
        <View style={styles.moneyContainer}>
            <View style={styles.payContainer}>
                <YoYoIcon />
                <YoYoText type="md" logo>
                    pay
                </YoYoText>
            </View>
            <YoYoText
                type={account ? "subTitle" : "md"}
                bold
                color={MainStyle.colors.white}
            >
                {money >= 0
                    ? format.formatNumber(money) + "원"
                    : "등록된 계좌가 없습니다."}
            </YoYoText>
        </View>
    );
}
const styles = StyleSheet.create({
    moneyContainer: {
        flexDirection: "row",
        width: "100%",
        justifyContent: "space-between",
        paddingVertical: 12,
        paddingHorizontal: 24,
        backgroundColor: MainStyle.colors.hover,
        alignItems: "center",
        borderRadius: 8,
    },
    payContainer: {
        flexDirection: "row",
        alignItems: "center",
        gap: 4,
    },
});
