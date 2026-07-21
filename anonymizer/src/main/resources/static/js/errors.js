function showError(message, duration=2000){
    const popup =document.getElementById("error-popup");
    popup.textContent=message;
    popup.style.display ="block";

    clearTimeout(popup._hideTimer);
    popup._hideTimer =setTimeout(()=>{
        popup.style.display ="none";

    }, duration);
}