<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

</script>
</head>
<body>
<div class="container-fluild">
	<!-- 上 -->
	<div class="row">
		<div class="col-md-12"
			style="background-color: #222222; height: 40px;">
				<jsp:include page="/WEB-INF/view/index/top.jsp"></jsp:include>
			</div>
	</div>
	<div style="float: left">
		<!-- 文章 -->
		<div>
			<h2>${article.title }</h2>
			<img alt="" src="">
			${article.user.username }
			<fmt:formatDate value="${article.created }" pattern="yyyy-MM-dd"/>
			${article.content }
		</div>
		<!-- 输入评论 -->
		<div>
			<form method="post" action="/user/advice">
				<input type="hidden" value="${article.user.id}" name="user.id">
				<input type="hidden" value="${article.id}" name="article.id">
				<textarea style="width: 800px" name="content"
				placeholder="请输入你的评论..."></textarea>
				<input type="submit" value="发布">
			</form>
		</div>
		
					<!-- 评论 -->
			<div style="width: 1000px">
				<c:if test="${comment.size()!=0}">
					<div>
						<c:forEach items="${comment}" var="c">
							<ul class="list-group list-group-flush">
								<li class="list-group-item">${c.user.username}&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate
										value="${c.created}" pattern="yyyy-MM-dd" /> <br>${c.content}
								</li>
							</ul>
						</c:forEach>
						<jsp:include page="/WEB-INF/view/public/page.jsp"></jsp:include>
					</div>
				</c:if>
				<c:if test="${comment.size()==0}">
					<span style="color: gray">暂无评论，快来抢沙发吧！</span>
				</c:if>
			</div>
	</div>
	
			<!-- 相关文章 -->
	<div style="float: left">
		<h3>相关文章</h3>
		<div>
			<c:forEach items="${articles}" var="a">
				<ul class="list-group list-group-flush">
					<li class="list-group-item"><a href="javascript:look(${a.id})">${a.title}&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate
								value="${a.created}" pattern="yyyy-MM-dd" /></a> <br>${a.content}
					</li>
				</ul>
			</c:forEach>
		</div>
	</div>
</div>

  
</body>
</html>
