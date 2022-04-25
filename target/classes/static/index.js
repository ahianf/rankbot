function getJSON() {

    // fetch('http://192.168.1.224:8080/mm')
    fetch('http://localhost:8080/mm')
        .then(result => result.json())
        .then((a) => {
            song_a_title = a.songA.title
            song_b_title = a.songB.title
            song_a_album = a.songA.album
            song_b_album = a.songB.album
            song_a_arturl = a.songA.artUrl
            song_b_arturl = a.songB.artUrl
            songAId = a.songA.songId
            songBId = a.songB.songId
            matchId = a.matchId

        }).catch(err => console.error(err));
        document.getElementById("song_a_arturl").src = song_a_arturl
}

function init() {

    // fetch('http://192.168.1.224:8080/mm')
    fetch('http://localhost:8080/mm')
        .then(result => result.json())
        .then((a) => {
            song_a_title = a.songA.title
            song_b_title = a.songB.title
            song_a_album = a.songA.album
            song_b_album = a.songB.album
            song_a_arturl = a.songA.artUrl
            song_b_arturl = a.songB.artUrl
            songAId = a.songA.songId
            songBId = a.songB.songId
            matchId = a.matchId

            document.getElementById("song_a_arturl").src = a.songA.artUrl
            document.getElementById("song_b_arturl").src = a.songB.artUrl
            document.getElementById("song_a_album").innerHTML = a.songA.album
            document.getElementById("song_b_album").innerHTML = a.songB.album
            document.getElementById("song_a_title").innerHTML =  a.songA.title
            document.getElementById("song_b_title").innerHTML =  a.songB.title

            animateCSS("#song_a_arturl", 'zoomIn')
            animateCSS("#song_b_arturl", 'zoomIn')
            animateCSS("#song_a_album", 'zoomIn')
            animateCSS("#song_b_album", 'zoomIn')
            animateCSS("#song_a_title", 'zoomIn')
            animateCSS("#song_b_title", 'zoomIn')

        }).catch(err => console.error(err));

}


function ui(matchId, vote) {
    try { postJSON(matchId, vote) } catch {
        return
     }
    animateCSS("#song_a_arturl", 'zoomOut').then((message) => {
        document.getElementById("song_a_arturl").src = song_a_arturl
        animateCSS("#song_a_arturl", 'zoomIn')
    });
    animateCSS("#song_b_arturl", 'zoomOut').then((message) => {
        document.getElementById("song_b_arturl").src = song_b_arturl
        animateCSS("#song_b_arturl", 'zoomIn')
    });
    animateCSS("#song_a_album", 'zoomOut').then((message) => {
        document.getElementById("song_a_album").innerHTML = song_a_album
        animateCSS("#song_a_album", 'zoomIn')
    });
    animateCSS("#song_b_album", 'zoomOut').then((message) => {
        document.getElementById("song_b_album").innerHTML = song_b_album
        animateCSS("#song_b_album", 'zoomIn')
    });
    animateCSS("#song_a_title", 'zoomOut').then((message) => {
        document.getElementById("song_a_title").innerHTML = song_a_title
        animateCSS("#song_a_title", 'zoomIn')
    });
    animateCSS("#song_b_title", 'zoomOut').then((message) => {
        document.getElementById("song_b_title").innerHTML = song_b_title
        animateCSS("#song_b_title", 'zoomIn')
    });
}

function postJSON(matchId, vote) {

    // Creating a XHR object
    let xhr = new XMLHttpRequest();
    // let url = "http://192.168.1.224:8080/mm";
    let url = "http://localhost:8080/mm";

    // open a connection
    xhr.open("POST", url, true);

    // Set the request header i.e. which type of content you are sending
    xhr.setRequestHeader("Content-Type", "application/json");

    // Create a state change callback
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
        }
    };

    // Converting JSON data to string
    var data = JSON.stringify(
        {
            "matchId": matchId,
            "vote": vote
        }
    );

    // Sending data with the request
    xhr.send(data);
    getJSON();
}

const animateCSS = (element, animation, prefix = 'animate__') =>
    // We create a Promise and return it
    new Promise((resolve, reject) => {
        const animationName = `${prefix}${animation}`;
        const node = document.querySelector(element);

        node.classList.add(`${prefix}animated`, animationName);

        // When the animation ends, we clean the classes and resolve the Promise
        function handleAnimationEnd(event) {
            event.stopPropagation();
            node.classList.remove(`${prefix}animated`, animationName);
            resolve('Animation ended');
        }

        node.addEventListener('animationend', handleAnimationEnd, { once: true });
    });
