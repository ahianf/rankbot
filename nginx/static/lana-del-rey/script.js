function updateImages(image1, image2) {

    const data = {
        token: 10,
        vote: 1
    };

    image1.classList.add('animate__animated', 'animate__zoomOut');
    image2.classList.add('animate__animated', 'animate__zoomOut');
    // Send POST request to first URL
    fetch('http://localhost:8080/api/test', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(() => {
            // Add a delay of 1.5 seconds (1500 ms) before getting the new image
            setTimeout(() => {
                // Send GET request to second URL to get new image data
                fetch('http://localhost:8080/api/match/lana-del-rey')
                    .then(response => response.json())
                    .then(data => {
                        // Update image1 element with new source URL
                        image1.src = data.songA.artUrl;
                        image1.alt = data.imageAltText;

                        // Update image2 element with new source URL
                        image2.src = data.songB.artUrl;
                        image2.alt = data.imageAltText;

                        // Show the images using Animate.css
                        image1.classList.remove('animate__zoomOut');
                        image2.classList.remove('animate__zoomOut');
                        image1.classList.add('animate__animated', 'animate__zoomIn');
                        image2.classList.add('animate__animated', 'animate__zoomIn');

                    })
                    .catch(error => {
                        console.error('Error updating images:', error);
                    });
            }, 50);
        })
        .catch(error => {
            console.error('Error updating images:', error);
        });
}


