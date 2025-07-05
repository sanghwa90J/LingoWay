import React, { useState } from 'react';
import { View, TextInput, Button, Alert, StyleSheet } from 'react-native';
import axios from 'axios';
import { useRouter } from 'expo-router';

export default function RegisterScreen() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');

  const handleRegister = async () => {
    try {
      await axios.post('http://192.168.0.7:8080/api/users/register', {
        email, password, name,
      });
      Alert.alert('회원가입 성공');
      router.replace('/login');
    } catch (e) {
      Alert.alert('에러', e.response?.data?.message || '회원가입 실패');
    }
  };

  return (
    <View style={styles.container}>
      <TextInput placeholder="이메일" onChangeText={setEmail} style={styles.input} />
      <TextInput placeholder="비밀번호" onChangeText={setPassword} secureTextEntry style={styles.input} />
      <TextInput placeholder="이름" onChangeText={setName} style={styles.input} />
      <Button title="회원가입" onPress={handleRegister} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { padding: 20 },
  input: { borderWidth: 1, padding: 10, marginBottom: 10 },
});