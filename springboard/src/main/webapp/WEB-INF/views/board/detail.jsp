<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp"%>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<section class="content">
	<div class="box">
		<div class="box-header">
			<h3 class="box-title">상세보기</h3>
		</div>
		<div class="box-body">
			<div class="form-group">
				<label>제목</label> <input type="text" name="title"
					class="form-control" value="${board.title}" readonly="readonly" />
			</div>
			<div class="form-group">
				<label>내용</label>
				<textarea name="content" rows="5" readonly="readonly"
					class="form-control">${board.content}</textarea>
			</div>
			<div class="form-group">
				<label>작성자</label> <input type="text" class="form-control"
					value="${board.nickname}" readonly="readonly" />
			</div>
		</div>
		<div class="box-footer">
			<button class="btn btn-success" id="mainbtn">메인</button>
			<c:if test="${user.email == board.email}">
				<button class="btn btn-warning" id="updatebtn">수정</button>
				<button class="btn btn-danger" id="deletebtn">삭제</button>
			</c:if>
			<button class="btn btn-primary" id="listbtn">목록</button>
			<button class="btn btn-info" id="writereply">댓글쓰기</button>
			<c:if test="${board.replycnt > 0 }">
				<button class="btn btn-info" id="replylist">댓글보이기</button>
			</c:if>
		</div>
	</div>
	<!-- 댓글 출력 영역 -->
	<div id="replydisp" style="margin-top:5px;">
	</div>
