class AppSidebar extends HTMLElement {
    connectedCallback() {
        const userRole = this.getAttribute('role') || 'app_attendee';
        
        // Se for admin ou organizer, mostra menu completo
        const isAdmin = userRole === 'app_admin' || 'app_viewer';

        const logo = `
            <div class="h-16 flex items-center px-6 border-b border-zinc-800">
                <span class="font-bold text-white tracking-tight">SysEventos</span>
            </div>
        `;

        const links = [
            { href: '/read/evento', label: 'Eventos', adminOnly: false },
            { href: '/read/palestra', label: 'Palestras', adminOnly: true },
            { href: '/read/palestrante', label: 'Palestrantes', adminOnly: true },
            { href: '/read/usuario', label: 'Usuários', adminOnly: true }
        ];

        let linksHtml = '<nav class="flex-1 px-4 py-6 space-y-1 flex flex-col">';
        
        links.forEach(link => {
            if (!link.adminOnly || isAdmin) {
                const isActive = window.location.pathname.startsWith(link.href);
                const classes = isActive 
                    ? 'bg-zinc-900 text-white border border-zinc-800' 
                    : 'text-zinc-400 hover:text-white hover:bg-zinc-900/50 border border-transparent';
                
                linksHtml += `
                    <a href="${link.href}" class="flex items-center gap-3 px-3 py-2 text-sm font-medium rounded-md transition-colors ${classes}">
                        ${link.label}
                    </a>
                `;
            }
        });

        const footerHtml = `
            <div class="mt-auto pt-6 border-t border-zinc-900 space-y-2">
                <a href="/perfil" class="flex items-center gap-3 px-3 py-2 text-sm font-medium rounded-md text-zinc-400 hover:text-emerald-400 hover:bg-emerald-950/10 transition-colors">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path></svg>
                    Meu Perfil
                </a>
                <form action="/logout" method="POST">
                    <button type="submit" class="w-full flex items-center gap-3 px-3 py-2 text-sm font-medium rounded-md text-zinc-400 hover:text-red-400 hover:bg-red-950/10 transition-colors text-left">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path></svg>
                        Sair
                    </button>
                </form>
            </div>
        </nav>`;

        this.innerHTML = `
            <aside class="w-64 border-r border-zinc-800 bg-zinc-950 flex flex-col fixed h-full transition-all">
                ${logo}
                ${linksHtml}
                ${footerHtml}
            </aside>
        `;
    }
}

customElements.define('app-sidebar', AppSidebar);
