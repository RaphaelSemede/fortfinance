document.addEventListener("DOMContentLoaded", function () {
    try {
        const dadosSalvos = localStorage.getItem("usuario") || sessionStorage.getItem("usuario");

        if (!dadosSalvos) {
            window.location.href = "login.html";
        } else {
            const usuario = JSON.parse(dadosSalvos);
            console.log("Usuário logado:", usuario);

            if (!usuario || !usuario.email) {
                console.error("E-mail do usuário não encontrado nos dados salvos.");
                return;
            }

            const username = usuario.username || usuario.nome || usuario.email;
            const elementoNome = document.querySelector("#usuario-nome");
            if (elementoNome) {
                elementoNome.innerText = username;
            }

            function atualizarTotal(endpoint, elementoId) {
                fetch(`http://localhost:8080/${endpoint}/${encodeURIComponent(usuario.email)}`, {
                    method: "GET",
                    headers: { "Accept": "application/json" }
                })
                    .then(response => response.json())
                    .then(total => {
                        const elemento = document.querySelector(`#${elementoId}`);
                        if (elemento) {
                            const valorFormatado = new Intl.NumberFormat('pt-BR', {
                                style: 'currency',
                                currency: 'BRL'
                            }).format(total);
                            elemento.textContent = `${valorFormatado}`;
                        }
                    })
                    .catch(error => {
                        console.error(`Erro ao buscar total de ${endpoint}:`, error);
                    });
            }

            atualizarTotal("despesas/total", "despesa-total");
            atualizarTotal("receitas/total", "receita-total");
        }
        console.log(JSON.parse(localStorage.getItem("usuario")));
    }
    catch (e){
        console.error("Erro no DOMContentLoaded:", e);
    }
});