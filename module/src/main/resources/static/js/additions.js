function updateEventCount() {
    $.get("queue", function(fragment) { // get from controller
        $("#queueLength").replaceWith(fragment); // update snippet of page
    });
}

$(document).ready(setInterval(updateEventCount, 3000));