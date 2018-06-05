<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>

<section class="content">
	<!-- 회원가입 -->
	<!-- method와 enctype은 파일업로드를 위해 설정
		파일이 업로드 되는 폼은 반드시 method="post" enctype="multipart/form-data"로 설정
		onsubmit에 함수를 연결한 건 폼의 데이터를 전송할 때 유효성검사를 하기 위해서 -->
	<form id="registerform" enctype="multipart/form-data" method="post"
		onsubmit="return check()">
		<p align="center">
		<table border="1" width="50%" height="80%" align='center'>
			<tr>
				<td colspan="3" align="center"><h2>회원 가입</h2></td>
			</tr>
			<tr>
				<td rowspan="5" align="center">
					<p></p> <img id="img" width="100" height="auto" border="1" /> <br />
					<br /> <input type='file' id="image" name="image" /><br />
				</td>
			</tr>
			<tr>
				<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;이메일</font></td>
				<!-- onblur는 포커스를 떠날 때
					confirmId()를 호출해서 email 중복검사 수행 -->
				<td>&nbsp;&nbsp;&nbsp;
					<input type="email" name="email" id="email" 
					size="30" maxlength=50 onblur="confirmId()" required="required" />
					<!-- 메시지 출력 영역 -->
					<div id="emailDiv"></div>
				</td>
			</tr>
			<tr>
				<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;비밀번호</font></td>
				<td>&nbsp;&nbsp;&nbsp;
					<input type="password" name="pw" id="pw" size="20" required="required" />
				</td>
			</tr>
			<tr>
				<td bgcolor="#f5f5f5">
					<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;비밀번호 확인</font>
				</td>
				<td>&nbsp;&nbsp;&nbsp; <input type="password" id="pwconfirm" size="20" required="required" />
				</td>
			</tr>
			<tr>
				<td width="17%" bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;이름</font></td>
				<td>&nbsp;&nbsp;&nbsp;
					<input type="text" name="nickname"
					size="20" pattern="([a-z, A-Z, 가-힣]){2,}" required="required"
					title="닉네임은 문자 2자 이상입니다." />
				</td>
			</tr>
			<tr>
				<td align="center" colspan="3">
					<p></p>
					<input type="submit" value="회원가입" class="btn btn-warning" />
					<input type="button" value="메인으로" class="btn btn-success" onclick="javascript:window.location='../'">
					<p></p>
				</td>
			</tr>
		</table>
	</form>
	<br /> <br />
</section>
<%@include file="../include/footer.jsp"%>
<script>
// 이메일 중복검사 통과여부를 저장할 변수
// 전송버튼을 눌렀을 때 이 값이 false 면 전송하지 않을것
var emailcheck = false;

// 이메일 중복체크 함수
function confirmId(){
	email = document.getElementById("email").value;
	emaildiv = document.getElementById("emailDiv");
	$.ajax({
		url:'emailcheck',
		data:{'email':email},
		dataType:'json',
		success:function(data){
			//alert(data);
			if(data.result==true){
				// 이메일이 중복되지 않은 경우
				emailcheck = true;
				emaildiv.innerHTML = "사용 가능한 이메일입니다.";
				emaildiv.style.color = 'blue';
			}else{
				// 이메일이 중복된 경우
				emaildiv.innerHTML = "중복된 이메일입니다.";
				emaildiv.style.color = 'red';
				emailcheck = false;
			}
		}
	});
}

// 이미지파일 선택했을 경우 미리보기
var image = document.getElementById("image");
var img = document.getElementById("img");
// 선택한 파일이름을 저장할 변수
var filename = '';
// image 에서 선택이 변경됐을 때 호출되는 함수
image.addEventListener("change", function(){
	readURL(this);
})
function readURL(input){
	//alert("테스트");
	// 선택한 파일명
	filename = input.files[0].name;
	// 이미지파일인지 확인
	//var ext = filename.substr(filename.length-3, filename.length);
	var point = filename.lastIndexOf(".");
	var ext = filename.substr(point+1,filename.length);
	//alert(ext);
	if(ext.toLowerCase() != 'gif' && ext.toLowerCase() != 'jpg' && ext.toLowerCase() != 'jpeg'&& ext.toLowerCase() != 'png'){
		// 이미지파일이 아니면
		alert("이미지파일을 선택하세요");
		return;
	}
	// 그림파일 내용 읽기
	var reader = new FileReader();
	reader.readAsDataURL(input.files[0]);
	// 그림파일의 내용을 전부 읽으면 img에 출력
	reader.onload = function(e){
		img.src = e.target.result;
	}
}

// form에서 submit 했을 때 호출되는 함수
// false를 리턴하면 서버로 전송되지 않음
function check(){
	// emailcheck의 값이 false면 서버로 전송하지 않음
	if(emailcheck == false){
		document.getElementById("emailDiv").innerHTML = "이메일 중복검사를 통과하지 못했습니다.";
		document.getElementById("emailDiv").styel.color = "red";
		return false;
	}
	// 비밀번호에 입력한 값과 비밀번호 확인 란에 입력한 값이 일치하지 않으면 전송하지 않음
	var pw = document.getElementById("pw").value;
	var pwconfirm = document.getElementById("pwconfirm").value;
	if(pw != pwconfirm){
		alert("비밀번호가 일치하지 않습니다!");
		return false;
		document.getElementById("pw").focus();
	}
	// 비밀번호는 숫자, 영문자, 특수문자 1개이상으로 8자 이상
	// 정규식 이용
	var p1 = /[0-9]/;
	var p2 = /[a-zA-Z]/;
	var p3 = /[~!@#$%^&*()]/;
	if(!p1.test(pw) || !p2.test(pw) || !p3.test(pw) || pw.length<8){
		alert("비밀번호는 8자 이상이어야 하고, 숫자, 영문자, 특수문자를 포함해야합니다.");
		return false;
		document.getElementById("pw").focus();
	}
}
</script>
