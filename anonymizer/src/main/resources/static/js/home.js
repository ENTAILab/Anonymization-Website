const base = "http://localhost:8080"; // adjust port

async function loadModalities() {
    const res = await fetch(`${base}/api/modalities`);
    const modalities = await res.json();
    const sel = document.getElementById("modality");


    sel.innerHTML = '<option value="">-- select --</option>' +
        modalities.map(m => `<option value="${m}">${m}</option>`).join("");
}

document.getElementById("modality").addEventListener("change", async (e) => {
    const inputType = e.target.value;
    const methodSel = document.getElementById("method");
    if (!inputType) { methodSel.disabled = true; return; }

    const res = await fetch(`${base}/api/methods?inputType=${inputType}`);
    const methods = await res.json();
    methodSel.innerHTML = methods.map(m => `<label><input type="radio" name="method" value="${m}"> ${m}</label>`).join("");
    methodSel.disabled = false;
});


document.getElementById("modality").addEventListener("change", async (e) => {
    const inputType = e.target.value;
    const methodSel = document.getElementById("parameters");

    if (!inputType) { methodSel.disabled = true; return; }

    const res = await fetch(`${base}/api/parameters?inputType=${inputType}`);
    const methods = await res.json();
    methodSel.innerHTML = methods.map(m => `<label for="${m}">${m}</label><br><input type="text" id="parameter-options" name="${m}"> `).join("");
    methodSel.disabled = false;
});




function collectParameters(){
    const chosenMethods = Array.from(
        document.querySelectorAll('input[name="method"]:checked')
    ).map(el => el.value)
    const parameterInput = document.querySelectorAll("#parameters input[type='text']");
    const inputParams = {};
    parameterInput.forEach(parameterInput => {
        inputParams[parameterInput.name] = parameterInput.value;
    });
    const params = {
        modality: document.getElementById("modality").value,
        methods: chosenMethods,
        views: document.getElementById("view-toggle").checked,
        textinput: document.getElementById("text-input-area").value,
        duuiurl: document.getElementById("entryBox-component").value,
        parameters: inputParams
    };
    return params;
}


async function sendToEndpoint(params) {
    try {
        const res = await fetch(`${base}/api/process`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(params)
        });
        const output = await res.json();
        if (!res.ok) {
            showError(output.message || "something didnt work")
            return;
        }
        return output;
    }catch (error){
        showError("couldnt reach the server")
    }
}

document.getElementById("process-btn").addEventListener("click", async ()=>{
    const params = collectParameters();


    console.log(params);
    await sendToEndpoint(params);
    // continue here todo
});

function componentURI(){
    const entryBox = document.getElementById("entryBox-component");
    const selected = document.querySelector("input[name='component-urls']:checked").value;

    // TODO uncomment this once i have remote links

    //entryBox.disabled = (selected != "docker");
    //if (entryBox.disabled) entryBox.value = " ";

}

async function loadResults(){
    const response = await fetch("/api/results");
    const text = await response.text();
    document.getElementById("output").textContent = text;
}



// __________ load methods

loadModalities()
setInterval(loadResults, 10000); // every 10s

