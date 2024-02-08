/**
	1. 게시물 수정
 	2. 게시물 삭제
 **/

//게시물 수정
function imageUpdate(imageId, userId, event){
	event.preventDefault();
	
	let formData = new FormData($("#upload-form")[0]);
	
	console.log(formData);
	
	$.ajax({
		type: "put",
		url: `/api/image/board/${imageId}`,
		data: formData,
		contentType: false,
		processData: false,
		dataType: "json"
	}).done(res=>{
		console.log("update successful", res);
		alert("이미지가 수정되었습니다.");
		location.href=`/user/${userId}`;
	}).fail(error=>{
		if(error.responseJSON == null){
			alert(error.responseJSON.message);
		}else{
			alert("이미지 수정이 실패하였습니다. 원인 : " + 
			JSON.stringify(error.responseJSON.data));
		}
	});
			
}

// 게시물 삭제
//이미지 삭제 
function deleteImage(imageId, userId){
	$.ajax({
		type:"delete",
		url:`/api/image/${imageId}`,
		dataType:"json"
	}).done(res=>{
		alert("이미지가 삭제 되었습니다.");
		location.href=`/user/${userId}`;
	}).fail(error=>{
	}).fail(error=>{
		console.log("오류", error);
	})
}