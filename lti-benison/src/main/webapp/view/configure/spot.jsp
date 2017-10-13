<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>景区管理</title>
    <meta charset="UTF-8">
    <%@ include file="/include.jsp" %>
</head>
<body class="easyui-layout">
<div style="height: 80px; padding-left: 50px ;position: relative; display: flex; flex-direction: row; align-items: center; align-content: center;"
     region="north" title="景区管理" iconCls="icon-search">
    <label style="font-size: 15px">状态筛选</label>
    <select id="filterStatus" onchange="selectChange()" style="width:142px;height: 28px;margin-left: 20px;">
        <option value="">全部</option>
        <option value="0">正常</option>
        <option value="1">暂停</option>
        <option value="-1">删除</option>
    </select>
</div>
<div region="center">
    <div style="padding:5px;background:#fafafa;width:auto;border:1px solid #ccc">
        <a href="#" class="easyui-linkbutton" plain="true" onclick="openWindow('add');"
           iconCls="icon-add">添加</a>
        <a href="#" class="easyui-linkbutton" plain="true" onclick="update()" iconCls="icon-edit">修改</a>
        <a href="#" class="easyui-linkbutton" plain="true" onclick="openDelete()" iconCls="icon-remove">删除</a>
    </div>
    <div id="content" class="easyui-panel" style="height: auto">
        <table id="tables"></table>
    </div>
</div>
</body>
<div id="win" class="easyui-window" title="景区"
     data-options="closed:true,resizable:false,iconCls:'icon-save',modal:true,collapsible:false,maximizable:false,minimizable:false"
     style="padding:50px 120px 50px 120px;width:auto;height:auto;display: flex;flex-flow: column;align-items: center">
    <h1 id="win_title" align="center" style="margin-bottom: 50px">景区添加</h1>
    <div>
        <form id="dataForm">
            <div>
                <input type="hidden" id="spotId" name="spotId">
                <label for="spotName">景区名称:</label><input id="spotName" name="spotName"
                                                          data-options="required:true" class="easyui-validatebox"
                                                          validType="length[2,50]"/><br><br>
                <label for="spotCode">景区编号:</label><input id="spotCode" name="spotCode" data-options="required:true"
                                                          class="easyui-validatebox" validType="length[2,50]"><br><br>
                <lable for="spotStatus">状　　态</lable>
                <select id="spotStatus" name="spotStatus">
                    <option value="0">正常</option>
                    <option value="1">暂停</option>
                    <option value="-1">删除</option>
                </select><br><br>
                <label for="spotProvince">所在省份:</label><input id="spotProvince" data-options="required:true"
                                                              name="spotProvince" class="easyui-validatebox"
                                                              validType="length[2,50]"><br><br>
                <label for="spotCity">所在城市:</label><input id="spotCity" data-options="required:true" name="spotCity"
                                                          class="easyui-validatebox" validType="length[2,50]"><br><br>
                <label for="spotAddress">景区地址:</label><input id="spotAddress" data-options="required:true"
                                                             validType="length[2,50]" class="easyui-validatebox"
                                                             name="spotAddress"><br><br>
                <label for="spotDescription">描述信息:</label><textarea id="spotDescription"
                                                                    name="spotDescription"></textarea>
            </div>
            <div style="padding:5px;text-align:center;padding-top: 100px">
                <a id="save" href="#" class="easyui-linkbutton" icon="icon-ok" style="margin-right: 30px;width: 80px"
                   onclick="requestAdd();">添加</a>
                <a class="easyui-linkbutton" icon="icon-cancel" onclick="reset();" style="width: 80px">取消</a>
            </div>
        </form>
    </div>
</div>
<div id="winclose" class="easyui-window" title="警告"
     data-options="closed:true,resizable:false,iconCls:'icon-save',modal:true,collapsible:false,maximizable:false,minimizable:false"
     style="padding:10px 50px 20px 50px;width:auto;height:auto;display: flex;flex-flow: column;align-items: center">
    <h2>删除景区</h2>
    <div style="font-size: 16px">
        <label>编号:&nbsp;&nbsp;</label><span id="winclose_code"></span><br>
        <label>名称:&nbsp;&nbsp;</label><span id="winclose_name"></span>
    </div>
    <div style="padding:5px;text-align:center;padding-top: 30px">
        <a id="winclose_reset" href="#" class="easyui-linkbutton" icon="icon-undo" style="margin-right: 30px;width: 80px">取消</a>
        <a id="winclose_delete" class="easyui-linkbutton" icon="icon-remove"
           style="width: 80px">删除</a>
    </div>
