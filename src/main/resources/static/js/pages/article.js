$(document).ready(function(){
    get("/api/articles/"+$("#info").attr("data-id"), function(response){
        console.log(response);
        $(".container-sm").append(response["content"]);
    });
})