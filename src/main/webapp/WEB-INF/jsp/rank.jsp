<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<div class="main-top-layout">
		<div class="container">
			<%@ include file="/include/gnb.jsp"%>
			<h1>�α�˻���</h1>
		</div>
	</div>
	<div class="container mt-3">
		<div class="row">
			<div class="col list-bookmark">
				<h3>�˻� �����丮</h3>
				<ul class="list-group">
					<li class="list-group-item"><dl>
							<dt>�˻���: ${b.search_word }</dt>
							<dd>
								<span class="blockquote-footer text-right"> </span>
							</dd>
						</dl></li>
				</ul>
			</div>
		</div>
		<div class="paging-layout center-block"></div>
	</div>

</body>
</html>