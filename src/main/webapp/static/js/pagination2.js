var pn = 1;
var totalCount = 0; // 总记录数
var pageSize = 0;
var pageTotal = 0;// 总页数
var pageUrl = "";
var param = new Array();
function getJSONDate2(pn, url) {
	$.post(url, {
		page : pn
	}, function(data) {
		totalCount = data.totalCount; // 总记录数
		pageSize = data.pageSize;
		pageTotal = Math.ceil(totalCount / pageSize);// 总页数
		changeNumber2(pn);
		var dateRoot = data.voList;
		var tdCount = $("#tab2 tr th").length;
		$("#tab2 tr:gt(0):not(:eq(0))").remove();
		if (pageTotal == 1 || pageTotal == pn) {
			var p = totalCount;
			if (pageTotal == pn) {
				p = dateRoot.length;
			}
			for (var i = 0; i < p; i++) {
				$("#tab2 tr:last").after($("#tab2 tr:gt(0)").prop("outerHTML"));
				for (var ii = 0; ii < tdCount; ii++) {
					if (param[ii] == "sequence" && ii == 0) {
						$("#tab2 tr:gt(" + i + "):eq(1) td:eq(" + ii + ")")
								.text(i + 1);
					} else {
						$("#tab2 tr:gt(" + i + "):eq(1) td:eq(" + ii + ")")
								.text(dateRoot[i][param[ii]]);
					}

				}
				$("#tab2 tr:gt(0):eq(0)").remove();
			}
		} else {
			for (var i = 0; i < pageSize; i++) {
				$("#tab2 tr:last").after($("#tab2 tr:gt(0)").prop("outerHTML"));
				for (var ii = 0; ii < tdCount; ii++) {
					if (param[ii] == "sequence" && ii == 0) {
						$("#tab2 tr:gt(" + i + "):eq(1) td:eq(" + ii + ")")
								.text(i + 1);
					} else {
						$("#tab2 tr:gt(" + i + "):eq(1) td:eq(" + ii + ")")
								.text(dateRoot[i][param[ii]]);
					}
				}
			}
			$("#tab2 tr:gt(0):eq(0)").remove();
		}

	}, "json");
}

function getPage2(tCount, pSize, url, pkey) {

	pn = 1;
	totalCount = tCount; // 总记录数
	pageSize = pSize;
	pageTotal = Math.ceil(totalCount / pageSize);// 总页数
	pageUrl = url;
	param = pkey;
	getJSONDate2(pn, pageUrl);
	changeNumber2(pn);

}

// $(document).ready(function() {
// getPage(20, 3, ctx + "/rec/list",["id","userName","email","birthday"]);
// })
function nextPage2() {
	if (pn == pageTotal || pageTotal == 0) {
		alert("最后一页");
		if (pageTotal == 0) {
			pn = 1
		} else {
			pn = pageTotal;
		}
	} else {
		pn++;
		getJSONDate2(pn, pageUrl);
	}
	changeNumber2(pn);
}

function prevPage2() {
	if (pn == 1) {
		alert("第一页");
		pn = 1;
	} else {
		pn--;
		getJSONDate2(pn, pageUrl);
	}
	changeNumber2(pn);
}

function firstPage2() {
	pn = 1;
	getJSONDate2(pn, pageUrl);
	changeNumber2(pn);
}

function lastPage2() {
	pn = pageTotal;
	getJSONDate2(pn, pageUrl);
	changeNumber2(pn);
}

function gotoPage2(num) {
	if ($.trim(num) <= pageTotal && $.trim(num) != "") {
		pn = num;
		getJSONDate2(pn, pageUrl);
	} else {
		alert("您输入的页码有误！");
	}
	changeNumber2(pn);
}

function jumpPage2() {
	gotoPage2($("#goNum2").val());
}
function changeNumber2(num) {
	var str = "";
	// alert(totalCount);
	if (num == 1 || num == pageTotal) {
		str = "<a href='javascript:;' onclick='gotoPage2(" + num + ")'>" + num
				+ "</a>&nbsp;";
	} else {
		for (var i = 1; i < 4; i++) {
			if (num == 1 || num % 2 != 0) {
				str = "<a href='javascript:;' onclick='gotoPage2(" + num + ")'>"
						+ num + "</a>&nbsp;"
						+ "<a href='javascript:;' onclick='gotoPage2("
						+ (num + 1) + ")'>" + (num + 1) + "</a>&nbsp;"
						+ "<a href='javascript:;' onclick='gotoPage2("
						+ (num + 2) + ")'>" + (num + 2) + "</a>&nbsp;"
						+ "<a href='#'>...</a>&nbsp;"

						+ "<a href='javascript:;' onclick='gotoPage2("
						+ pageTotal + ")'>" + pageTotal + "</a>&nbsp;";
			} else {
				str = "<a href='javascript:;' onclick='gotoPage2(" + (num - 1)
						+ ")'>" + (num - 1) + "</a>&nbsp;"
						+ "<a href='javascript:;' onclick='gotoPage2(" + num
						+ ")'>" + num + "</a>&nbsp;"
						+ "<a href='javascript:;' onclick='gotoPage2("
						+ (num + 1) + ")'>" + (num + 1) + "</a>&nbsp;"
						+ "<a href='#'>...</a>&nbsp;"
						+ "<a href='javascript:;' onclick='gotoPage2("
						+ pageTotal + ")'>" + pageTotal + "</a>&nbsp;";
			}
		}
	}

	var htm = "<div class='total2-nb'><p class='p2'>"
			+ "<a class='shu' href='javascript:;' onclick='firstPage2()'>首页</a>"
			+ "<a class='shu' href='javascript:;' id='next2' onclick='prevPage2()' class='huang'>上一页</a>"
			+ str
			+ "<a class='shu' href='javascript:;' onclick='nextPage2()' class='huang'>下一页</a>&nbsp;&nbsp;"
			+ "当前<span class='huang'>"
			+ pn
			+ "</span>/"
			+ pageTotal
			+ "&nbsp;&nbsp;"
			+ "<a class='shu' >共"
			+ totalCount
			+ "条</a>"
			+ "<span>转到<input type='text' class='shuru' id='goNum2' /> &ensp;页&emsp;"
			+ "<input type='button' value='确定' class='total-input' onclick='jumpPage2()'/>&emsp;"
			+ "</span>" + "</p></div>";

	$(".total2-nb").replaceWith(htm);
}