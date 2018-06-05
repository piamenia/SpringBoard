<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp" %>

<section class="content">
	<div class="box">
		<div class="box-header with-border" id="address"></div>
		<c:if test="${user==null}">
			<div class="box-header with-border">
				<a href="user/login"><h3 class="box-title">로그인</h3></a>
			</div>
			<div class="box-header with-border">
				<a href="user/register"><h3 class="box-title">회원가입</h3></a>
			</div>
		</c:if>
		<c:if test="${user!=null }">
			<div class="box-header with-border">
				<a href="user/logout"><h3 class="box-title">로그아웃</h3></a>
			</div>
			<div class="box-header with-border">
				<a href="board/write"><h3 class="box-title">글쓰기</h3></a>
			</div>
		</c:if>
		<div class="box-header with-border">
			<a href="board/list"><h3 class="box-title">목록보기</h3></a>
		</div>		
	</div>
</section>
<%@ include file="include/footer.jsp" %>

<c:if test="${msg != null}">
	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link rel="stylesheet" href="/resources/demos/style.css">
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$("#dialog-message").dialog({
				modal : true,
				buttons : {
					Ok : function() {
						$(this).dialog("close");
					}
				}
			});
		});
	</script>
	<div id="dialog-message" title="" >
		<p>
			<span class="ui-icon ui-icon-circle-check"
				style="float: left; margin: 0 7px 50px 0;"></span> ${msg}
		</p>
	</div>
</c:if>

<script>
// 10초에 한번 받아오기
setInterval(function(){
	// 현재 접속한 브라우저의 위도와 경로
	navigator.geolocation.getCurrentPosition(function(position){
		lat = position.coords.latitude;
		lng = position.coords.longitude;
		//console.log(lat+", "+lng);
		// 위도와 경도를 하나의 문자열로 만들기
		loc = lat + "-" + lng;
		// alert(loc);
		// address라는 URL에 loc를 파라미터로 넘겨서 json 타입으로 데이터를 받아오는 ajax요청
		$.ajax({
			url:"address",
			data:{"loc":loc},
			dataType:"json",
			success:function(data){
				document.getElementById("address").innerHTML = "<h3>"+data.address.split(" ")[0]+"</h3>";
			}
		})
	})
},10000);
</script>