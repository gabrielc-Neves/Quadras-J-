const apiUrl = 'http://localhost:8080'; // Endereço do backend

document.getElementById('submitLogin').addEventListener('click', login);
document.getElementById('logoutBtn').addEventListener('click', logout);
document.getElementById('submitQuadra').addEventListener('click', cadastrarQuadra);
document.getElementById('submitReserva').addEventListener('click', fazerReserva);

async function login() {
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    
    const response = await fetch(`${apiUrl}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, senha })
    });

    if (response.ok) {
        const data = await response.json();
        localStorage.setItem('token', data.token);
        document.getElementById('loginSection').style.display = 'none';
        document.getElementById('quadrasSection').style.display = 'block';
        document.getElementById('reservasSection').style.display = 'block';
        document.getElementById('logoutBtn').style.display = 'inline';
        loadQuadras();
        loadReservas();
    } else {
        document.getElementById('loginMessage').innerText = 'Usuário ou senha inválidos.';
    }
}

async function cadastrarQuadra() {
    const nome = document.getElementById('quadraNome').value;
    const capacidade = document.getElementById('quadraCapacidade').value;
    const token = localStorage.getItem('token');

    const response = await fetch(`${apiUrl}/quadras/cadastrar`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ nome, capacidade })
    });

    if (response.ok) {
        document.getElementById('quadraMessage').innerText = 'Quadra cadastrada com sucesso!';
        loadQuadras();
    } else {
        document.getElementById('quadraMessage').innerText = 'Erro ao cadastrar quadra.';
    }
}

async function loadQuadras() {
    const token = localStorage.getItem('token');
    const response = await fetch(`${apiUrl}/quadras/listar`, {
        method: 'GET',
        headers: { 'Authorization': `Bearer ${token}` }
    });

    if (response.ok) {
        const quadras = await response.json();
        const quadrasList = document.getElementById('quadrasList');
        const quadraSelect = document.getElementById('quadraSelect');
        quadrasList.innerHTML = '';
        quadraSelect.innerHTML = '';

        quadras.forEach(quadra => {
            const li = document.createElement('li');
            li.textContent = `Quadra: ${quadra.nome} - Capacidade: ${quadra.capacidade}`;
            quadrasList.appendChild(li);

            const option = document.createElement('option');
            option.value = quadra.id;
            option.textContent = quadra.nome;
            quadraSelect.appendChild(option);
        });
    } else {
        alert('Erro ao carregar quadras.');
    }
}

async function fazerReserva() {
    const token = localStorage.getItem('token');
    const quadraId = document.getElementById('quadraSelect').value;
    const dataReserva = document.getElementById('dataReserva').value;
    const horaInicio = document.getElementById('horaInicio').value;
    const horaFim = document.getElementById('horaFim').value;

    const response = await fetch(`${apiUrl}/reservas/criar`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ quadra: { id: quadraId }, dataReserva, horaInicio, horaFim })
    });

    if (response.ok) {
        document.getElementById('reservaMessage').innerText = 'Reserva realizada com sucesso!';
        loadReservas();
    } else {
        document.getElementById('reservaMessage').innerText = 'Erro ao fazer reserva.';
    }
}

async function loadReservas() {
    const token = localStorage.getItem('token');
    const response = await fetch(`${apiUrl}/reservas/listar?data=${new Date().toISOString().split('T')[0]}`, {
        method: 'GET',
        headers: { 'Authorization': `Bearer ${token}` }
    });

    if (response.ok) {
        const reservas = await response.json();
        const listaReservas = document.getElementById('listaReservas');
        listaReservas.innerHTML = '';
        reservas.forEach(reserva => {
            const li = document.createElement('li');
            li.textContent = `Quadra: ${reserva.quadra.nome}, Data: ${reserva.dataReserva}, Horário: ${reserva.horaInicio} - ${reserva.horaFim}`;
            listaReservas.appendChild(li);
        });
    }
}

function logout() {
    localStorage.removeItem('token');
    location.reload();
}