</div>
</html>
<script type="text/javascript">
    function requestAdd() {
        var id = document.getElementById("spotId").value;
        var isAdd = (id === '' || id.trim().length === 0);
        var r = $('#dataForm').form('validate');
        if (!r) {
            return false;
        } else {
            var id = document.getElementById("spotId").value;
            var isAdd = (id === '' || id.trim().length === 0);
            var url = isAdd ? 'add_spot' : 'update_spot';
            $('#save').linkbutton('disable');
            $.post("${rootPath}/spot/" + url, $("#dataForm").serializeArray(),
                function (data) {
                    if (data.result === 'true' || data.result === true) {
                        $.messager.alert("提示", isAdd ? '添加成功' : '修改成功');
                        $("#win").window('close');
                        $('#tables').datagrid('reload', {
                            method: "get",
                            url: 'get_spot_page?page=1&rows=10',
                            status: $("#filterStatus").val()
                        });
                    } else {
                        $.messager.alert("提示", isAdd ? '添加失败' : '修改失败');
                        $('#save').linkbutton('enable');
                    }
                });
        }
    }
    //重置
    function reset() {
        $("#dataForm")[0].reset();
    }
    //打开添加模板
    function openWindow(method, spot) {
        var title = document.getElementById("win_title");
        var id = document.getElementById("spotId");
        var name = document.getElementById("spotName");
        var code = document.getElementById("spotCode");
        var status = document.getElementById("spotStatus");
        var province = document.getElementById("spotProvince");
        var city = document.getElementById("spotCity");
        var address = document.getElementById("spotAddress");
        var description = document.getElementById("spotDescription");

        if ('add' === method) {
            title.innerText = "景区添加";
            document.getElementById("dataForm").reset();
            $('#save').linkbutton({
                iconCls: 'icon-add',
                text: '添加'
            });
        } else if ('update' === method) {
            title.innerText = "景区修改";
            id.value = spot.spotId;
            name.value = spot.spotName;
            code.value = spot.spotCode;
            status.value = spot.spotStatus;
            province.value = spot.spotProvince;
            city.value = spot.spotCity;
            address.value = spot.spotAddress;
            description.value = spot.spotDescription;
            $('#save').linkbutton({
                iconCls: 'icon-edit',
                text: '修改'
            });
        }
        $('#save').linkbutton('enable');
        $("#win").window('open')
    }
    //初始化数据表格
    function initTables() {
        $('#tables').datagrid({
            width: 'auto',
            height: 'auto',
            pagination: true,
            title: '景区数据',
            singleSelect: true,
            rownumbers: true,
            striped: true,
            iconCls: 'icon-save',
            url: 'get_spot_page',
            columns: [[
                {field: 'spotCode', title: '编号', width: '5%', halign: 'center'},
                {field: 'spotName', title: '名称', width: '15%', halign: 'center'},
                {field: 'spotStatus', title: '状态', width: '5%', align: 'center'},
                {field: 'spotProvince', title: '所在省', width: '10%', halign: 'center'},
                {field: 'spotCity', title: '所在市', width: '15%', halign: 'center'},
                {field: 'spotAddress', title: '详细地址', width: '20%', halign: 'center'},
                {field: 'spotDescription', title: '描述信息', width: '30%', halign: 'center'}]],
            onLoadSuccess: function (data) {
                $('#dataTable').datagrid('clearSelections');
            },
            queryParams: {
                status: $("#filterStatus").val()
            }
        })
    }
    //修改景区
    function update() {
        var rows = $('#tables').datagrid('getSelections');
        if (rows.length === 1) {
            openWindow('update', rows[0]);
        } else {
            alert('请选择一条数据修改')
        }
    }
    //删除景区
    function openDelete() {
        var rows = $('#tables').datagrid('getSelections');
        if (rows.length === 1) {
            var title = document.getElementById("winclose_code");
            var name = document.getElementById("winclose_name");
            title.innerText = rows[0].spotCode;
            name.innerText = rows[0].spotName;
            document.getElementById("winclose_reset").onclick = function () {
                $('#winclose').window('close');
            };
            document.getElementById("winclose_delete").onclick = function () {
                $.post("${rootPath}/spot/delete_spot", {id: rows[0].spotId},
                    function (data) {
                        if (data.result === 'true' || data.result === true) {
                            $.messager.alert("提示", '删除成功');
                            $("#winclose").window('close');
                            $('#tables').datagrid('reload', {
                                method: "get",
                                url: 'get_spot_page?pageNum=1&pageSize=10',
                                status: $("#filterStatus").val()
                            });
                        } else {
                            $.messager.alert("提示", '删除失败');
                        }
                    });
            };
            $('#winclose').window('open');
        } else {
            alert('请选择一条数据删除')
        }
    }
    //选择景区状态，改变数据
    function selectChange() {
        $('#tables').datagrid('reload', {
            status: $("#filterStatus").val()
        });
    }

    $(function () {
        initTables();
    })
</script>