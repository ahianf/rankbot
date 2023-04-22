fetch("https://rankbot.me/api/results/daft-punk")
  .then((response) => response.json())
  .then((json) => {
    let li = `<thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Title</th>
                    <th scope="col">Album</th>
                    <th scope="col">Score</th>
                </tr>
              </thead>`;

    var i = 1;
    json.forEach((song) => {
      li += `<tr>
              <th scope="row">${i}</th>
              <td>${song.title} </td>
              <td>${song.album}</td>
              <td>${song.elo.toFixed(1)}</td>
            </tr>`;
      i++;
    });

    document.getElementById("rankings").innerHTML = li;
  });