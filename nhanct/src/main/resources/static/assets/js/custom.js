$(document).ready(function(){
	$("#cot1").click(function(){
		/*alert("Hello !")*/
		$(this).addClass("activedLink")
		
		var chuoi = $("#cot1").attr("data-text");
		alert(chuoi);
	});
	
	$("#dangnhap").click(function(){
		/*$(this).addClass("actived");
		$("#dangky").removeClass("actived");
		$(".container-login-form").show();
		$(".container-sigup-form").css("display","none");*/
	});
	
	$("#dangky").click(function(){
		/*$(this).addClass("actived"); 
		$("#dangnhap").removeClass("actived");
		$(".container-login-form").hide();
		$(".container-sigup-form").show();*/
	});
})