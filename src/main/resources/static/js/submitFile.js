const form = document.getElementById('formulario');

form.addEventListener('sys-success', async (e) => {
    const dadosResposta = e.detail;
    const inputFile = document.getElementById('inputArquivo');

    if (inputFile && inputFile.files.length > 0) {
        e.preventDefault();
        try {
            const idEvento = dadosResposta.id;
            const formData = new FormData();
            formData.append('arquivo', inputFile.files[0]);

            console.log(`Enviando arquivo para o evento ${idEvento}...`);

            const respUpload = await fetch(inputFile.dataset.url.replace('{id}', idEvento), {
                method: inputFile.dataset.method,
                body: formData
            });

            if (respUpload.ok) alert('✅ Evento criado e arquivo enviado com sucesso!');
            else alert('⚠️ Evento criado, mas erro ao enviar arquivo.');


            window.location.href = dadosResposta.redirectUrl;

        } catch (erro) {
            console.error(erro);
            alert('Erro ao tentar enviar o arquivo.');
            window.location.href = dadosResposta.redirectUrl;
        }
    }
});