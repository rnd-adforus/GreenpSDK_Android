## ChangeLog

### Version [3.5.7]
#### FIX
- 문의하기 기능 입력 필수 값 정정

### Version [3.5.6]
#### FIX
- Android OS 8 버전 버그 픽스

### Version [3.5.5]
#### FIX
- 리워드 포인트 폰트 색상 그린피 어드민에서 설정하는 기능 추가

### Version [3.5.4]
#### FIX
- (UAd SDK 적용 시)Goolge Application ID 확인 기능 오류 수정

### Version [3.5.3]
#### FIX
- androidx.fragment.app.Fragment$InstantiationException 오류 수정
- Android SDK 35 Target 정책에 맞추어 READ_MEDIA_IMAGES 권한 제거
- Android SDK 35 Target 정책에 맞추어 Activity 레이아웃 영역이 status bar를 침범하지 않도록 정정
- 다크모드 색상 테마 일부를 수정
- 초기화 시 UserId, AppCode가 빈 문자열로 설정시 초기화 되지 않도록 수정
- application 내 테마가 없어도 오퍼월이 동작되도록 오퍼월 Activity 테마를 지정

### Version [3.5.2]
#### FIX
- AdsU SDK 호출 오류 픽스 

### Version [3.5.1]
#### FIX
- 기 참여 이력이 소메뉴 전환 시 반영 되지 않고 다시 나타 나는 현상 픽스
- 더 정확한 데이터 없음 표시
- 광고 목록 검색 기능, 참여 완료로 광고 목록 갱신 상태 개선
- 멀티 리워드 타입 광고 지원
- 오퍼월 내 전역 UI 배치 및 이미지 수정
#### ADD
- 시스템 설정에 따른 다크모드 지원
- 그린피 어드민 설정에 따라 소메뉴 별 광고리스트가 별칭으로 통합하는 기능 지원
- 랜더링 개선, 메모리 성능 개선
- AdsU SDK dependency를 Optional하게 적용하여 오퍼월 내 Google Admob 광고가 게시되도록 지원
- 복수의 AppCode 사용을 위한 인터페이스 확장

### Version [3.4.2]
#### FIX
- 색상 표시 영역과 관련한 UI 버그 픽스

### Version [3.4.1]
#### FIX
- 내부 코드 개선을 위한 레거시 코드 정리

### Version [3.4.0]
#### ADD
- 개인정보동의 팝업 관련 그린피 어드민 설정을 통한 UI 컨트롤 기능 지원
- Android 플랫폼 형식에 맞지 않는 AppCode 입력시 초기화 실패 되도록 수정
- 미니 배너, 띠배너, 팝업배너 형식을 Deprecated 인터페이스로 변경
- 기타 데이터 수집과 관련한 버그 픽스

### Version [3.3.0]
#### ADD
- 광고 표시 유형 리스트/피드형 스위치 버튼이 그린피 어드민에서 ON/OFF 되도록 수정
- 문의하기 버튼이 그린피 어드민에서 ON/OFF 되도록 수정

### Version [3.2.0]
#### ADD
- 광고 검색 기능 추가
- 오퍼월 아이콘 그린피 어드민에서 ON/OFF하는 설정을 따르도록 수정
- 광고 포인트 소수점 1의 자리까지 표시
- 클릭체류형 기능 추가
- 대분류, 소분류 그린피 어드민에서 ON/OFF 되도록 수정
- 광고 표시 유형 리스트/피드형 초기 값이 그린피 설정을 따르도록
- 마이페이지 참여 광고 리스트 정렬 기준 변경 
#### FIX 
- 뒤로가기 엑션 버그 수정
- 화면 전환 버그 수정

### Version [3.1.2]
#### ADD
- 그린피 오퍼월, 그린피 띠배너 타입 Flutter 플랫폼 지원