</section>
<%@ include file="../include/footer.jsp"%>
<script>
	//메인 버튼을 눌렀을 때 처리
	document.getElementById("mainbtn").addEventListener("click",function() {
		location.href = "../";
	});
	<c:if test = "${user.email == board.email}">
		//수정 버튼을 눌렀을 때 처리
		document.getElementById("updatebtn").addEventListener("click",function() {
			location.href = "update?bno=${board.bno}&page=${criteria.page}&perPageNum=${criteria.perPageNum}";
		});
		//삭제 버튼을 눌렀을 때 처리
		document.getElementById("deletebtn").addEventListener("click",function() {
			$(function(){
				$("#dialog-confirm").dialog({
					resizable : false,
					height : "auto",
					width : 400,
					modal : true,
					buttons : {
						"삭제" : function() {
							$(this).dialog("close");
							location.href = "delete?bno=${board.bno}&page=${criteria.page}&perPageNum=${criteria.perPageNum}";
						},
						"취소" : function() {
							$(this).dialog("close");
						}
					}
				});
			});
		});
	</c:if>
	//목록 버튼을 눌렀을 때 처리
	document.getElementById("listbtn").addEventListener("click",function() {
		location.href = "list?page=${criteria.page}&perPageNum=${criteria.perPageNum}";
	});
	// 댓글쓰기 버튼을 눌렀을 때 처리
	document.getElementById("writereply").addEventListener("click",function(){
		$(function(){
			$("#replyform").dialog({
				resizable : false,
				height : "auto",
				width : 400,
				modal : true,
				open : function(){
					$("#replytext").focus();
				},
				buttons : {
					"저장" : function(){
						$(this).dialog("close");
						replytext = $("#replytext").val();
						$.ajax({
							url : "../reply/write",
							data : {
								"bno" : "${board.bno}",
								"email" : "${user.email}",
								"nickname" : "${user.nickname}",
								"replytext":replytext
							},
							dataType : "json",
							success : function(data){
								console.log(data.result);
							}
						});
						getReply();
					},
					"취소" : function(){
						$(this).dialog("close");
						$("#replytext").val("");
					}
				}
			});
		});
	});
	// 접속한 유저의 이메일
	email = "${user.email}";
	<c:if test='${board.replycnt>0}' >
		// 댓글보이기 버튼을 눌렀을 때 처리
		dispreply = false;
		document.getElementById("replylist").addEventListener("click", function(){
			if(dispreply==false){
				// 함수 호출
				// 댓글 저장이나 수정 및 삭제 후에도 호출해야하기 때문에 함수로 만들어서 재사용
				getReply();
				$("#replylist").text("댓글감추기");
				dispreply = true;
			} else {
				$("#replydisp").html("");
				$("#replylist").text("댓글보이기");
				dispreply = false;
			}
		});
	</c:if>
	// 호출되는 함수들
	function getReply(){
		$.ajax({
			url:"../reply/list",
			data:{"bno" : "${board.bno}"},
			dataType:"json",
			success:function(data){
				// 출력하는 함수 호출
				// 하나의 영역에서 코드가 너무 길어지지 않도록 함수를 다시 호출
				display(data);
			}
		});
	};
	function display(data){
		// 출력 내용을 저장할 변수
		disp="";
		// data 배열을 순회
		$(data).each(function(idx,item){
			disp += "<div style='height:46px; border-top:1px solid grey; padding: 5px 0;'>";
			disp += "<label style='height:36px; line-height:36px;'>";
			disp += item.nickname + " : " + item.replytext;
			disp += "</label>";
			if(email==item.email){
				// 삭제버튼 생성
				// 삭제버튼이 여러개 될 수 있기 때문에
				// 버튼의 id를 구분할 수 있는 값으로 만들면 나중에 id로 구분할 수 있음
				// 여기에서는 id를 del댓글번호 가 됨
				disp += "<button class='btn btn-danger' ";
				disp += "id='del"+item.rno+"'";
				disp += "style='float:right; margin-left:5px;' onclick='del(this)'>";
				disp += "댓글삭제"
				disp += "</button>";
				// 댓글수정 버튼
				disp += "<button class='btn btn-warning' ";
				disp += "id='mod"+item.rno+"'";
				disp += "style='float:right' onclick='mod(this)'>";
				disp += "댓글수정"
				disp += "</button>";
			}
			disp += "</div>";
		});
		// 출력영역에 출력
		document.getElementById("replydisp").innerHTML = disp;
	};
	// 댓글삭제 버튼을 눌렀을 때 호출되는 함수
	function del(btn){
		// 댓글번호
		id = btn.id;
		rno = id.split("del")[1];
		$("#dialog-confirm").dialog({
			resizable : false,
			height : "auto",
			width : 400,
			modal : true,
			buttons : {
				"삭제" : function() {
					$(this).dialog("close");
					$.ajax({
						url:"../reply/delete",
						data:{"rno":rno},
						dataType:"json",
						success:function(){
							getReply();
						}
					});
				},
				"취소" : function() {
					$(this).dialog("close");
				}
			}
		});
	};
	// 댓글수정 버튼을 눌렀을 때 호출되는 함수
	function mod(btn){
		// 댓글번호
		id = btn.id;
		rno = id.split("mod")[1];
		$("#replyform").dialog({
			resizable : false,
			height : "auto",
			width : 400,
			modal : true,
			open : function(){
				/* $("#replytext").value(text); */
				$("#replytext").focus();
			},
			buttons : {
				"수정" : function(){
					$(this).dialog("close");
					replytext = $("#replytext").val();
					$.ajax({
						url : "../reply/update",
						data : {
							"rno" : rno,
							"replytext":replytext
						},
						dataType : "json",
						success : function(data){
							console.log(data.result);
						}
					});
					getReply();
				},
				"취소" : function(){
					$(this).dialog("close");
					$("#replytext").val("");
				}
			}
		});
	};
</script>
<!-- 삭제 대화상자 -->
<c:if test = "${user.email == board.email}">
	<div id="dialog-confirm" title="삭제 확인" style="display:none;">
		<p>
			<span class="ui-icon ui-icon-alert" style="float: left; margin: 12px 12px 20px 0;"></span>
			<span>한번 삭제한 자료는 복구할 수 없습니다. 정말 삭제하시겠습니까?</span>
		</p>
	</div>
</c:if>
<!-- 댓글쓰기 대화상자 -->
<div class="box-body" style="display:none;" id="replyform">
	<label for="nickname">작성자</label>
	<input class="form-control" type="text" id="nickname" value="${user.nickname }" readonly="readonly"/>
	<label for="replytext">댓글내용</label>
	<input class="form-control" type="text" id="replytext" placeholder="댓글 내용을 작성하세요."/>
</div>
</html>