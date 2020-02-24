/**

 * writeForm.jsp validation
 * => jquery validation 플러그인 이용

 */

$(function(){
	$("#writeForm").validate({		
		//규칙지정
		rules:{
			name:{ 
				required: true
			},
			title:{
				required : true
			},
			content:{
				required : true
			},
			password:{
				required : true
			},
			file:{
				extension:"png|jpg|gif",
				maxsizetotal:2097152
			}			
		},

		//개발자가 원하는 에러 메시지 작성
		messages:{
			name:{ //이름은 필수요소이고 글자는 최소 2~최대4까지만 허용
				required: "(이름 입력)"	
			},
			title:{
				required : "(제목 입력)"	
			},
			content:{
				required : "(내용 입력)"	
			},
			password:{
				required : "(비밀번호 입력)",	
			},
			file:{
				extension:"(이미지 파일 입력)",
				maxsizetotal:"(파일 사이즈 초과)"
			}
		},		
		errorElement: "span",
		errorPlacement: function(error, element) {
			if(element.prop("type")==="file"){
				$( element ).closest( "form" )
				.find( "small[id='" + element.attr( "id" ) + "']" )
					.append(error);
			}else{
				$( element ).closest( "form" )
				.find( "label[for='" + element.attr( "id" ) + "']" )
					.append(" ").append(error);
			}
		},

	});	

});

 

/*//정규식을 이용한 검증의 형태는 메소드 추가의 형태로 작성

$.validator.addMethod("extension",function(value){

	var pos=value.lastIndexOf("."); // .jpg

	var extension=value.slice(pos);

	var regExtension=/^\\S+\.(?i)(png|jpg|gif)$/;

	console.log(regExtension.test(extension));

	return regExtension.test(extension);

},"확장자를 확인해 주세요");*/