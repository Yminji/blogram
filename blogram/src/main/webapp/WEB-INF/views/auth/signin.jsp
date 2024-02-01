<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Blogram</title>
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
   integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.0/css/all.min.css" 
integrity="sha512-10/jx2EXwxxWqCLX/hHth/vu2KY3jCF70dCQB8TSgNjbCVAC/8vai53GfMDrO2Emgwccf2pJqxct9ehpzG+MTw==" 
crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
	<div class="container">
		<main class="loginMain">
			<section class="login">
				<article class="login__form__container">
					<div class="login__form">
                        <h1><img src="/images/logo.jpg" alt=""></h1>
	                 	<form class="login__input" action="/auth/signin" method="post">
	                 		<input type="text" name="username" placeholder="유저네임" required="required"/>
	                 		<input type="password" name="password" placeholder="비밀번호" required="required"/>
	                 		<button>로그인</button>
	                 	</form>
	                 	
	                 	<div class="login__horizon">
	                       <div class="br"></div>
	                       <div class="or">또는</div>
	                       <div class="br"></div>
	                   </div>
	                   
	                   <div class="login__facebook">
	                   	<button onclick="javascript:location.href='/oauth2/authorization/kakao'">
	                   		<i class="fa-solid fa-comments" style="color: #FFD43B;"></i>
	                   		<span style="color:#FFD43B;">Kakao Login</span>
	                   	</button>
	                   	<button onclick="javascript:location.href='/oauth2/authorization/facebook'">
	                   		<i class="fa-brands fa-facebook" style="color: #310ce9;"></i>
	                   		<span>Facebook Login</span>
	                   	</button>
	                   	<button onclick="javascript:location.href='/oauth2/authorization/naver'">
	                   		<i class="fa-solid fa-n" style="color: #109d54;"></i>
	                   		<span style="color:#109d54;">Naver Login</span>
	                   	</button>
	                   </div>
	                  
                   </div>
                   
                   <div class="login__register">
                        <span>계정이 없으신가요?</span>
                        <a href="/auth/signup">가입하기</a>
                    </div>
				</article>
			</section>
		</main>
	</div>
</body>
</html>