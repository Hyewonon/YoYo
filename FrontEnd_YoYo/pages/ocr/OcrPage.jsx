import { View, Alert, Image, StyleSheet } from "react-native";
import React, { useState } from "react";
import { launchCameraAsync, useCameraPermissions, PermissionStatus } from "expo-image-picker";
import YoYoText from "../../constants/YoYoText";
import Button from './../../components/common/Button';
import { sendOcrImage } from "../../apis/https/transactionApi";
import { useDispatch } from "react-redux";
import { setOcrData } from "../../store/slices/ocrSlice";

export default function OcrPage({ navigation }) {
  const [pickedImage, setPickedImage] = useState();
  const [cameraPermissionInformation, requestPermission] = useCameraPermissions();
  const dispatch = useDispatch()
  async function verifyPermissions() {
    if (cameraPermissionInformation.status === PermissionStatus.UNDETERMINED) {
      const permissionResponse = await requestPermission();
      return permissionResponse.granted;
    }
    if (cameraPermissionInformation.status === PermissionStatus.DENIED) {
      Alert.alert("Insufficient Permissions", "사용하려면 허용을 해라.");
      return false;
    }
    return true;
  }

  async function takeImageHandler() {
    const hasPermission = await verifyPermissions();
    if (!hasPermission) {
      return;
    }
    const image = await launchCameraAsync({
      allowsEditing: true,
      quality: 0.5,
    });
    if (!image.canceled) {
      setPickedImage(image.assets[0].uri);
    }
  }
  async function sendImage() {

    try {
      // const imageUri = Image.resolveAssetSource(require('../../assets/ocrtest2.jpg')).uri; // URI 가져오기
      const response = await sendOcrImage(pickedImage);
      const ocrList = response.map((item, idx) => ({
        id: idx,
        oppositeId: 0,
        amount: item.amount,
        description: item.description,
        memberId: item.memberId,
        name: item.name,
        relationStatus: item.relationStatus,
        relationType: item.relationType,
      }))
      dispatch(setOcrData(ocrList))
      navigation.navigate("OCRLIST");
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <View style={{ flex: 1 }}>
      <View style={styles.imageContainer}>
        {pickedImage ? (
          <Image source={{ uri: pickedImage }} style={styles.image} />
        ) : <><View style={{ marginTop: 48 }}><YoYoText type="md" bold center>아래와 같이 표의 테두리에 맞춰 사진을 잘라주세요.</YoYoText></View><View style={styles.exImageContainer}><Image source={require('../../assets/ocrEX.jpg')} style={styles.exImage} resizeMode="contain" />
          <View style={styles.redBox} /></View></>}
      </View>
      <View style={styles.rowContainer}>
        {pickedImage ? <>
          <Button type="fill" width="50%" onPress={takeImageHandler} radius={0}><YoYoText type="md" bold>다시 촬영</YoYoText></Button>
          <Button type="fill" width="50%" onPress={sendImage} radius={0}><YoYoText type="md" bold>인식하기</YoYoText></Button>
        </> :
          <Button type="fill" width="100%" onPress={takeImageHandler} radius={0}><YoYoText type="md" bold>촬영</YoYoText></Button>}
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  imageContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',


  },
  image: {
    width: "100%", // 원하는 이미지 너비
    height: "100%", // 원하는 이미지 높이
  },
  exImageContainer: {
    width: "100%",
    height: "100%",
    position: 'relative'
  },
  exImage: {
    width: "100%",
    height: '100%',

  },
  redBox: {
    position: 'absolute',
    borderWidth: 2,
    borderColor: 'red',
    width: "85%",
    height: "60%",
    backgroundColor: "transParent",
    top: "21.5%",
    left: "8%"
  },
  rowContainer: {
    flexDirection: 'row',

  }
});
