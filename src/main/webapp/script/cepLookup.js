const cep = document.querySelector("#cep");

cep.addEventListener("blur", async () => {
    const url = `https://viacep.com.br/ws/${cep.value}/json`;

    try {
        const response = await fetch(url);
        const data = await response.json();

        if (data.erro) { // api error
            cep.innerHTML = "Incorrect CEP";
            console.error("Incorrect CEP");
        } else {
            document.querySelector("#street").value = data.logradouro;
            document.querySelector("#complement").value = data.complemento;
            document.querySelector("#neighborhood").value = data.bairro;
            document.querySelector("#city").value = data.localidade;
            document.querySelector("#state").value = data.uf;
        }
    } catch (e) {
        cep.innerHTML = 'API Error';
        console.error("API Error: ", e);
    }
})

