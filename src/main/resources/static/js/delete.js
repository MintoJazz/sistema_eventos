const botoes = document.querySelectorAll('.btn-delete')
const entidade = document.body.dataset.entidade || 'item'

for (const botao of botoes) {
    botao.addEventListener('click', async (event) => {
        event.stopPropagation()
        
        if (!confirm(`Tem a certeza que deseja eliminar este ${entidade}?`)) return
        
        try {
            const response = await fetch(botao.dataset.url, { method: 'DELETE' })
            
            if (response.ok) {
                window.location.reload()
            } else {
                alert(`Erro ao eliminar: ${await response.text()}`)
            }
        } catch (error) {
            console.error(error)
            alert('Erro de ligação ao servidor.')
        }
    })
}
