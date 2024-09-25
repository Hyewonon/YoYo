import { View, Alert, Image, StyleSheet } from "react-native";
import React, { useState } from "react";
import { launchCameraAsync, useCameraPermissions, PermissionStatus } from "expo-image-picker";
import YoYoText from "../../constants/YoYoText";
import Button from './../../components/common/Button';

export default function OcrPage({ navigation }) {
  const [pickedImage, setPickedImage] = useState();
  const [cameraPermissionInformation, requestPermission] = useCameraPermissions();

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

  return (
    <View style={{ flex: 1 }}>
      <View style={styles.imageContainer}>
        {pickedImage ? (
          <Image source={{ uri: pickedImage }} style={styles.image} />
        ) : <YoYoText type="md" center>이미지가 없습니다.</YoYoText>}
      </View>
      <View style={styles.rowContainer}>
        {pickedImage ? <>
          <Button type="fill" width="50%" onPress={takeImageHandler} radius={0}><YoYoText type="md" bold>다시 촬영</YoYoText></Button>
          <Button type="fill" width="50%" onPress={() => navigation.navigate("OCRLIST")} radius={0}><YoYoText type="md" bold>인식하기</YoYoText></Button>
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
    justifyContent: 'center'
  },
  image: {
    width: "100%", // 원하는 이미지 너비
    height: "100%", // 원하는 이미지 높이
  },
  rowContainer: {
    flexDirection: 'row',

  }
});
