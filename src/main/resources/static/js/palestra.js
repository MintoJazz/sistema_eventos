class Palestra {
    constructor(idFormulario) {
        this.elementoFormulario = new FormData(document.getElementById(idFormulario))
        this.nome = this.elementoFormulario.get('palestra-nome') || ''
        this.dataHoraString = this.elementoFormulario.get('datetime')
        this.dataHora = new Date(this.dataHoraString) || ''
        this.duracao = parseInt(this.elementoFormulario.get('duracao'), 10) || 0
        this.evento = parseInt(this.elementoFormulario.get('evento-palestra'), 10) || null
        this.palestrante = this.elementoFormulario.getAll('palestrantes')
    }

    validar() {
        const erros = {}

        if (!this.nome) erros.nome = 'Nome da palestra é obrigatório'
        if (this.dataHoraString && this.dataHora < new Date()) erros.dataHora = 'A data nao pode ser anterior a data de hoje'
        else if (this.dataHoraString && isNaN(this.dataHora)) erros.dataHora = 'Digite uma data e hora validos';
        if (this.duracao <= 0) erros.duracao = 'Insira uma duracao valida em horas'
        if (!this.evento) erros.evento = 'Selecione um evento'
        if (this.palestrante.length === 0) erros.palestrante = 'Selecione pelo menos um palestrante'

        return {
            isValid: Object.keys(erros).length === 0,
            erros: erros
        };

    }
}