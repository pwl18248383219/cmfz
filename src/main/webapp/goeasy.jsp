<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="./boot/css/back.css">
    <link rel="stylesheet" href="./jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="./jqgrid/css/jquery-ui.css">
    <script src="./boot/js/jquery-2.2.1.min.js"></script>
    <script src="./boot/js/bootstrap.min.js"></script>
    <script src="./jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="./jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="./boot/js/ajaxfileupload.js"></script>
    <!-- 将https协议改为http协议 -->
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script type="text/javascript">
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-c54978d75ba841fa89f22df0f70bdeb8", //替换为您的应用appkey
        });
        goEasy.subscribe({
            channel: "qqq", //替换为您自己的channel
            onMessage: function (message) {
                // alert(message.content);
                $("#receiveMessage2").append(message.content).append("&#10;");
                // $("#receiveMessage2").val($("#receiveMessage2").val()+message.content);
            }
        });
        function send() {
            goEasy.publish({
                channel: "qqq", //替换为您自己的channel
                message: $("#sendMessage2").val() //替换为您想要发送的消息内容
            });
            $("#sendMessage2").val("");
        }

    </script>

</head>
<body>
<form class="form-inline" >
    <textarea class="form-control" rows="10" id="receiveMessage2" readonly></textarea>
    <br/>
    <div class="form-group">
        <input type="email" class="form-control" id="sendMessage2">
    </div>
    <button type="button" class="btn btn-default" onclick="send()">发送</button>

</form>
</body>