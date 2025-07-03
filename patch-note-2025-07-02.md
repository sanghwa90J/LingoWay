# 🗓️ Patch Note - 2025-07-02

## ✅ 프로젝트 리셋 및 리브랜딩
- 기존 토익 앱 → LingoWay로 리브랜딩
- 기초 영어 ~ 토익까지 통합 학습 구조로 전환
- 영어 신문/동화 + 생활 회화 포함 확정

## ✅ 기술/설정
- 백엔드 Spring Boot 프로젝트 생성 완료
- MariaDB 연결 설정 완료
- Springdoc OpenAPI 적용 및 Swagger UI 확인
- Spring Security 로그인 기반 API 테스트 완료
- `/api/hello` 정상 호출 확인 (보안 통과 포함)

## ✅ 로그 설계 확정
- 백엔드: Logback 기반 파일 로그
- 프론트: 주요 이벤트/에러 → 서버 API로 전송 → DB 저장 방식 확정
