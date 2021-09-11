$(document).on('change', '#userId', function(){
    if(validation_email($("#userId").val()) == false){
        $("#userId").addClass('border').addClass('border-danger');
        $("#userId-warn").val(dboo_messages.ko.USER_ID_IS_NOT_EMAIL).removeClass('d-none');
    } else {
        $("#userId").removeClass('border').removeClass('border-danger');
        $("#userId-warn").addClass('d-none');
    }
})

$(document).on('change', '#password-confirm', function(){
    if($("#password").val() !== $("#password-confirm").val()){
        $("#password-confirm").addClass('border').addClass('border-danger');
        $("#password-not-match").removeClass('d-none');
        $("#check").addClass('d-none');
    } else {
        $("#password-confirm").removeClass('border').removeClass('border-danger');
        $("#password-not-match").addClass('d-none');
        $("#check").removeClass('d-none');
    }
})

$(document).on('change', '#userId', function(){
    validation_email($("#userId").val())
})

$("#submit").on('click', function(){

    // 패스워드 불일치 검증
    let param = {};
    if($("#password").val() !== $("#password-confirm").val()) {
        $("#password-confirm").addClass('border').addClass('border-danger').focus();
        $("#password-not-match").removeClass('d-none');
        move_view_to_tag($("#password"));
        return;
    }

    // 계성 생성 파라미터 추출
    $('input').each(
        function(){
            let field = $(this).attr('id');
            let value = $(this).val();
            if(field !== undefined && field !== "password-confirm" && value !== undefined && value.length > 0){
                param[field] = value;
            }
        });

    // 계정 생성 요청
    const create_account_url = "/api/account";
    post(create_account_url, param
        ,function(result){
            alert("회원가입이 정상적으로 완료되었습니다! 자동 로그인 됩니다.")
            let userInfo = {
                "userId" : $("#userId").val(),
                "password" : $("#password").val()
            };
            post_login(userInfo);
        }
        ,function(response){
            hide_input_validation_error();
            if(check_object_type(response) == "Array"){
                show_input_validation_error(response);
            } else {
                console.log(response);
            }
        }
    );
})

//https://uxsolutions.github.io/bootstrap-datepicker/
$('#birth').datepicker({
    language: "ko",
    keyboardNavigation: false,
    todayHighlight: true
});