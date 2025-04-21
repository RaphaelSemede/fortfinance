function ajustarAlturas() {
    console.log('Função ajustarAlturas chamada');
    const elementos = document.querySelectorAll('.card, .grafico, .box');
    elementos.forEach(el => el.style.height = 'auto');

    let maiorAltura = 0;
    elementos.forEach(el => {
        if (el.offsetHeight > maiorAltura) {
            maiorAltura = el.offsetHeight;
        }
    });

    console.log('Maior altura:', maiorAltura);

    elementos.forEach(el => el.style.height = maiorAltura + 'px');
}

window.addEventListener('load', ajustarAlturas);
window.addEventListener('resize', ajustarAlturas);
