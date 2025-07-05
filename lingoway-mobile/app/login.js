import React, { useState } from 'react';
import { View, TextInput, Button, Alert, StyleSheet } from 'react-native';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useRouter } from 'expo-router';

export default function LoginScreen() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const router = useRouter();

  const handleLogin = async () => {
    try {
      const res = await axios.post('http://192.168.0.7:8080/api/users/login', {
        email, password
      });

      const token = res.data.token;
      await AsyncStorage.setItem('authToken', token);

      router.replace('/home'); // 로그인 후 홈으로 이동
    } catch (e) {
      Alert.alert('에러', e.response?.data?.message || '로그인 실패');
    }
  };

  return (
    <View style={styles.container}>
      <TextInput placeholder="이메일" onChangeText={setEmail} style={styles.input} />
      <TextInput placeholder="비밀번호" onChangeText={setPassword} secureTextEntry style={styles.input} />
      <Button title="로그인" onPress={handleLogin} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { padding: 20 },
  input: { borderWidth: 1, padding: 10, marginBottom: 10 },
});
