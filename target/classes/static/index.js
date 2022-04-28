function getMatch() {

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

function ui(matchId, vote) {
    postResult(matchId, vote)
    animateCSS("#song_a_arturl", 'zoomOut').then((message) => {
        document.getElementById("song_a_arturl").src = song_a_arturl
        animateCSS("#song_a_arturl", 'zoomIn')
    });
    animateCSS("#song_b_arturl", 'zoomOut').then((message) => {
        document.getElementById("song_b_arturl").src = song_b_arturl
        animateCSS("#song_b_arturl", 'zoomIn')
    });
    animateCSS("#song_a_album", 'fadeOut').then((message) => {
        document.getElementById("song_a_album").innerHTML = song_a_album
        animateCSS("#song_a_album", 'fadeIn')
    });
    animateCSS("#song_b_album", 'fadeOut').then((message) => {
        document.getElementById("song_b_album").innerHTML = song_b_album
        animateCSS("#song_b_album", 'fadeIn')
    });
    animateCSS("#song_a_title", 'fadeOut').then((message) => {
        document.getElementById("song_a_title").innerHTML = song_a_title
        animateCSS("#song_a_title", 'fadeIn')
    });
    animateCSS("#song_b_title", 'fadeOut').then((message) => {
        document.getElementById("song_b_title").innerHTML = song_b_title
        animateCSS("#song_b_title", 'fadeIn')
    });
    animateCSS("#info_alert", 'fadeOut').then((message) => {
        document.getElementById("info_alert").classList.add("d-none")
    });
}

function postResult(matchId, vote) {

    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8080/mm";


    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
        }
    };

    var data = JSON.stringify(
        {
            "matchId": matchId,
            "vote": vote
        }
    );

    xhr.send(data);
    getMatch();
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

function start() {
    setTimeout(
        function () {
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
                    document.getElementById("song_a_title").innerHTML = a.songA.title
                    document.getElementById("song_b_title").innerHTML = a.songB.title

                    animateCSS("#song_a_arturl", 'zoomIn')
                    animateCSS("#song_b_arturl", 'zoomIn')
                    animateCSS("#song_a_album", 'fadeIn')
                    animateCSS("#song_b_album", 'fadeIn')
                    animateCSS("#song_a_title", 'fadeIn')
                    animateCSS("#song_b_title", 'fadeIn')

                }).catch(err => console.error(err));

        }, 500);
}