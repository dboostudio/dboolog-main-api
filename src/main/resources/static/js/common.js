var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');

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
        error: function (x, t, e) { //XmlHttpRequest, textStatus, errorShown
            if ('function' === typeof (error)) {
                error(x.status, JSON.parse(x.responseText));
            } else {
                console.log(x.status);
                console.log(JSON.parse(x.responseText));
            }
        }
    })
}

function checkObjectType(object) {
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
 * @param tagObject 타겟 태그
 * @param duration (Optional) 뷰를 옮기는데 걸리는 시간. 짧을수록 빨리 움직인다.
 */
function moveViewToTag(tagObject, duration){
    if(!duration){ duration = 50 }
    $('html, body').animate({scrollTop : tagObject.offset()}, duration);
}

/**
 * 이메일(아이디) 형식 체크
 * @param email
 */
function validation_email(email){
    let reg_email = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if(!reg_email.test(email)){
        return false;
    }
    return true;
}

function post_login(userInfo){
    let login_url = "api/account/login";
    post(login_url, )
}