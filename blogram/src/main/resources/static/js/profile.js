

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

//구독자 정보 모달 닫기
function modalClose(){
	$(".modal-subscribe").css("display", "none");
	location.reload();
}