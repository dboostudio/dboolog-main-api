var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');

/**
 * POST 요청
 * @param url <String> 요청url
 * @param json <JSON> 파라미터
 * @param success <Function>(<JSON>Response) 성공시 수행할 함수
 * @param error <Function>(<JSON>Response) 에러시 수행할 함수
 * @param beforeSend <Function> 요청 전 수행할 함수 (디폴트로 csrf토큰을 붙이도록 정의되어 있음)
 * @param complete <Function> success함수가 끝난후 수행할 함수
 */
function post(url, json, success, error, beforeSend, complete){
    $.ajax({
        type: 'POST',
        contentType: 'application/json;charset=UTF-8',
        url: url,
        data: JSON.stringify(json),
        beforeSend : function (xhr) {
            xhr.setRequestHeader(header, token)
            if('function' === typeof (beforeSend)){
                beforeSend();
            }
        },
        success: function (result) {
            if ('function' === typeof (success)) {
                success(result);
            }
        },
        complete: function () {
            if ('function' === typeof (complete)) {
                complete();
            }
        },
        error: function (xmlHttpRequest, textStatus, errorShown) {
            if ('function' === typeof (error)) {
                error(JSON.parse(xmlHttpRequest.responseText));
            } else {
                alert(JSON.parse(xmlHttpRequest.responseText)[0]["message"]);
            }
        }
    })
}

/**
 * GET 요청
 * @param url <String> 요청url
 * @param success <Function>(Response) 성공시 수행할 함수
 * @param error <Function>(xmlHttpRequest, textStatus, errorShown) 에러시 수행할 함수
 * @param beforeSend <Function> 요청 전 수행할 함수
 * @param complete <Function> success함수가 끝난후 수행할 함수
 */
function get(url, success, error, beforeSend, complete){
    $.ajax({
        type: 'GET',
        contentType: 'application/json;charset=UTF-8',
        url: url,
        beforeSend : function (xhr) {
            xhr.setRequestHeader(header, token)
            if('function' === typeof (beforeSend)){
                beforeSend();
            }
        },
        success: function (response) {
            if ('function' === typeof (success)) {
                success(response);
            }
        },
        complete: function () {
            if ('function' === typeof (complete)) {
                complete();
            }
        },
        error: function (xmlHttpRequest, textStatus, errorShown) {
            if ('function' === typeof (error)) {
                error(xmlHttpRequest,textStatus,errorShown);
            }
            console.log(xmlHttpRequest.status);
            console.log(JSON.parse(xmlHttpRequest.responseText));
            console.log(textStatus);
            console.log(errorShown);
        }
    })
}

/**
 * 오브젝트 타입 검증
 * @param object 오브젝트
 * @returns {string} 타입
 */
function check_object_type(object) {
    let stringConstructor = "test".constructor;
    let arrayConstructor = [].constructor;
    let objectConstructor = ({}).constructor;

    if (object === null) {
        return "null";
    }
    if (object === undefined) {
        return "undefined";
    }
    if (object.constructor === stringConstructor) {
        return "String";
    }
    if (object.constructor === arrayConstructor) {
        return "Array";
    }
    if (object.constructor === objectConstructor) {
        return "Object";
    }
    {
        return "don't know";
    }
}

/**
 * 타겟 태그 위치로 현재 뷰를 옮긴다.
 * @param tagObject <Object> 타겟 태그
 * @param duration <Integer> (Optional) 뷰를 옮기는데 걸리는 시간. 짧을수록 빨리 움직인다.
 */
function move_view_to_tag(tagObject, duration){
    if(!duration){ duration = 50 }
    $('html, body').animate({scrollTop : tagObject.offset()}, duration, 'swing', $(tagObject).focus());
}

/**
 * 이메일(아이디) 형식 체크
 * @param email <String>
 */
function validation_email(email){
    let reg_email = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if(!reg_email.test(email)){
        return false;
    }
    return true;
}

/**
 * 로그인 요청을 보낸다.
 * @param userInfO <JSON> "userId" : "유저이메일아이디", "password" : "패스워드"
 */
function post_login(userInfo){
    let login_url = "/api/account/login";
    post(login_url, userInfo, function(){
        location.href = "/";
    })
}

/**
 * InputBox의 값 validation error가 있을 때
 * InputBox에 빨간 테두리를 치고, 포커스와 뷰를 이동한다.
 * @param response <JSON>
 */
function show_input_validation_error(response){
    $(response).each(function(index){
        let field = response[index]["field"];
        let message = response[index]["message"];
        console.log(field);
        console.log(message);
        if(index == 0){
            move_view_to_tag($(`input[id=${field}]`));
        }
        $(`.warn-message[id=${field}-warn]`).text(message).removeClass('d-none');
        $(`input[id=${field}]`).addClass('border').addClass('border-danger');
    })
}

/**
 * InputBox의 에려표시를 모두 초기화한다.(테두리, 에러메세지)
 */
function hide_input_validation_error(){
    $('input').each(function(){
        $(this).removeClass('border').removeClass('border-danger');
    })
    $('.warn-message').each(function(){
        $(this).addClass('d-none');
    });
}