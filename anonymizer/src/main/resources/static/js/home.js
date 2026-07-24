console.log("home.js loaded");

let selectedModality = null;
let selectedMethod = null;
let resultFetchInterval = null;

/**
 * loads modalities (add more in yml)
 * @returns {Promise<void>}
 */
async function loadModalities() {
    const res = await fetch(`/api/modalities`);
    const modalities = await res.json();
    const group = document.getElementById("modality-group");

    group.innerHTML = modalities.map(m =>
        `<button type="button" class="btn-toggle" data-value="${m}">${m}</button>`
    ).join("");

    group.querySelectorAll(".btn-toggle").forEach(btn => {
        btn.addEventListener("click", async () => {
            group.querySelectorAll(".btn-toggle").forEach(b => b.classList.remove("active"));
            btn.classList.add("active");
            selectedModality = btn.dataset.value;
            selectedMethod = null;
            await loadMethods(selectedModality);
            await loadParameters(selectedModality);

            // show the additional settings button
            document.getElementById("more-settings-btn").disabled = false;

        });
    });
}

/**
 * loads methods depending on modality
 * @param inputType
 * @returns {Promise<void>}
 */
async function loadMethods(inputType) {
    const res = await fetch(`/api/methods?inputType=${inputType}`);
    const methods = await res.json();
    const group = document.getElementById("method-group");

    group.innerHTML = methods.map(m =>
        `<button type="button" class="btn-toggle" data-value="${m}">${m}</button>`
    ).join("");

    group.querySelectorAll(".btn-toggle").forEach(btn => {
        btn.addEventListener("click", () => {
            group.querySelectorAll(".btn-toggle").forEach(b => b.classList.remove("active"));
            btn.classList.add("active");
            selectedMethod = btn.dataset.value;
        });
    });
}

/**
 * loads possible parameters depending on modality
 * @param inputType
 * @returns {Promise<void>}
 */
async function loadParameters(inputType) {
    const res = await fetch(`/api/parameters?inputType=${inputType}`);
    const params = await res.json();
    const el = document.getElementById("parameters");
    el.innerHTML = params.map(m =>
        `<label for="${m}" class="form-label">${m}</label>
         <input type="text" id="parameter-options" name="${m}" class="form-control mb-2">`
    ).join("");
}

/**
 * listen if button has been toggled or not
 */
document.getElementById("view-toggle").addEventListener("click", (e) => {
    const btn = e.currentTarget;
    const isActive = btn.dataset.active === "true";
    btn.dataset.active = (!isActive).toString();
    btn.classList.toggle("active", !isActive);
});
/**
 * shows all settings
 */
document.getElementById("more-settings-btn").addEventListener("click", (e) => {
    e.currentTarget.classList.toggle("active");
});

/**
 * collects all parameters (views, input, settings etc to send to backend)
 * @returns {{modality: null, methods: *[], views: boolean|boolean|*, textinput: *, duuiurl: *, parameters: {}}}
 */
function collectParameters(){
    const parameterInput = document.querySelectorAll("#parameters input[type='text']");
    const inputParams = {};
    parameterInput.forEach(parameterInput => {
        inputParams[parameterInput.name] = parameterInput.value;
    });
    const params = {
        modality: selectedModality,
        methods: selectedMethod ? [selectedMethod] : [],
        views: document.getElementById("view-toggle").dataset.active === "true",
        textinput: document.getElementById("text-input-area").value,
        duuiurl: document.getElementById("entryBox-component").value,
        parameters: inputParams
    };
    return params;
}

/**
 * sends collected parameters to the process endpoint
 * @param params
 * @returns {Promise<any>}
 */
async function sendToEndpoint(params) {

    const res = await fetch(`/api/process`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(params)
    });
    const output = await res.json();
    if (!res.ok) {
        showError(output.message || "something didnt work")
    }
    return output;

}

/**
 * button to trigger everything
 */
document.getElementById("process-btn").addEventListener("click", async ()=>{
    //on rerun set it to empty
    document.getElementById("output").textContent = "";

    const button = document.getElementById("process-btn");
    // fail safe dont press that more than once ;) so it just gets disabled until the process is done
    button.disabled=true;
    hideError();
    showWaiting();
    if (resultFetchInterval != null) {
        clearInterval(resultFetchInterval);
        resultFetchInterval = null;
    }
    try {
        const params = collectParameters();

        await sendToEndpoint(params);
        resultFetchInterval = setInterval(loadResults, 2000);
    } catch (e) {
        showError("Something went wrong: " + e.message);
        button.disabled = false; // re-enable immediately on error
    }

});


/**
 * fetches result from endpoint with an interval after button has been pressed
 * interval resets once result arrives and then contacts the clear endpoint
 * @returns {Promise<void>}
 */
async function loadResults(){
    // todo add the wait pls popup
    const response = await fetch("/api/results");
    const text = await response.text();
    document.getElementById("output").textContent = text;

    // clear the timer so it stops loading once a result arrived
    if(text && resultFetchInterval != null){
        hideWaiting();
        console.log("Clearing interval, calling /clear");
        clearInterval(resultFetchInterval);
        resultFetchInterval = null;
        document.getElementById("process-btn").disabled = false; // re-enable button to process

        const res = await fetch("/api/results/clean", { method: "POST" });
        if (!res.ok) {
            console.error("Clear failed:", res.status, await res.text());
        }
    }
}



// __________ load methods

loadModalities()


