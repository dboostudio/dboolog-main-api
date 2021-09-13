$(document).ready(
    post(
        "/api/articles",
        { "size" : 12 },
        function (response) {
            draw_main_cards(response["articleList"]);
        }
    )
);

function draw_main_cards(articleList){
    $(articleList).each(function(index, item){
        $(".container-sm > .row").append(
            `<div class="col">
                <div id="card-${index}" data-id="${item.id}" class="card shadow-sm">
                    <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"></rect>
                        <text x="50%" y="50%" fill="#eceeef" dy=".3em"></text>
                    </svg>

                    <div class="card-body">
                        <p class="card-text">${item.title}</p>
                    </div>
                </div>
            </div>`
        );
    });
};

$(document).on('click', '.card', function(){
    location.href = "/view/article/" + this.dataset.id;
    // get("/view/article/" + this.dataset.id);
});

