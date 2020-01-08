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
    <script src="./kindeditor/kindeditor-all-min.js"></script>
    <script src="./kindeditor/lang/zh-CN.js"></script>
    <script type="text/javascript">
        KindEditor.ready(function(K) {
            window.editor = K.create('#editor_id', {
                uploadJson : '${pageContext.request.contextPath}/article/uploadImg',
                allowFileManager : true,
                fileManagerJson : '${pageContext.request.contextPath}/article/showAllImg',
                // 失去焦点后 触发的事件
                afterBlur:function () {
                    // 同步数据方法
                    this.sync();
                }
            });
        });

        $.ajax({
            url:"${pageContext.request.contextPath}/guru/queryAllGuru",
            datatype:"json",
            type:"post",
            success:function(data){
                var option = "<option value=\"0\">请选择所属上师</option>";
                data.forEach(function (guru) {
                    option += "<option value="+guru.id+">"+guru.name+"</option>";
                })
                $("#guru_list").html(option);
            }
        })
        function sub() {
            $.ajaxFileUpload({
                url:"${pageContext.request.contextPath}/article/addArticle",
                type:"post",
                // ajaxFileUpload 不支持serialize() 格式化形式,需要手动封装
                // 异步提交时 无法传输修改后的kindeditor内容,需要刷新
                data:{"id":$("#id").val(),"title":$("#title").val(),"content":$("#editor_id").val(),"status":$("#status").val(),"guruId":$("#guru_list").val()},
                datatype:"json",
                fileElementId:"inputfile",
                success:function (data) {

                }
            })
        }
    </script>
</head>
<body>
    <form role="form" id="kindForm">
        <div class="form-group">
            <input type="hidden" class="form-control" id="id">
        </div>
        <div class="form-group">
            <label for="title">标题</label>
            <input type="text" class="form-control" id="title" placeholder="请输入名称">
        </div>
        <div class="form-group">
            <label for="inputfile">封面</label>
            <!-- name不能起名和实体类一致 会出现使用String类型接受二进制文件的情况 -->
            <input type="file" id="inputfile" name="inputfile">
        </div>
        <div class="form-group">
            <label for="editor_id">内容</label>
            <textarea id="editor_id" name="content" style="width:700px;height:300px;">
                 &lt;strong&gt;HTML内容&lt;/strong&gt;
            </textarea>
        </div>
        <div class="form-group">
            <label for="inputfile">状态</label>
            <select class="form-control" id="status" name="status">
                <option value="1">展示</option>
                <option value="2">冻结</option>
            </select>
        </div>
        <div class="form-group">
            <label for="guru_list">上师列表</label>
            <select class="form-control" id="guru_list" name="guruId">

            </select>
        </div>
        <button type="button" onclick="sub()" class="btn btn-default">提交</button>
    </form>

</body>