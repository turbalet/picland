let showCategoryLustBool = false;

function openCategoryList() {
    if (showCategoryLustBool) {
        document.getElementById("categoryList").style.opacity = '1';
        showCategoryLustBool = false;
    } else {
        document.getElementById("categoryList").style.opacity = '0';
        showCategoryLustBool = true;
    }

}