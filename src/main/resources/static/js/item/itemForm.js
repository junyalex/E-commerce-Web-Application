$(document).ready(function(){
    if(window.errorMessage && window.errorMessage.trim() !== ''){
        alert(window.errorMessage);
    }
    bindDomEvent();
});

function bindDomEvent(){
    $(".img-button").on("change", function() {  // ✅ 클래스명 수정

        // Handling when file is not selected
        if(!this.files || this.files.length === 0) {
            $(this).siblings(".file-name-display").html("Select file");
            return;
        }

        let file = this.files[0];
        let fileName = file.name;
        // file extension
        let fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
        fileExt = fileExt.toLowerCase();

        // Allowed extensions
        let allowedExtensions = ["jpg", "jpeg", "gif", "png", "bmp", "webp"];
        if(!allowedExtensions.includes(fileExt)) {
            alert("Can upload image file only");
            $(this).val('');
            $(this).siblings(".file-name-display").html("Select file");  // ✅ 수정
            return;
        }

        // Check file size (Has to be less than 20MB)
        let maxSize = 20 * 1024 * 1024; // 20MB
        if(file.size > maxSize) {
            alert("File size should be less than 20MB");
            $(this).val('');
            $(this).siblings(".file-name-display").html("Select file");  // ✅ 수정
            return;
        }

        let displayName = fileName.length > 30 ?
            fileName.substring(0, 27) + "..." : fileName;

        $(this).siblings(".file-name-display").html(displayName);  // ✅ 수정
    });
}