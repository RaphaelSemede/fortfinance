const formulario = document.querySelector("form");
const Iemail = document.querySelector("#email");
const Isenha = document.querySelector("#senha");
const manterCheckbox = document.querySelector("#manterConectado");

function salvarUsuario(usuario) {
    if (manterCheckbox && manterCheckbox.checked) {
        localStorage.setItem("usuario", JSON.stringify(usuario));
    } else {
        sessionStorage.setItem("usuario", JSON.stringify(usuario));
    }
}

async function verificarSenha(){
    const email = Iemail.value;
    const senha = Isenha.value;

    if (!email || !senha) {
        alert("Preencha email e senha");
        return;
    }

    try {
        const resposta = await fetch("http://localhost:8080/cadastrar/verificar-senha", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({email, senha})
        });

        if (!resposta.ok) {
            if (resposta.status === 401) {
                alert("Senha incorreta");
            } else if (resposta.status === 404) {
                alert("Usuário não encontrado");
            } else {
                alert("Erro ao tentar logar");
            }
            return;
        }

        const usuario = await resposta.json();
        salvarUsuario(usuario);

        alert("Login realizado com sucesso!");
        window.location.href = "index.html"; // redirecione para onde quiser

    } catch (erro) {
        console.error("Erro no login:", erro);
        alert("Erro inesperado");
    }
}

formulario.addEventListener("submit", function (event) {
    event.preventDefault();
    verificarSenha();
});