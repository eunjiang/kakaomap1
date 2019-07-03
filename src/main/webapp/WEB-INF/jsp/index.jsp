<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>키워드로검색 - Kakao keyword search</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class="main-top-layout">
		<div class="container">
			<%@ include file="/include/gnb.jsp"%>
			<h1>키워드로 장소 검색</h1>
			<div class="row">
				<div class="col-12">
					<form id="search_form" name="search_form" onsubmit="return submitSearch();">
						<input type="hidden" name="page" value="1">
						<div class="form-row">
							<div class="col">
								<div class="form-group">
									<label for="input-searchword">검색어</label>
									<input id="input-searchword" class="form-control" name="searchWord" placeholder="검색어를 입력하세요.">
								</div>
							</div>
							<div class="col">
								<div class="form-group">
									<label>&nbsp;</label>
									<div><button class="btn btn-primary" type="submit">검색</button></div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<hr>
		<h2>지역리스트</h2>
		<div class="row">
			<div class="col-12">
				<div id="address">
					<ul class="list-group">
					</ul>
					<div id="resultMessage" class="alert" role="alert" style="display: none;">검색된 지역이 없습니다.</div>
				</div>
			</div>
		</div>
		<hr>
	</div>
	<script type="text/javascript">
		function submitSearch(page) {
			var pg = page ? page : 1;
			var frm = document.search_form;
			frm.page.value = pg;
			if (frm.searchWord.value == "") {
				alert("검색어를 입력하세요. ");
			} else {
				$
						.ajax({
							url : "/ajax/searchAddress",
							data : $(frm).serialize(),
							beforeSend: function(){
								$(frm).find("button[type=submit]").prop("disabled", true);
								$(frm).find("button[type=submit]").text("Searching for ...");
								console.log($(frm).find("button[type=submit]"));
							},
							success : function(res) {
								if (res.meta.total_count < 1) {
									$("#address > ul").html("");
									$("#address > #resultMessage").show();
								} else {
									$("#address > #resultMessage").hide();
									var html = "";
									$(res.documents)
											.each(
													function(idx) {
														var place_name = "", category_name = "", address_name = "", road_address_name = "", id = "";
														$(this.place_name)
																.each(
																		function() {
																			place_name += (this + " ")
																		});
														$(this.category_name)
																.each(
																		function() {
																			category_name += (this + " ")
																		});
														$(this.address_name)
														.each(
																function() {
																	address_name += (this + " ")
																});
														$(this.road_address_name)
														.each(
																function() {
																	road_address_name += (this + " ")
																});
														$(this.id)
														.each(
																function() {
																	id += (this + " ")
																});
														html += "<li class='list-group-item'>";
// 														html += "<dl><dt><a href='./detail?isbn="
// 																+ isbn
// 																+ "'>"
// 																+ this.title
// 																+ " | "
// 																+ this.publisher
// 																+ "</a>"
// 																+" <button type='button' class='btn btn-primary btn-bookmark add' data-isbn='"+isbn+"'>북마크</button>"
// 																+"</dt>";
														html += "<dl><dt>"
																+ this.place_name
																+ " | "
																+ this.category_name
																+"</dt>";
														html += "<dd><div class='left'> 지번주소: "
																+ this.address_name
																+ "<br> 도로명 주소: "
																+ this.road_address_name
																+ "<br> 상세 URL: <a href='https://map.kakao.com/link/map/"
																+ this.id
																+ "'>"
																+ "다음 지도 보기 </a>"
																+ "</div></dd></dl></li>";
// 														html += "<dd><div class='left'>"
// 																+ thumbnail
// 																+ "</div><div class='right'>저자: "
// 																+ authors
// 																+ "<br> 번역자: "
// 																+ trans
// 																+ "<br> 상태: "
// 																+ this.status
// 																+ "</div></dd></dl></li>";
													});
									if (!res.meta.is_end) {
										html += "<li><button class='btn btn-primary btn-lg btn-block' onclick='submitSearch("
												+ (pg + 1)
												+ "); $(this).parent().remove();'>더보기 </button></li>";
									}
									if (pg > 1) {
										$("#address > ul").append(html);
									} else {
										$("#address > ul").html(html);
									}
								}
								console.log(res);
							},
							error : function(res) {
								console.log(res);
								alert(res);
							},
							complete : function() {
								$(frm).find("button[type=submit]").prop("disabled", false);
								$(frm).find("button[type=submit]").text("검색");
							}
						});
			}
			return false;
		}
		function btnBookmark() {
			const $this = $(this);
			const ISBN = $this.data("isbn");
			$.ajax({
				url : "/ajax/bookmark",
				method: "POST",
				data : {
					"isbn" : ISBN
				},
				success : function(res) {
					console.log(res);
					alert("북마크 되었습니다.");
				},
				error : function(res) {
					console.log(res);
					alert(res); 
				},
				complete : function() {
					$this.prop( "disabled", true );
				}
			});
		}
		$(document).ready(
				function() {
					$("#address > .list-group").on("mouseenter",
							".list-group-item", function() {
								$(this).find("dd").show();
							});
					$("#address > .list-group").on("mouseleave",
							".list-group-item", function() {
								$(this).find("dd").hide();
							});
					
					$("#address > .list-group").on("click", ".btn-bookmark.add", btnBookmark);
				});
	</script>
</body>
</html>