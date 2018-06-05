<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>목록보기</title>
</head>
<body>
	<%@include file="../include/header.jsp"%>
	<div class="box">
		<div class="box-header with-border">
			<c:if test="${msg == null}">
				<h3 class="box-title">게시판 목록보기</h3>
			</c:if>

			<c:if test="${msg != null}">
				<h3 class="box-title">${msg}</h3>
			</c:if>
		</div>
		<div class="box-header with-border">
			<!-- 페이지당 출력개수 설정하는 select -->
			<span>목록개수</span>
			<select id="count" class="form-control" style="width:200px; display:inline;">
				<option value="1" <c:out value="${map.pageMaker.criteria.perPageNum==1?'selected':''}"/>>
					1개씩 보기
				</option>
				<option value="2" <c:out value="${map.pageMaker.criteria.perPageNum==2?'selected':''}"/>>
					2개씩 보기
				</option>
				<option value="3" <c:out value="${map.pageMaker.criteria.perPageNum==3?'selected':''}"/>>
					3개씩 보기
				</option>
				<option value="4" <c:out value="${map.pageMaker.criteria.perPageNum==4?'selected':''}"/>>
					4개씩 보기
				</option>
			</select>
			<!-- 검색폼 -->
			<span>검색</span>
			<select name="searchType" id="searchType">
				<option value="n"
				 <c:out value="${map.pageMaker.criteria.searchType==null?'selected':'' }"/>
				>--</option>
				<option value="t"
				 <c:out value="${map.pageMaker.criteria.searchType=='t'?'selected':'' }"/>
				>제목</option>
				<option value="c"
				 <c:out value="${map.pageMaker.criteria.searchType=='c'?'selected':'' }"/>
				>내용</option>
				<option value="tc"
				 <c:out value="${map.pageMaker.criteria.searchType=='tc'?'selected':'' }"/>
				>제목+내용</option>
			</select>
			<input type="text" name="keyword" id="keyword" value="${map.pageMaker.criteria.keyword }"/>
			<input type="button" value="검색" id="searchbtn" class="btn btn-primary">
		</div>
		<script>
			// 검색버튼 눌렀을 때
			document.getElementById("searchbtn").addEventListener("click",function(){
				// select의 선택된 항목 찾아오기
				// 선택된 행번호
				x = document.getElementById("searchType").selectedIndex;
				// select의 모든 값을 배열로 가져오기
				y = document.getElementById("searchType").options;
				// keyword에 입력된 값
				keyword = document.getElementById("keyword").value;
				
				location.href = "list?page=1&perPageNum=${map.pageMaker.criteria.perPageNum}&searchType=" + y[x].value + "&keyword=" + keyword;
			});
		</script>
		<div class="box-body">
			<table class="table table-bordered table-hover">
				<tr>
					<th width="11%">글번호</th>
					<th width="46%">제목</th>
					<th width="16%">작성자</th>
					<th width="16%">작성일</th>
					<th width="11%">조회수</th>
				</tr>
				<c:forEach var="board" items="${map.list }">
					<tr>
						<td align="right">${board.bno}&nbsp;</td>
						<td>&nbsp; 
							<a href="detail?bno=${board.bno}&page=${map.pageMaker.criteria.page}&perPageNum=${map.pageMaker.criteria.perPageNum}&searchType=${map.pageMaker.criteria.searchType}&keyworkd=${map.pageMaker.criteria.keyword}">
								${board.title}
								<span class="badge bg-blue">[${board.replycnt }]</span>
								<c:if test="${board.replycnt > 0 }">
									<img src="../resources/hot.png" width="25px">
								</c:if>
							</a>
						</td>
						<td>&nbsp; <a href="../user/sendmail?email=${board.email}">${board.nickname}</a></td>
						<td>&nbsp; ${board.dispdate}</td>
						<td align="right">
							<span class="badge bg-blue">${board.readcnt}</span>&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<!-- 페이지 번호 출력영역 -->
		<div class="box-footer text-center">
			<ul class="pagination">
				<c:if test="${map.pageMaker.totalCount>0 }">
					<!-- 이전링크 -->
					<c:if test="${map.pageMaker.prev }">
						<li><a href="list?page=${map.pageMaker.startPage-1}&perPageNum=${map.pageMaker.criteria.perPageNum}&searchType=${map.pageMaker.criteria.searchType}&keyworkd=${map.pageMaker.criteria.keyword}">이전</a></li>
					</c:if>
					<!-- 페이지번호 -->
					<c:forEach var="idx" begin="${map.pageMaker.startPage }" end="${map.pageMaker.endPage }">
						<li
						<c:out value="${map.pageMaker.criteria.page==idx?'class=active':'' }"/>
						><a href="list?page=${idx }&perPageNum=${map.pageMaker.criteria.perPageNum}&searchType=${map.pageMaker.criteria.searchType}&keyworkd=${map.pageMaker.criteria.keyword}">${idx }</a></li>
					</c:forEach>
					<!-- 다음 링크 -->
					<c:if test="${map.pageMaker.next }">
						<li><a href="list?page=${map.pageMaker.endPage+1}&perPageNum=${map.pageMaker.criteria.perPageNum}&searchType=${map.pageMaker.criteria.searchType}&keyworkd=${map.pageMaker.criteria.keyword}">다음</a></li>
					</c:if>
				</c:if>
			</ul>
		</div>
		<div class="box-footer">
			<div class="text-center">
				<button id='mainBtn' class="btn-primary">메인으로</button>
			</div>
			<script>
				// 메인버튼 눌렀을 때
				$(function() {
					$('#mainBtn').on("click", function(event) {
						location.href = "../";
					});
				});
				// 출력개수 선택했을 때
				document.getElementById("count").addEventListener("change",function(){
					searchType = document.getElementById("searchType").value;
					keyword = document.getElementById("keyword").value;
					location.href = 'list?page=${map.pageMaker.criteria.page}&perPageNum=' + this.value
						+ "&searchType=" + searchType + "&keyword="+ keyword;
				});
			</script>
		</div>
	</div>
	<%@include file="../include/footer.jsp"%>
</body>
<style>
.table th {
	text-align: center;
}
</style>
</html>