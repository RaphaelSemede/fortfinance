const Iformulario = document.querySelector("form")
const Idespesas = document.querySelector("#despesas")
const Ivalor = document.querySelector("#valor")
const Idata = document.querySelector("#data")
const Idescricao = document.querySelector("#descricao")

const dadosSalvos = localStorage.getItem("usuario") || sessionStorage.getItem("usuario");

function cadastroDespesa(){
    if (!Ivalor.value || !Idata.value || !Idescricao.value) {
        alert("Por favor, preencha todos os campos");
        return;
    }

    fetch("http://localhost:8080/despesas", {
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify({
            descricao: Idescricao.value,
            data: Idata.value,
            valor: Ivalor.value,
            usuario: {
                id:
            }
        })
    })
}