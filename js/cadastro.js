const formulario = document.querySelector("form");
const Iemail = document.querySelector("#email");
const Inome = document.querySelector("#nome");
const Isenha = document.querySelector("#senha");
const Iusername = document.querySelector("#username");
const Itelefone = document.querySelector("#tel");

function verificarUsername(){
    const username = Iusername.value;

    return fetch(`http://localhost:8080/cadastrar/verificar-username?username=${encodeURIComponent(username)}`, {
        method: "GET",
        headers: {
            "Accept": "application/json",
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro na verificação de Usuário");
        }

        const contentType = response.headers.get("Content-Type");
        if (contentType && contentType.includes("application/json")) {
            return response.json();
        } else {
            throw new Error("Resposta não é JSON");
        }
    })
    .then(data => {
        console.log("Resposta da verificação:", data);
        return data.usernameValido;
    })
    .catch(error => {
        console.error("Erro:", error);
        return false;
    });
}

function verificarTelefone(){
    const telefone = Itelefone.value;

    return fetch(`http://localhost:8080/cadastrar/verificar-tel?telefone=${encodeURIComponent(telefone)}`, {
        method: "GET",
        headers: {
            "Accept": "application/json",
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro na verificação do telefone");
        }

        const contentType = response.headers.get("Content-Type");
        if (contentType && contentType.includes("application/json")) {
            return response.json();
        } else {
            throw new Error("Resposta não é JSON");
        }
    })
    .then(data => {
        console.log("Resposta da verificação:", data);
        return data.telefoneValido;
    })
    .catch(error => {
        console.error("Erro:", error);
        return false;
    });
}

function verificarEmail() {
    const email = Iemail.value;

    if (!email) {
        console.error("E-mail não informado");
        return Promise.resolve(false);
    }

    return fetch(`http://localhost:8080/cadastrar/verificar-email?email=${encodeURIComponent(email)}`, {
        method: "GET",
        headers: {
            "Accept": "application/json",
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro na verificação do e-mail");
        }

        const contentType = response.headers.get("Content-Type");
        if (contentType && contentType.includes("application/json")) {
            return response.json();
        } else {
            throw new Error("Resposta não é JSON");
        }
    })
    .then(data => {
        console.log("Resposta da verificação:", data);
        return data.emailValido;
    })
    .catch(error => {
        console.error("Erro:", error);
        return false;
    });
}

function manterConectado(){
    const dadosUsuario = {
        email: Iemail.value,
        username: Iusername.value,
        nome: Inome.value
    };
    localStorage.setItem("usuario", JSON.stringify(dadosUsuario));
}

function limparCampos() {
    Iemail.value = "";
    Inome.value = "";
    Isenha.value = "";
    Iusername.value = "";
    Itelefone.value = "";
}

async function cadastrar() {
    const emailValido = await verificarEmail();
    const usernameValido = await verificarUsername();
    const telefoneValido = await verificarTelefone();

    if (!emailValido) {
        alert("Por favor, use um email que ainda não foi cadastrado.");
        return;
    }
    if (!usernameValido) {
        alert("Por favor, use um nome de usuário que ainda não está em uso.");
        return;
    }
    if (!telefoneValido) {
        alert("Por favor, use um telefone que ainda não está em uso.");
        return;
    }    
    if (!Iemail.value || !Inome.value || !Isenha.value || !Iusername.value || !Itelefone.value) {
        alert("Por favor, preencha todos os campos");
        return;
    }

    fetch("http://localhost:8080/cadastrar", {
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
    .then(function (res) {
        console.log("Cadastro realizado:", res);
        manterConectado();
        limparCampos();
        window.location.href = "index.html";
    })
    .catch(function (err) {
        console.error("Erro no cadastro:", err);
    });
}

formulario.addEventListener("submit", function (event) {
    event.preventDefault();
    cadastrar();
});