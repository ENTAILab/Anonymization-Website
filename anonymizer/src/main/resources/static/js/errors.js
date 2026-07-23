/**
 * shows a little error popup with a message (such as telling user to select a modality)
 * @param message
 * @param duration
 */
function showError(message, duration = 4000) {
    hideWaiting();

    const popup = document.getElementById("error-popup");
    popup.textContent = message;
    popup.classList.remove("d-none");

    document.getElementById("process-btn").disabled = false;
    // make sure the popup disappears ahgain
    clearTimeout(popup._hideTimer);
    popup._hideTimer = setTimeout(() => {
        popup.classList.add("d-none");
    }, duration);
}

/**
 * manually hide the error again for some cases
 */
function hideError() {
    document.getElementById("error-popup").classList.add("d-none");
}

/**
 * signals that the process is running a spinning circle thing
 */
function showWaiting() {
    document.getElementById("waiting-popup").classList.remove("d-none");
}

/**
 * to hide the loading symbol again
 */
function hideWaiting() {
    document.getElementById("waiting-popup").classList.add("d-none");
}