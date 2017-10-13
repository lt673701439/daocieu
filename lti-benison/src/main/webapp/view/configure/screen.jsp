<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>屏幕设置</title>
    <meta charset="UTF-8">
    <%@ include file="/include.jsp" %>
    <style type="text/css">
        .panel-header {
            background: #DAEEF5;
            border: #DAEEF5 0;
        }

        .panel-title {
            color: #4F4C5D;
        }

        .panel-body {
            background: #FAFAFA;
        }
    </style>

</head>
<body class="easyui-layout">
<div id="topLayout" style="height: 80px; width:auto; border:0; display: flex; flex-direction: row; align-items: center;" region="north" title="&emsp;&emsp;屏幕管理"
     iconCls="icon-search" data-options="collapsible:false">
    <div style="width:20%;">
        <label style="font-size: 13px; padding-left: 45px;"><b>状态筛选:</b></label>
        <select id="filterStatus" onchange="filterStatus()" style="width:auto; height: auto; padding: 0px 10px 0px 10px; margin-left: 6px;">
            <option value="">全部</option>
            <option value="0" selected="selected">正常</option>
            <option value="1">暂停</option>
            <option value="-1">删除</option>
        </select>
    </div>
    <div style="width:80%; display: flex;flex-direction: row;justify-content: flex-end;padding-right: 50px">
        <a class="easyui-linkbutton" plain="true" onclick="openWin('add');" iconCls="icon-add">添加</a>
        <a class="easyui-linkbutton" plain="true" iconCls="icon-edit" onclick="openWin('update')">修改</a>
        <a class="easyui-linkbutton" plain="true" iconCls="icon-remove" onclick="openDelete()">删除</a>
    </div>
</div>
<div region="center" style="border:0;padding: 20px;">
    <table id="tables" style="border:0;" data-options="border:false"></table>
</div>
</body>
</html>
<div id="addWin" class="easyui-window" title="屏幕" data-options="closed:true,resizable:false,iconCls:'icon-save',modal:true,collapsible:false,maximizable:false,minimizable:false"
     style="padding:50px 120px 50px 120px;width:auto;height:auto;display: flex;flex-flow: column;align-items: center;">
    <h1 id="addWinTitle" style="margin-bottom: 50px">屏幕添加</h1>
    <div>
        <form id="dataForm">
            <div>
                <input type="hidden" id="screenId" name="screenId">

                <label for="screenName">屏幕名称:</label>
                <input id="screenName" name="screenName" data-options="required:true" class="easyui-validatebox" validType="length[2,50]"/><br><br>

                <label for="spotId">所属景区:</label>
                <select id="spotId" name="spotId">
                    <c:forEach items="${spots}" var="spot" varStatus="status">
                        <option value="${spot.spotId}">${spot.spotName}</option>
                    </c:forEach>
                </select><br><br>

                <label for="screenCode">屏幕编号:</label>
                <input id="screenCode" name="screenCode" data-options="required:true" class="easyui-validatebox" validType="length[2,50]" style="width: 100px">&emsp;&emsp;

                <lable for="screenStatus">状　　态:</lable>
                <select id="screenStatus" name="screenStatus">
                    <option value="0">正常</option>
                    <option value="1">暂停</option>
                    <option value="-1">删除</option>
                </select><br><br>

                <label for="screenLocation">位　　置:</label>
                <input id="screenLocation" data-options="required:true" name="screenLocation" class="easyui-validatebox" validType="length[2,50]"><br><br>

                <label for="screenLongitude">经　　度:</label>
                <input id="screenLongitude" data-options="required:true" name="screenLongitude" class="easyui-validatebox" validType="length[2,50]" style="width: 80px">&emsp;

                <label for="screenDimension">维　　度:</label>
                <input id="screenDimension" name="screenDimension" data-options="required:true" class="easyui-validatebox" validType="length[2,50]" style="width: 80px"><br><br>

                <label for="screenLong">物理高度:</label>
                <input id="screenLong" name="screenLong" data-options="required:true" validType="length[1,10]" class="easyui-validatebox" style="width: 80px">&emsp;

                <label for="screenWide">物理宽度:</label>
                <input id="screenWide" name="screenWide" data-options="required:true" validType="length[1,10]" class="easyui-validatebox" style="width: 80px"><br><br>

                <label for="screenSize">物理尺寸:</label>
                <input id="screenSize" name="screenSize" name="screenSize" data-options="required:true" validType="length[1,10]" class="easyui-validatebox"><br><br>

                <label for="screenResolution">分辨率:&emsp;</label>
                <input id="screenResolution" name="screenResolution" data-options="required:true" validType="length[3,20]" class="easyui-validatebox"><br><br>

                <label for="screenDescription">描述信息:</label>
                <textarea id="screenDescription" name="screenDescription" rows="3" cols="30" style="vertical-align:middle"></textarea>
            </div>
            <div style="padding:5px;text-align:center;padding-top: 50px">
                <a id="save" class="easyui-linkbutton" icon="icon-ok" style="margin-right: 30px;width: 80px" onclick="save();">添加</a>
                <a class="easyui-linkbutton" icon="icon-cancel" onclick="reset();" style="width: 80px">取消</a>
            </div>
        </form>
    </div>
</div>
<div id="delWin" class="easyui-window" title="警告" style="padding:10px 50px 20px 50px;width:auto;height:auto;display: flex;flex-flow: column;align-items: center"
     data-options="closed:true,resizable:false,iconCls:'icon-save',modal:true,collapsible:false,maximizable:false,minimizable:false">
    <h2>删除屏幕</h2>
    <div style="font-size: 16px">
        <label>编号:&nbsp;&nbsp;</label><span id="delWinCode"></span><br>
        <label>名称:&nbsp;&nbsp;</label><span id="delWinName"></span>
    </div>
    <div style="padding:5px;text-align:center;padding-top: 30px">
        <a id="delWinReset" class="easyui-linkbutton" icon="icon-undo" style="margin-right: 30px;width: 80px">取消</a>
        <a id="delWinDelete" class="easyui-linkbutton" icon="icon-remove" style="width: 80px">删除</a>
    </div>
