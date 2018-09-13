/**
 * 
 */
// 屏蔽非数字和非退格符
function GetInput() {
	var k = event.keyCode;
	// 48-57是大键盘的数字键，96-105是小键盘的数字键，8是退格符←
	if ((k <= 57 && k >= 48) || (k <= 105 && k >= 96) || (k == 8)) {
		return true;
	} else {
		return false;
	}
}

$(document).ready(
		function() {
			$("#rechar").click(
					function() {

						if ($("#agreement").prop("checked")) {
							var mo = $("#money").val();
							if (mo < 1000 ) {
								alert("充值金额大于1000且小于50000元");
							} else {
								$('#rechargeMoney').attr("action",
										ctx + "/rec/redirect").submit();
							}

						} else {
							alert("请同意《汇中网贷注册协议》");
						}
					});
		})