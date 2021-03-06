<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 引入bootstrap样式 -->
<link rel="stylesheet" type="text/css"
	href="/resource/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="/resource/css/sb-admin.css" />
<link rel="stylesheet" type="text/css" href="/resource/css/cms.css" />
<link rel="stylesheet" type="text/css" href="/resource/css/index.css" />
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<!-- 上 -->
		<div class="row">
			<div class="col-md-12"
				style="background-color: #222222; height: 40px;">
					<jsp:include page="/WEB-INF/view/index/top.jsp"></jsp:include>
				</div>
		</div>



		<!--中  -->
		<div class="row" style="margin-top: 10px">
			<!--中间的左边 所有的栏目 -->
			<div class="col-md-2">

				<ul style="margin-top: 8px">

					<li class="channel-item ${article.channel_id==null?'active':'' }"><a href="/index" class="channel">热门</a></li>

					<c:forEach items="${channelList }" var="c">

						<li class="channel-item" ${article.channel_id==c.id?'active':'' }><a href="/index?channel_id=${c.id}"
							class="channel">${c.name }</a></li>


					</c:forEach>
				</ul>
			</div>

			<!--  中间的中间-->
			<div class="col-md-7 split">
							<!-- 搜索框 -->
			<form action="/search" method="get">
				<div class="input-group mb-3">
					<input type="text" name="key" value="${key}" class="form-control"
						placeholder="请输入要搜索的内容" aria-label="Recipient's username"
						aria-describedby="button-addon2">
					<div class="input-group-append">
						<button class="btn btn-outline-secondary" id="button-addon2">搜索<tton>
					</div>
				</div>
			</form>
				<!-- 第一次进入 没有选择栏目  默认显示轮播图和热门文章 -->
				<c:if test="${article.channel_id==null}">
					
					<!--  轮播图-->
					<div id="carouselExampleCaptions" class="carousel slide"
						data-ride="carousel">
						<ol class="carousel-indicators">
							<li data-target="#carouselExampleCaptions" data-slide-to="0"
								class="active"></li>
							<li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
							<li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
						</ol>
						<div class="carousel-inner">
							<c:forEach items="${slideList }" var="s" varStatus="i">

								<div class="carousel-item ${i.index==0?'active':'' }">
									<img src="/pic/${s.picture}" class="d-block w-100" alt="..."
										width="280px" height="200px">
									<div class="carousel-caption d-none d-md-block">
										<h5>${s.title }</h5>

									</div>
								</div>
							</c:forEach>
						</div>
						<a class="carousel-control-prev" href="#carouselExampleCaptions"
							role="button" data-slide="prev"> <span
							class="carousel-control-prev-icon" aria-hidden="true"></span> <span
							class="sr-only">Previous</span>
						</a> <a class="carousel-control-next" href="#carouselExampleCaptions"
							role="button" data-slide="next"> <span
							class="carousel-control-next-icon" aria-hidden="true"></span> <span
							class="sr-only">Next</span>
						</a>
					</div>

					<!--热门文章  -->
					<div id="hotArticle">

						<ul class="list-unstyled">
							
							<c:forEach items="${articleList }" var="a">
								<li class="media"><img src="/pic/${a.picture }"
									class="mr-3" alt="..." width="160px" height="100px">
									<div class="media-body text-center">
										<h5 class="mt-0 mb-1 ">
											<a href="/indexs/select?id=${a.id }"
												style="font-size: 15px;" 
												data-target="#exampleModal">${a.title }</a>
										</h5>
										<br> ${a.user.username }&nbsp;&nbsp;&nbsp;
										<fmt:formatDate value="${a.created }" pattern="yyyy-MM-dd" />
									</div></li>
								<hr>

							</c:forEach>
						</ul>


					</div>
				</c:if>
				<!-- 选择了栏目  只显示栏目下的 文章 和分类-->
				<c:if test="${article.channel_id!=null}">
						<!-- 栏目下分类菜单 -->
					<div class="subchannel">
						<ul class="sub-list" style="width: 660px;">
							<li class="sub-item ${article.category_id==null?'sub-selected':'' }"><a
									href="/index/?channel_id=${article.channel_id }">全部</a></li>
							<c:forEach items="${cates}" var="category">

								<li class="sub-item ${article.category_id==category.id?'sub-selected':'' }"><a
									href="/index/?channel_id=${article.channel_id }&category_id=${category.id}">${category.name }</a></li>
							</c:forEach>

						</ul>
						</div>
						<hr>
				</c:if>
				<!-- 文章显示 -->
				<div id="article">

						<ul class="list-unstyled">
							<c:forEach items="${info.list }" var="a">
								<li class="media"><img src="/pic/${a.picture }"
									class="mr-3" alt="..." width="160px" height="100px">
									<div class="media-body text-center">
										<h5 class="mt-0 mb-1 ">
											<a href="/indexs/select?id=${a.id }" onclick="look(${a.id})"
												style="font-size: 15px;"
												data-target="#exampleModal">${a.title }</a>
										</h5>
										<br> ${a.user.username }&nbsp;&nbsp;&nbsp;
										<fmt:formatDate value="${a.created }" pattern="yyyy-MM-dd" />
									</div></li>
								<hr>

							</c:forEach>
						</ul>


					</div>	
	
			</div>
			<!-- 中间的右边 -->
			<div class="col-md-3 split min_h_500">
				<div class="card" style="width: 18rem;">
					<div class="card-header">最新图片</div>
					<ul class="list-group list-group-flush">
						<c:forEach items="${newArcitles}" var="a">
							<img src="/pic/${a.picture}" class="d-block w-100" alt="..."
										width="280px" height="200px">
							<li class="list-group-item"><a href="">${a.title }</a></li>
						</c:forEach>
					</ul>
				</div>
				<div class="card" style="width: 18rem;">
					<div class="card-header">热门文章</div>
					<ul class="list-group list-group-flush">
						<c:forEach items="${articleList}" var="a">
							<li class="list-group-item"><a href="">${a.title }</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>

		</div>


















	<a href="search?key=${key }&pageNum=${info.pageNum==1?'1':info.pageNum-1 }">上一页</a>
    <a href="search?key=${key }&pageNum=${info.pageNum==11?'1':info.pageNum+1 }">下一页</a>

	</div>
</body>
</html>