</div>
<script type="text/javascript">
    function openWin(type) {
        document.getElementById("dataForm").reset();
        if ('update' === type) {
            var rows = $('#tables').datagrid('getSelections');
            if (rows.length === 1) {
                $('#screenId').val(rows[0].screenId);
                $('#screenName').val(rows[0].screenName);
                $('#spotId').val(rows[0].spotId);
                $('#screenCode').val(rows[0].screenCode);
                $('#screenStatus').val(rows[0].screenStatus);
                $('#screenLocation').val(rows[0].screenLocation);
                $('#screenLongitude').val(rows[0].screenLongitude);
                $('#screenDimension').val(rows[0].screenDimension);
                $('#screenLong').val(rows[0].screenLong);
                $('#screenWide').val(rows[0].screenWide);
                $('#screenSize').val(rows[0].screenSize);
                $('#screenResolution').val(rows[0].screenResolution);
                $('#screenDescription').val(rows[0].screenDescription);
            } else {
                alert('请选择一条数据修改')
            }
        }
        $('#save').linkbutton('enable');
        $("#addWin").window('open')
    }
    function openDelete() {
        $('#delWinDelete').linkbutton('enable');
        var rows = $('#tables').datagrid('getSelections');
        if (rows.length === 1) {
            var title = document.getElementById("delWinCode");
            var name = document.getElementById("delWinName");
            title.innerText = rows[0].screenCode;
            name.innerText = rows[0].screenName;
            document.getElementById("delWinReset").onclick = function () {
                $('#delWin').window('close');
            };
            document.getElementById("delWinDelete").onclick = function () {
                $('#delWinDelete').linkbutton('disable');
                $('#delWin').window('close');
                $.post("${rootPath}/screen/delete_screen", {screenId: rows[0].screenId, status: $("#filterStatus").val()},
                    function (data) {
                        if (data.result === 'true' || data.result === true) {
                            $.messager.alert("提示", '删除成功');
                            $("#winclose").window('close');
                            $('#tables').datagrid('reload', {
                                method: "get",
                                url: 'get_screen_page?page=1&rows=10',
                                status: $("#filterStatus").val()
                            });
                        } else {
                            $.messager.alert("提示", '删除失败');
                        }
                    });
            };
            $('#delWin').window('open');
        } else {
            alert('请选择一条数据删除')
        }
    }
    function save() {
        var r = $('#dataForm').form('validate');
        if (!r) {
            return false;
        } else {
            var screenId = document.getElementById("screenId").value;
            var isAdd = (screenId === '' || screenId.trim().length === 0);
            var url = isAdd ? 'add_screen' : 'update_screen';
            $('#save').linkbutton('disable');
            $.post('${rootPath}/screen/' + url, $('#dataForm').serializeArray(), function (data) {
                if (data.result === 'true' || data.result === true) {
                    $.messager.alert("提示", isAdd ? '添加成功' : '修改成功');
                    $("#addWin").window('close');
                    $('#tables').datagrid('reload', {
                        method: "get",
                        url: 'get_screen_page?page=1&rows=10',
                        status: $("#filterStatus").val()
                    });
                } else {
                    $.messager.alert("提示", isAdd ? '添加失败' : '修改失败');
                    $('#save').linkbutton('enable');
                }
            });
        }
    }
    function initTables() {
        $('#tables').datagrid({
            width: 'auto',
            height: 'auto',
            pagination: true,
            title: '屏幕数据',
            singleSelect: true,
            rownumbers: true,
            striped: true,
            url: 'get_screen_page',
            columns: [[
                {field: 'screenCode', title: '编号', width: '4%', align: 'center'},
                {field: 'screenStatus', title: '状态', width: '4%', align: 'center'},
                {field: 'screenName', title: '名称', width: '15%', halign: 'center'},
                {field: 'spotName', title: '景区名称', width: '15%', halign: 'center'},
                {field: 'screenLongitude', title: '经度', width: '5%', align: 'center'},
                {field: 'screenDimension', title: '维度', width: '5%', align: 'center'},
                {field: 'screenLong', title: '物理高度', width: '4%', align: 'center'},
                {field: 'screenWide', title: '物理宽度', width: '4%', align: 'center'},
                {field: 'screenSize', title: '尺寸', width: '4%', align: 'center'},
                {field: 'screenResolution', title: '分辨率', width: '5%', align: 'center'},
                {field: 'screenLocation', title: '详细地址', width: '15%', halign: 'center'},
                {field: 'screenDescription', title: '描述', width: '20%', halign: 'center'}]],
            onLoadSuccess: function (data) {
                $('#dataTable').datagrid('clearSelections');
            },
            queryParams: {
                status: $("#filterStatus").val()
            },
            rowStyler: function (index, row) {
//                if (index + 1 % 1 === 0) {
//                    return 'background-color:#DEF0D8;color:black;';
//                } else {
//                    return 'background-color:#FEF7E5;color:black;';
//                }
            },
            onSelect: function (rowIndex, rowData) {
            },
            onUnselectAll: function (rows) {
            }
        })
    }
    function filterStatus() {
        $('#tables').datagrid('reload', {
            status: $("#filterStatus").val()
        });
    }
    $(function () {
        document.getElementById("filterStatus").value = "0";
        initTables();
    })
</script>