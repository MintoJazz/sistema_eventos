document.addEventListener(
    'DOMContentLoaded', () => {
        const botaoSubmit = document.getElementById('btn-confirmar-inscricao');
        const checkboxes = document.querySelectorAll('.checkbox-evento');

        checkboxes.forEach(
            checkbox => {
                checkbox.addEventListener(
                    'change', () => {
                        const algumCheckboxMarcado = [...checkboxes].some(checkbox => checkbox.checked);
                        botaoSubmit.disabled = !algumCheckboxMarcado;
                    }
                );
            }
        );
    }
);
