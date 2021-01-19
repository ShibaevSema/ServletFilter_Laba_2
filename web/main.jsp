<%@page contentType="text/html" pageEncoding="UTF-8"
        language="java"
        session="true"
%>
<!doctype html>
<html lang="en">
<head>
    <link rel="shortcut icon" href="imgAnDcss/icon.png" type="image/png">
    <title>two lab web</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="imgAnDcss/main.css">

    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="js/scripts.js"></script>
</head>
<body onload="drawGraph('canvas',1)">
<div class = "header">
    <h2 align="center" >Шибаев Семён Сергеевич<br>
        P3210<br>
        Вариант 10791</h2>
</div>
<div class = "card-input" >
    <form class="form card-body" id="form"
          action="checking" method="post"
          onsubmit="return validate(this);">
        <table class="table-btn">
            <tr>
                <td>
                    Выберете значение X из предложенных :<br>
                    <input type="radio" name="X" value="-4" id="-4" required> <label for="-4"> -4 </label>
                    <input type="radio" name="X" value="-3" id="-3" required> <label for="-3"> -3 </label>
                    <input type="radio" name="X" value="-2" id="-2" required> <label for="-2"> -2 </label>
                    <input type="radio" name="X" value="-1" id="-1" required> <label for="-1"> -1 </label>
                    <input type="radio" name="X" value="0" id="0" required> <label for="0"> 0 </label>
                    <input type="radio" name="X" value="1" id="1" required> <label for="1"> 1 </label>
                    <input type="radio" name="X" value="2" id="2" required> <label for="2"> 2 </label>
                    <input type="radio" name="X" value="3" id="3" required> <label for="3"> 3 </label>
                    <input type="radio" name="X" value="4" id="4" required> <label for="4"> 4 </label>
                </td>
            </tr>

            <tr>
                <td>
                    Введите значение Y из интервала (-5;5)<br>
                    <input type="text" name="Y" maxlength="7">
                </td>
            </tr>

            <tr>
                <td>
                    Выберете значение R :<br>
                    <label for="R"> R = </label>
                    <select size="1" multiple name="R" id="R">
                        <option disabled>Выбор за Вами</option>
                        <option selected value="1" onclick="drawGraph('canvas', 1)">1</option>
                        <option value="2" onclick="drawGraph('canvas', 2)">2</option>
                        <option value="3" onclick="drawGraph('canvas', 3)">3</option>
                        <option value="4" onclick="drawGraph('canvas', 4)">4</option>
                        <option value="5" onclick="drawGraph('canvas', 5)">5</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td>
                    <small class="d-block text-left mt-3">
                        <input type="submit" name="submit" value="Отправить" style="background-color: #09624e">
                        <a href="checking" class="btn" style="color: #09624e">Показать историю</a>
                    </small>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="card-body"
     style="min-height: 300px ; min-width: 300px ; max-height: 300px ;max-width: 300px ">
    <canvas id="canvas" onclick="clickGraph('canvas', document.getElementById('form').R.value)"
            width="300" height="300"></canvas>
</div>
</body>
</html>
