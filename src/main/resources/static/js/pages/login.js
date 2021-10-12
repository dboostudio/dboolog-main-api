$("#login-button").on("click", function(){
    let param={};
    param['userId'] = $("#userId").val();
    param['password'] = $("#password").val();
    post("/api/account/login", param,
        function(){ location.href = "/"; }
        ,function(response) {
            hide_input_validation_error();
            if (check_object_type(response) == "Array") {
                show_input_validation_error(response);
            } else {
                console.log(response);
            }
        }
    )
})

$(document).on('keydown', '#password', function(event){
    if(event.which == 13){
        $("#login-button").click();
    }
})

$(document).on('keydown', '#userId', function(event){
    if(event.which == 13){
        $("#login-button").click();
    }
})