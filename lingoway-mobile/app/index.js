// app/index.js

import { View, Text, Button, StyleSheet } from 'react-native';
import { useRouter } from 'expo-router';

export default function HomeScreen() {
  const router = useRouter();

  return (
    <View style={styles.container}>
      <Text style={styles.title}>LingoWay에 오신 것을 환영합니다!</Text>
      <Button title="회원가입" onPress={() => router.push('/register')} />
      <Button title="로그인" onPress={() => router.push('/login')} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, justifyContent: 'center', alignItems: 'center', gap: 10 },
  title: { fontSize: 20, marginBottom: 20 },
});
