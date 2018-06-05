<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring MVC Board</title>
<!-- 현재 기기의 너비에 맞춰서 출력, 기본크기 1배, 최대 크기 1배, 확대축소 못하게
	모바일 웹 애플리케이션에서 주로 이용하는 옵션 -->
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<!-- 부트스트랩 스타일시트 파일링크
	contextPath는 절대경로를 만들기 위해서 추가함 -->
<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<!-- IE9 이하 버전에서 HTML5의 시멘틱 태그를 사용하기 위한 설정 -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.comrespond/1.4.2respond.min.js"></script>
    <![endif]-->

</head>
<!-- jQuery설정 -->
<script src="${pageContext.request.contextPath}/resources/jquery/jquery.1.12.4.min.js"></script>
<body class="skin-blue sidebar-mini" style="margin:auto 5%;">
	<div class="wrapper">
		<header class="main-header">
			<div class="page-header">
				<h1><a href="${pageContext.request.contextPath }" style="color:black; text-decoration:none;">Spring MVC 게시판</a></h1>
			</div>
		</header>
	</div>
	<aside class="main-sidebar">
		<section class="sidebar">
			<ul class="nav nav-tabs">
				<li role="presentation" class="active"><a href="#">메인</a></li>
				<li role="presentation"><a href="${pageContext.request.contextPath }/board/list?page=1">목록보기</a></li>
				<li role="presentation"><a href="${pageContext.request.contextPath }/board/write">게시물 쓰기</a></li>
				<c:if test="${user == null }">
					<li role="presentation"><a href="${pageContext.request.contextPath}/user/register">회원가입</a></li>
					<li role="presentation"><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
				</c:if>
				<c:if test="${user != null }">
					<li role="presentation"><img width="20px" src ="${pageContext.request.contextPath}/userimage/${user.image}">${user.nickname }님 </li>
					<li role="presentation"><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
				</c:if>
			</ul>
		</section>
	</aside>
	<div>