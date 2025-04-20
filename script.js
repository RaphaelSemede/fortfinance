const formulario = document.querySelector("form");
const Iemail = document.querySelector("#email");
const Inome = document.querySelector("#nome");
const Isenha = document.querySelector("#senha");
const Iusername = document.querySelector("#username");
const Itelefone = document.querySelector("#tel");

function cadastrar() {

    fetch("http://localhost:8080/usuarios",
        {
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            method: "POST",
            body: JSON.stringify({
                email: Iemail.value,
                nome: Inome.value,
                senha: Isenha.value,
                username: Iusername.value,
                telefone: Itelefone.value
            })
        })
        .then(function (res) {console.log(res)})
        .catch(function (res) {console.log(res)});
};

function limparCampos() {
    Iemail.value = "";
    Inome.value = "";
    Isenha.value = "";
    Iusername.value = "";
    Itelefone.value = "";
}

formulario.addEventListener("submit", function (event) {
    event.preventDefault();
    
    cadastrar();
    limparCampos();
});