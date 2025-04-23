document.addEventListener("DOMContentLoaded", function () {
    const dadosSalvos = localStorage.getItem("usuario") || sessionStorage.getItem("usuario");

    if (!dadosSalvos) {
        window.location.href = "login.html";
    } else {
        const usuario = JSON.parse(dadosSalvos);
        const username = usuario.username || usuario.nome || usuario.email;

        const elemento = document.querySelector("#usuario-nome");
        if (elemento) {
            elemento.innerText = elemento.innerText.replace("", username);
        }

        console.log("Usuário logado:", usuario);
    }
});
