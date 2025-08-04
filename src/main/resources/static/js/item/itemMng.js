$(document).ready(function(){
    $("#searchBtn").on("click",function(e) {
        e.preventDefault();
        page(0);
    });
});

function page(page){
    let searchDateType = $("#searchDateType").val();
    let searchSellStatus = $("#searchSellStatus").val();
    let searchItemType = $("select[name='searchItemType']").val();
    let searchBy = $("#searchBy").val();
    let searchQuery = $("#searchQuery").val();

    location.href="/admin/items/" + page + "?searchDateType=" + searchDateType
    + "&searchSellStatus=" + searchSellStatus
    + "&searchItemType=" + searchItemType
    + "&searchBy=" + searchBy
    + "&searchQuery=" + searchQuery;
}

