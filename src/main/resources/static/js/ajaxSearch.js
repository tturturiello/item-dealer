const context = document.querySelector('base').getAttribute('href');
const searchInput = document.querySelector("#search-input");
const searchButton = document.querySelector("#search");
const itemCards = document.querySelectorAll("#item-card");

searchButton.addEventListener("keyup", search);
searchInput.addEventListener("keyup", function() {
    if (searchInput.value.length > 2) search();
});

function search() {
    const url = context + "item/search?q="+searchInput.value;
    for (let i = 0; i < itemCards.length; i++) {
        itemCards[i].innerHTML = "";
    }
    fetch(url, {method : "GET"})
        .then(e => e.json())
        .then(items => {
            for (let i = 0; i < items.length; i++) {
                let item = items[i];
                let date = new Date(item.date);
                var dateFormatted =  date.getDate()+'.'+(date.getMonth()+1)+"."+date.getFullYear();
                itemCards[i].innerHTML = "" +
                    "                <div class=\"card mb-4 shadow-sm\">\n" +
                    "                    <img class=\"bd-placeholder-img card-img-top\" src=\"/item/" + item.id + "/image\" height=\"225px\"/>\n" +
                    "                    <div class=\"card-body\">\n" +
                    "                        <p style=\"color:grey\"><span class=\"category\">" + item.category.name + "</span> | <span>" + dateFormatted + "</span> by <a href=\"#\">" + item.author.username + "</a></p>\n" +
                    "                        <p><span><b>" + item.title + "</b></span></p>\n" +
                    "                        <p><span>" + item.description + "</span></p>\n" +
                    "                        <div class=\"d-flex justify-content-between align-items-center\">\n" +
                    "                            <div class=\"btn-group\">\n" +
                    "                                <a class=\"btn btn-sm btn-outline-secondary\" href=\"/item/" + item.id + "\">View</a>\n" +
                    "                                <a class=\"btn btn-sm btn-outline-secondary\" href=\"/item/" + item.id + "/edit\">Edit</a>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n"
            }
        })
        .catch(err => console.log(err));
}
