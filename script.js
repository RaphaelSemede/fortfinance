const formulario = document.querySelector("form");
const Iemail = document.querySelector("#email");
const Isenha = document.querySelector("#senha");
const Iusername = document.querySelector("#username");
const Itel = document.querySelector("#tel");

function cadastrar() {

    fetch("http://localhost:8080/cadastrar",
        {
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            method: "POST",
            body: JSON.stringify({
                email: Iemail.value,
                senha: Isenha.value,
                username: Iusername.value,
                tel: Itel.value
            })
        })
        .then(function (res) {console.log(res)})
        .catch(function (res) {console.log(res)});
};

function limparCampos() {
    Iemail.value = "";
    Isenha.value = "";
    Iusername.value = "";
    Itel.value = "";
}

formulario.addEventListener("submit", function (event) {
    event.preventDefault();
    
    cadastrar();
    limparCampos();
});