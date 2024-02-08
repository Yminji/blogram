<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
		<!--사진 업로드페이지 중앙배치-->
        <main class="uploadContainer">
           <!--사진업로드 박스-->
            <section class="upload">
               <c:if test="${dto.image.user.id eq principal.user.id}">
               <!--사진업로드 로고-->
                <div class="upload-top">
                    <a href="/" class="">
                        <img src="/images/logo.jpg" alt="">
                    </a>
                    <p>게시물 수정</p>
                </div>
                <!--사진업로드 로고 end-->
                
                <!--사진업로드 Form-->
                <form id="upload-form" class="upload-form" onsubmit="imageUpdate(${dto.image.id}, ${principal.user.id}, event)" enctype="multipart/form-data" >
                    <input  type="file" name="file"  onchange="imageChoose(this)"/>
                    <div class="upload-img">
                        <img src="/upload/${dto.image.postImageUrl}" alt="" id="imageUploadPreview" />
                    </div>
                    
                    <!--사진설명 + 업로드버튼-->
                    <div class="upload-form-detail">
                   		 <input type="text" placeholder="사진설명" name="caption" value="${dto.image.caption}"/>
                        <button class="cta blue">수정하기</button>
                    </div>
                    <!--사진설명end-->
                    
                </form>
                
                
				<div class="upload-form">
					<div class="upload-form-detail">
					<button class = "cta red" onclick="deleteImage(${dto.image.id}, ${principal.user.id})">삭제하기</button>
					</div>
				</div>
			</c:if>	
			<c:if test="${dto.image.user.id ne principal.user.id}">
				<!--사진업로드 로고-->
                <div class="upload-top">
                    <a href="/" class="">
                        <img src="/images/logo.jpg" alt="">
                    </a>
                    <br>
                   <p>게시물</p>
                   <br>
                </div>
                <div class="upload-form">
               	<div class="upload-img">
                       <img src="/upload/${dto.image.postImageUrl}" alt="" id="imageUploadPreview" />
                </div>
                <div class="upload-form-detail">
               		 <input type="text" placeholder="사진설명" name="caption" value="${dto.image.caption}" readonly="required"/>
                </div>
                </div>
			</c:if>
                <!--사진업로드 Form-->
            </section>
            <!--사진업로드 박스 end-->
        </main>
        <br/><br/>
	
	<script src="/js/board.js" ></script>
   	
    <%@ include file="../layout/footer.jsp" %>