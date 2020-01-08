<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    $(function () {
        $("#userTable").jqGrid(
            {
                url: "${pageContext.request.contextPath}/user/queryByPage",
                datatype: "json",
                autowidth:true,
                colNames: ['ID', '手机号', '密码', '状态', '头像', '姓名', '法名','性别','签名','所在地','注册时间','最后登陆时间'],
                colModel : [
                    {name : 'id',align:"center",hidden:true},
                    {name : 'phone',align:"center"},
                    {name : 'password',align:"center"},
                    {name : 'status',align:"center",editable:true,formatter:function (data) {
                            if(data == '1'){
                                return "正常";
                            }else {
                                return "冻结";
                            }
                        },editrules:{required:true},edittype:"select",editoptions: {value:"1:正常;2:冻结"}},
                    {name : 'photo',align:"center",formatter:function(data){
                            return "<img style='width: 180px;height: 80px' src='"+data+"'>"
                        }},
                    {name : 'name',align:"center"},
                    {name : 'nickName',align:"center"},
                    {name : 'sex',align:"center",formatter:function (data) {
                            if(data == '0'){
                                return "男";
                            }else {
                                return "女";
                            }
                        },edittype:"select",editoptions: {value:"1:男;2:女"}},
                    {name : 'sign',align:"center"},
                    {name : 'location',align:"center"},
                    {name : 'registerDate',align:"center",edittype: "date"},
                    {name : 'lastLogin',align:"center",edittype: "date"}
                ],
                rowNum: 5,
                rowList: [5,10, 15],
                pager: '#userPage',
                sortname: 'id',
                // 限定请求方式
                mtype: "get",
                viewrecords: true,
                sortorder: "desc",
                caption: "用户信息",
                autowidth:true,
                multiselect:true,
                styleUI: "Bootstrap",
                height:"500px",
                autowidth: true,
                editurl: "${pageContext.request.contextPath}/user/editUser"
            });
        $("#userTable").jqGrid('navGrid', '#userPage', {edit : true,add : false,del : false,edittext:"修改"},
            {
                closeAfterEdit:true,
            },{
                closeAfterAdd:true,
            },{
                closeAfterDel:true,
            }
        );

    })

</script>

<div class="page-header">
    <h4>用户管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>用户信息</a></li>
</ul>
<div class="panel">
    <table id="userTable"></table>
    <div id="userPage" style="height: 50px"></div>
</div>
