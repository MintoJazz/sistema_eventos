document.getElementById('formulario').addEventListener('submit', async (evento) => {
    const formulario = evento.target
    
    evento.preventDefault();
    formulario.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
    formulario.querySelectorAll('.invalid-feedback').forEach(el => el.remove());

    try {
        const resposta = await submitFormulario(formulario.action, formulario, formulario.dataset.method || 'POST')
        const dados = await resposta.json()
        let errosGerais = ''
        if (resposta.ok) {
            const eventoSucesso = new CustomEvent('sys-success', { 
                detail: dados,
                cancelable: true // Importante para o submitFile.js poder pausar
            });

            if (formulario.dispatchEvent(eventoSucesso) && dados.redirectUrl) {
                alert('✅ ' + (dados.mensagem || 'Operação realizada com sucesso!'));         
                window.location.href = dados.redirectUrl;
            }
        } else for (const [campo, mensagem] of Object.entries(dados)) {
            const input = formulario.querySelector(`[name="${campo}"]`);

            if (input) {
                input.classList.add('is-invalid');

                const divErro = document.createElement('div');
                divErro.className = 'invalid-feedback text-red-500 text-xs mt-1';
                divErro.innerText = mensagem;
                
                input.insertAdjacentElement('afterend', divErro);
            } else {
                errosGerais += `- ${mensagem}\n`;
            }
        }

        if (errosGerais) alert("Erros:\n" + errosGerais);
    } catch (erro) {
        console.error("Erro capturado:", erro);
        alert('❌ Erro: ' + erro.message);
    }
});

export async function submitFormulario(url, formulario, metodo) {
    const formData = new FormData(formulario);
    const data = {};

    formData.delete('_method');

    const keys = Array.from(new Set(formData.keys()));

    for (const key of keys) {
        const input = formulario.querySelector(`[name="${key}"]`);
        
        if (input && input.type === 'file') continue;

        const values = formData.getAll(key);

        if (input && input.type === 'checkbox') data[key] = input.checked; 
        else if ((input && input.type === 'select-multiple') || values.length > 1) data[key] = values.map(v => isNaN(v) ? v : (v === '' ? null : Number(v))); 
        else data[key] = values[0]; 

    }
    
    console.log("Enviando JSON:", JSON.stringify(data));

    return await fetch(url, {
        method: metodo,
        headers: { 
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
}
