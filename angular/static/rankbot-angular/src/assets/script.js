// Seleccionar los elementos de la imagen
const imageA = document.getElementById('image-a');
const imageB = document.getElementById('image-b');
const drawButton = document.getElementById('draw-button')
const nextButton = document.getElementById('next-button')
let titleA = document.getElementById('title-a');
let albumA = document.getElementById('album-a');
let titleB = document.getElementById('title-b');
let albumB = document.getElementById('album-b');

let token;

// Función para obtener las nuevas imágenes del servidor
function obtenerImagenes() {
    // Enviar solicitud GET para obtener nuevas URL de imágenes y token
    fetch('http://localhost:8080/api/match/lana-del-rey')
        .then(response => response.json())
        .then(data => {
            // Actualizar los elementos de imagen con las nuevas URL de origen y el texto alternativo
            imageA.src = data.songA.artUrl;
            imageA.alt = data.songA.title;
            titleA.innerText = data.songA.title;
            albumA.innerText = data.songA.album;

            imageB.src = data.songB.artUrl;
            imageB.alt = data.songB.title;
            titleB.innerText = data.songB.title;
            albumB.innerText = data.songB.album;

            imageA.classList.remove('animate__zoomOut');
            imageA.classList.add('animate__animated', 'animate__zoomIn');

            imageB.classList.remove('animate__zoomOut');
            imageB.classList.add('animate__animated', 'animate__zoomIn');

            // Almacenar el nuevo token para su uso posterior
            token = data.token;
        })
        .catch(error => {
            console.error('Error updating images:', error);
        });
}

// Función para enviar el voto al servidor
function enviarVoto(vote) {
    // Crear objeto de datos para enviar en petición POST
    const data = {
        token: token,
        vote: vote
    };

    // Enviar solicitud POST a la primera URL
    fetch('http://localhost:8080/api/test', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(() => {
            // Obtener imágenes del servidor
            obtenerImagenes();
        })
        .catch(error => {
            console.error('Error sending vote:', error);
        });
    imageA.classList.add('animate__animated', 'animate__zoomOut');
    imageB.classList.add('animate__animated', 'animate__zoomOut');
}

// Añadir escuchadores de eventos click a los elementos de imagen
imageA.addEventListener('click', () => {
    enviarVoto(1);
});

imageB.addEventListener('click', () => {
    enviarVoto(2);
});

drawButton.addEventListener('click', () => {
    enviarVoto(3);
});

nextButton.addEventListener('click', () => {
    enviarVoto(4);
});

// Obtener imágenes iniciales del servidor
obtenerImagenes();