// JavaScript Document

(function(){
	$("[data-load]").each(function() {
		$(this).load($(this).data("load"), function() {
			alert("公用html已经载入");
		});
	});
})();


