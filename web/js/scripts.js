document.addEventListener("DOMContentLoaded", function (event) {
    drawGraph("canvas", 1)
});


function validate(_form) {
    var fail = false;
    var X = _form.X.value;
    var Y = _form.Y.value;
    var R = _form.R.value;

    if (Y <= -5 || Y >= 5 || isNaN(Y) || Y === "" || Y.length > 7) {
        fail = "Введите корректное Y ! \n";
    }

    if (fail) {
        alert(fail);
        return false;
    } else {
        createGraph('canvas', X, Y, R);
        return true;
    }

}

function drawGraph(id, r) {
    var canvas = document.getElementById(id),
        context = canvas.getContext("2d");
    //очистка
    context.clearRect(0, 0, canvas.width, canvas.height);

    //квадрат
    context.beginPath();
    context.rect(150, 20, 130, 130);
    context.closePath();
    context.strokeStyle = "#09624e";
    context.fillStyle = "#09624e";
    context.fill();
    context.stroke();

    //треугольник
    context.beginPath();
    context.moveTo(150, 150);
    context.lineTo(150, 85 );
    context.lineTo(20 , 150);
    context.lineTo(150, 150);
    context.closePath();
    context.strokeStyle = "#09624e";
    context.fillStyle = "#09624e";
    context.fill();
    context.stroke();

    // сектор
    context.beginPath();
    context.moveTo(150, 150);
    context.arc(150, 150, 65, Math.PI / 2, Math.PI  , false);
    context.closePath();
    context.strokeStyle = "#09624e";
    context.fillStyle = "#09624e";
    context.fill();
    context.stroke();

    //отрисовка осей
    context.beginPath();
    context.font = "Bradley Hand, cursive";
    context.fontcolor="#000000";
    context.moveTo(150, 0);
    context.lineTo(150, 300);
    context.moveTo(150, 0);
    context.lineTo(145, 15);
    context.moveTo(150, 0);
    context.lineTo(155, 15);
    context.fillText("Y", 160, 10);
    context.moveTo(0, 150);
    context.lineTo(300, 150);
    context.moveTo(300, 150);
    context.lineTo(285, 145);
    context.moveTo(300, 150);
    context.lineTo(285, 155);
    context.fillText("X", 290, 135);

    // деления X
    context.moveTo(145, 20);
    context.lineTo(155, 20);
    context.fillText(r, 160, 20);
    context.moveTo(145, 85);
    context.lineTo(155, 85);
    context.fillText((r / 2), 160, 78);
    context.moveTo(145, 215);
    context.lineTo(155, 215);
    context.fillText(-(r / 2), 160, 215);
    context.moveTo(145, 280);
    context.lineTo(155, 280);
    context.fillText(-r, 160, 280);
    // деления Y
    context.moveTo(20, 145);
    context.lineTo(20, 155);
    context.fillText(-r, 20, 170);
    context.moveTo(85, 145);
    context.lineTo(85, 155);
    context.fillText(-(r / 2), 70, 170);
    context.moveTo(215, 145);
    context.lineTo(215, 155);
    context.fillText((r / 2), 215, 170);
    context.moveTo(280, 145);
    context.lineTo(280, 155);
    context.fillText(r, 280, 170);

    context.closePath();
    context.strokeStyle = "black";
    context.fillStyle = "black";
    context.stroke();
}

function createGraph(id, x, y, r) {
    drawGraph(id, r);
    var canvas = document.getElementById(id),
        context = canvas.getContext("2d");
    context.beginPath();
    context.rect(Math.round(150 + ((x / r) * 130)) - 2, Math.round(150 - ((y / r) * 130)) - 2, 4, 4);
    context.closePath();
    context.strokeStyle = "red";
    context.fillStyle = "red";
    context.fill();
    context.stroke();
}

function clickGraph(canvId, R) {
    var elem = document.getElementById(canvId);
    var br = elem.getBoundingClientRect();
    var left = br.left;
    var top = br.top;
    var event = window.event;
    var x = event.clientX - left;
    var y = event.clientY - top;
    var transf_x = R * (x - 150) / 130;
    var transf_y = R * (150 - y) / 130;

    $.ajax({
            url: "checking",
        type: "POST",
            data: {"X": transf_x, "Y": transf_y, "R": R, "silent": "on"},
        success: function (data) {
            drawPoint(canvId, x, y, data["in_area"]);
        },
        error: function () {
            alert("Некоторая ошибка обработки запроса")
        }
    });
}


function drawPoint(id, x, y, isArea) {
    var canvas = document.getElementById(id),
        context = canvas.getContext("2d");

    context.beginPath();
    context.ellipse(x - 1, y - 1, 2, 2, 1, 0, 2 * Math.PI, true);
    context.closePath();
    if (isArea) {
        context.strokeStyle = "#281845";
        context.fillStyle = "#281845";
    } else {
        context.strokeStyle = "#dc3545";
        context.fillStyle = "#dc3545";
    }
    context.fill();
    context.stroke();

}