/**
	유저 프로파일 페이지
	1. 유저 프로파일 페이지 구독하기, 구독취소
	2. 구독자 정보 모달 보기
	3. 구독자 정보 모달에서 구독하기, 구독취소
	4. 유저 프로픨 사진 변경
	5. 사용자 정보 메뉴 열기, 닫기
	6. 사용자 정보(회원정보, 로그아웃, 닫기) 모달
	7. 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
	8. 구독자 정보 모달 닫기	
**/

//유저 프로파일 사진 변경
function profileImageUpload(pageUserId, principalId) {
	/*console.log("pageUserId", pageUserId);
	console.log("principalId", principalId);*/
	
	if(pageUserId != principalId){
		alert("프로필 사진을 수정할 수 없는 유저입니다.");
		return;
	}
	
	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}
		//서버에 이미지를 전송
		let profileImageForm = $("#userProfileImageForm")[0];
		console.log(profileImageForm);
		
		//FormData 객체를 이용하면 form 태그의 필드와 그 값을 나타내는 일련의 key/value 쌍을 담을 수 있다.
		let formData = new FormData(profileImageForm); 
		
		$.ajax({
			type:"put",
			url:`/api/user/${principalId}/profileImageUrl`,
			data:formData, 
			contentType:false, //필수 : x-www-form-urlencoded로 파싱되는 것을 방지 (그림으로 넣으려면)
			processData:false, //필수 : contentType를 false를 줬을 때 QueryString으로 자동 설정되기 때문에 해제해야 함.
			entype:"multipart/form-data",
			dataType:"json"
		}).done(res=>{
			// 사진 전송 성공시 이미지 변경
			let reader = new FileReader();
			reader.onload = (e) => {
				$("#userProfileImage").attr("src", e.target.result);
			}
			reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
		}).fail(error=>{
			console.log("오류", error);
		})
		
		
	});
}

//사용자 정보 메뉴 열기 닫기
function popup(obj){
	$(obj).css("display", "flex");
}
function closePopup(obj){
	$(obj).css("display", "none");
}

//사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

//사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage(){
	$(".modal-image").css("display", "none");
}

//구독자 정보 모달 닫기
function modalClose(){
	$(".modal-subscribe").css("display", "none");
	location.reload();
}