$(document).ready(function(){
    if(window.errorMessage && window.errorMessage.trim() !== ''){
        alert(window.errorMessage);
    }
    bindDomEvent();
});

function bindDomEvent(){
    $(".custom-file-input").on("change", function() {

        // Handling when file is not selected
        if(!this.files || this.files.length === 0) {
            $(this).siblings(".custom-file-label").html("Select file");
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
            $(this).siblings(".custom-file-label").html("Select file");
            return;
        }

        // Check file size (Has to be less than 20MB)
        let maxSize = 20 * 1024 * 1024; // 20MB
        if(file.size > maxSize) {
            alert("File size should be less than 5MB");
            $(this).val('');
            $(this).siblings(".custom-file-label").html("Select file");
            return;
        }

        let displayName = fileName.length > 30 ?
            fileName.substring(0, 27) + "..." : fileName;

        $(this).siblings(".custom-file-label").html(displayName);
    });
}