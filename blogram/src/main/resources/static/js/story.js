/**
	1. 현재 로그인한 사용자 아이디
	2. 스토리 로드하기
	3. 스토리 스크롤 페이징하기
	4. 좋아요, 안좋아요
	5. 댓글쓰기
	6. 댓글삭제
 **/

// 현재 로그인한 사용자 아이디
let principalId = $("#principalId").val();

// 스토리 로드하기
let page = 0;

function storyLoad(){
	$.ajax({
		url: `/api/image?page=${page}`,
		dataType:"json"
	}).done(res=>{
		console.log(res);
		res.data.content.forEach((image)=>{
			let storyItem = getStoryItem(image);
			$("#storyList").append(storyItem);
		});
	}).fail(error=>{
		console.log(error);
	});
}

storyLoad();

function getStoryItem(image){
	let item = `<div class="story-list__item">
		<div class="sl__item__header">
		<div>
			<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
				onerror="this.src='/images/person.jpeg'" />
		</div>
		<div>${image.user.name}</div>
		</div>

		<div class="sl__item__img">
			<img src="/upload/${image.postImageUrl}" />
		</div>

		<div class="sl__item__contents">
			<div class="sl__item__contents__icon">
			
			<button>
		`;
		
	if(image.likeState){
		item += ` <i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
	}else{
		item += `<i class="far fa-heart far" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
	}
	
	item += `
		</button>
		</div>

		<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount}</b>likes</span>

		<div class="sl__item__contents__content">
			<p>${image.caption}</p>
		</div>
		<div id="storyCommentList-${image.id}">`;
		
	/*image.comments.forEach((comment)=>{
		item += `<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
				<p>
					<b>${comment.user.username} :</b> ${comment.content}
				</p>`;
				
	if(principalId == comment.user.id){				
		item += `<button onclick="deleteComment(${comment.id})">
					<i class="fas fa-times"></i>
				</button>
		`;
	}
	
	item += `
		</div>`;
	});
	*/
	item+=`
		</div>
	
		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
			<button type="button" onClick="addComment(${image.id})">게시</button>
		</div>
	
		</div>
	</div>`;
	
	return item;
}

// 스토리 스크롤 페이징하기
$(window).scroll(() => {
	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
	
	console.log(checkNum);
	
	if(checkNum < 1 && checkNum > -10){
		page++;
		storyLoad();
	}
});

//좋아요, 안좋아요
function toggleLike(imageId){
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	if(likeIcon.hasClass("far")){
		$.ajax({
			type:"post",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) + 1;
			console.log("좋아요카운트 증가", likeCount);
			
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error=>{
			console.log("오류", error);
		});
	}
	
	else{
		$.ajax({
			type:"delete",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) - 1;
			console.log("좋아요카운트 감소", likeCount);
			
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error=>{
			console.log("오류", error);
		})
	}
}