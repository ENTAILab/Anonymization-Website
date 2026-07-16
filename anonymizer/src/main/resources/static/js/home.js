
function changeTextButton(){
    var changeBtn = document.getElementById("changeButton");
    if(changeBtn.value =="Click me"){
        changeBtn.value = "i have been clicked";
        changeBtn.classList.add("green");
        changeBtn.classList.remove("red");
    }else{
        changeBtn.value = "Click me";
        changeBtn.classList.add("red");
        changeBtn.classList.remove("green");
    }
}
