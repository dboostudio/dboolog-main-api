
$(document).ready(function(){
    get("/api/articles/"+$("#info").attr("data-id"), function(response){
        parseMarkdownAndShow(response["content"]);
    });
})

function parseMarkdownAndShow(markdownText) {
    console.log(markdownText);
    console.log(marked(markdownText));
    // $("#markdown-container").append(marked(markdownText));
    $("#markdown-container").append(markdownText);

    document.querySelectorAll('pre code').forEach((el) => {
        hljs.highlightElement(el);
    });

}
