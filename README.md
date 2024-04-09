# blogram

## 소개
인스타그램(SNS)와 유사하게 클린 코드한 프로젝트입니다. 자신이 올린 사진과 게시글 공유와 댓글, 좋아요, 팔로우 기능을 가지고 있습니다.

## 기능
1. 인증 : Spring Security와 OAuth2.0 이용한 페이스북 로그인, 회원가입
2. 구독 : principalId로 구독리스트, 구독하기, 구독취소하기, 구독자 수 렌더링
3. 프로필페이지 : 이미지 업로드, 회원정보 수정, 유저 사진 변경, 게시물 수 렌더링
4. 스토리페이지 : 이미지리스트 렌더링, 페이징, 스크롤 페이징
5. 좋아요 : 좋아요 및 좋아요 취소, 좋아요 렌더링, 페이지별 좋아요 카운터
6. 인기페이지 : 좋아요 수에 따른 인기페이지
7. 댓글 : 댓글 쓰기, 삭제하기, Ajax 함수 이용해 뷰 렌더링
   
## 추가한 기능
1. 인증 : OAuth2.0 이용한 카카오톡, 네이버 로그인 연동
2. 스토리에서 유저 이미지 클릭하면 해당 유저 프로필로 이동
3. principalID와 이미지를 올린 유저가 동일할 때 삭제 가능
4. 프로필에서 ajax를 활용해서 바로 삭제 가능하게 했는데, 게시물 숫자가 동적으로 안변해서 변할 수 있게 변경
5. OAuth2.0로 로그인한 유저의 name이 중복될 수 있기에 name 뒤에 숫자 + 영어를 랜덤값으로 부여해줌
6. 스토리 수정 및 삭제

## 기술 스택
- HTML, CSS, JavaScript, Ajax, jQueury
- JAVA, SpringBoot, JSP
- MaraiaDB, JPA


![image](https://github.com/Yminji/blogram/assets/114499665/9597fe0e-a308-420f-990e-9c0fcd813bf6)
