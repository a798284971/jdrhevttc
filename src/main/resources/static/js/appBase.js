/**
 * 页面常量及公用方法
 * Created by yanglong on 2017-08-20.
 */
//登录
var baseUrl = $('#baseUrl').attr("href");
//console.log(baseUrl);
var url_findRoleById = baseUrl + "system/findRoleById";

function loadingStart() {
    if (typeof(layer) != "undefined" && layer != null) {
        return layer.load(1, {
            shade: [0.5, 'gray'] //0.1透明度的白色背景
        });
    }
    return null;
}
function isEmpty(value) {
    return value == null || value == '' || value == undefined;
}
function loadingStop(thisIndex) {
    if (typeof(layer) != "undefined" && layer != null) {
        if (typeof(thisIndex) != "undefined" && thisIndex != null) {
            layer.close(thisIndex);
            return;
        }
        layer.closeAll('loading');
    }
}

function showHint(msg, responseFun) {
    if (typeof(layer) != "undefined" && layer != null) {
        layer.open({
            title: ['提示'],
            content: msg,
            btn: ['确认'],
            closeBtn: 0,
            success: function (layero, index) {
            },
            yes: function (index) {
                layer.close(index);
                if (responseFun != null && responseFun != undefined) {
                    responseFun();
                }
            }
        });
        return;
    }
    alert(msg);
    return;
}

function errorRes(result) {
    //console.log(result);
    loadingStop();
    if(result!=null && (result.status==0 || result.status==200)){
        showHint("登录超时！",function(){
            location.href = "/login";
        })
        return;
    }
    showHint("请求异常，请刷新后重试！");
    return;
}

/**
 * 异步调用
 * @param url
 * @param checkStatusFlag 是否验证返回状态，checkStatusFlag=true且!result.success时，将提示信息弹框显示
 * @param loadFunFlag 忽视验证状态，总是执行回调，与responseFun共同出现
 * @param responseFun
 * @returns {boolean}
 */
function getJson(url, jsonData, checkStatusFlag, loadFunFlag, responseFun, timeOut) {
    var time = timeOut || 20;
    var loadIndex = loadingStart();
    //console.log(url + "?random=" + Math.random());
    //console.log(JSON.stringify(jsonData));
    $.ajax({
        data: jsonData,
        url: url + "?random=" + Math.random(),
        timeout: time * 1000, //超时时间设置，单位毫秒
        dataType: 'json',
        async: true,
        type: 'post',
        error: function (result) {
            errorRes(result);
        },
        success: function (result) {
          /*  console.log(JSON.stringify(result));*/
            //console.log(result);
            loadingStop(loadIndex);
            var flag = true;
            if (checkStatusFlag) {
                if (result == null) {
                    showHint("数据异常，请刷新后重试！");
                    flag = false;
                }
                if (!result.success) {
                    showHint(isEmpty(result.msg)?"返回数据异常，请刷新后重试！":result.msg);
                    flag = false;
                }
            }
            if (loadFunFlag != null && loadFunFlag != undefined && loadFunFlag == true) {
                flag = true;
            }
            if (flag && responseFun != null && responseFun != undefined) {
                responseFun(result);
            }
        }
    })
}

/**
 * 分页查询
 * @param url
 * @param pagesize
 * @param page
 * @param jsonStr
 * @param checkStatusFlag
 * @param responseFun
 */
function getJsonPage(url, pagesize, page, jsonData, checkStatusFlag, responseFun, timeOut,showLoading) {
    var time = timeOut || 20;
    var loadIndex;
    if(showLoading==null || showLoading==undefined){
        showLoading = true;
    }
    if(showLoading){
        loadIndex = loadingStart();
    }
    //console.log(url + "?random=" + Math.random());
    if (jsonData == null || jsonData == undefined) {
        jsonData = new Object();
    }
    jsonData.size = pagesize;
    jsonData.page = page;
    //console.log(jsonData);
    $.ajax({
        data: jsonData,
        url: url + "?random=" + Math.random(),
        timeout: time * 1000, //超时时间设置，单位毫秒
        dataType: 'json',
        async: true,
        type: 'post',
        error: function (result) {
            errorRes(result);
        },
        success: function (result) {
            //console.log(JSON.stringify(data));
            //console.log(result);
            if(loadIndex){
                loadingStop(loadIndex);
            }
            if (checkStatusFlag) {
                if (result == null) {
                    showHint("数据异常，请刷新后重试！");
                }
                if (!result.success) {
                    showHint(isEmpty(result.msg)?"返回数据异常，请刷新后重试！":result.msg);
                }
            }
            if (responseFun != null && responseFun != undefined) {
                responseFun(result);
            }
        }
    })
}

/**
 * 上传附件
 * @param url
 * @param fileName
 * @param files
 * @param jsonStr
 * @param checkStatusFlag
 * @param loadFunFlag
 * @param responseFun
 * @param timeOut
 */
function getJsonAndFile(url, fileName, files, jsonStr, checkStatusFlag, loadFunFlag, responseFun, timeOut) {
    var time = timeOut || 60;
    var loadIndex = loadingStart();
    //console.log(url + "?random=" + Math.random());
    //console.log(fileName);
    //console.log(files);
    //console.log(jsonStr);

    var formData = new FormData();
    formData.append('jsonStr', jsonStr);
    if (files && fileName && files.length > 0) {
        for (var i = 0; i < files.length; i++) {
            formData.append(fileName, files[i]);
        }
    }
    $.ajax({
        data: formData,
        url: url + "?random=" + Math.random(),
        timeout: time * 1000, //超时时间设置，单位毫秒
        dataType: 'json',
        async: true,
        type: 'post',
        cache: false,
        // 告诉jQuery不要去处理发送的数据
        processData: false,
        // 告诉jQuery不要去设置Content-Type请求头
        contentType: false,
        error: function (result) {
            errorRes(result, _layer);
        },
        success: function (result) {
            //console.log(JSON.stringify(data))
            //console.log(result);
            loadingStop(loadIndex);
            var flag = true;
            if (checkStatusFlag) {
                if (result == null) {
                    showHint("数据异常，请刷新后重试！");
                }
                if (!result.success) {
                    showHint(isEmpty(result.msg)?"返回数据异常，请刷新后重试！":result.msg);
                }
            }
            if (loadFunFlag != null && loadFunFlag != undefined && loadFunFlag == true) {
                flag = true;
            }
            if (flag && responseFun != null && responseFun != undefined) {
                responseFun(result);
            }
        }
    })
}

/**
 * 异步调用加载简单数据
 * @param url
 * @param jsonData
 * @param responseFun
 */
function getJsonSimple(url, jsonData, responseFun) {
    //console.log(url + "?random=" + Math.random());
    //console.log(jsonData);
    $.ajax({
        data: jsonData,
        url: url + "?random=" + Math.random(),
        timeout: 20 * 1000, //超时时间设置，单位毫秒
        dataType: 'json',
        async: true,
        type: 'post',
        error: function (result) {
        },
        success: function (result) {
            if (responseFun != null && responseFun != undefined) {
                responseFun(result);
            }
        }
    })
}

function createTableOperation(_url, sendData, createTableFun, tableName, paginationName, responseFun, pageNum, pageSize,showLoading) {
    if(showLoading==null || showLoading==undefined){
        showLoading = true;
    }
    var _tableManager = {
        sendData:sendData,
        _tableName:tableName || "tbody",
        _paginationName:paginationName || "pagination",
        _pageNum: pageNum || 1,
        _pageSize: pageSize || 10,
        _total: 0,
        _showLoading: showLoading,
        init: function () {
            _tableManager = this;
            //console.log("---------------"+_tableManager._tableName);
            _tableManager.loadData();
        },
        loadData: function (page, size) {
            _tableManager._pageNum = page || _tableManager._pageNum;
            _tableManager._pageSize = size || _tableManager._pageSize;
            getJsonPage(_url, _tableManager._pageSize, _tableManager._pageNum, _tableManager.sendData, false, function (result) {
                var list = result == null ? null : result.data == null ? null : result.data.list;
                createTableFun(_tableManager._tableName, list);
                var thisPageSize = _tableManager._pageSize;
                var thisPageNum = 1;
                var thisTotal = _tableManager._total;
                if (list) {
                    _tableManager._pageSize = result == null ? thisPageSize : result.data == null ? thisPageSize : (result.data.pageSize|| thisPageSize);
                    _tableManager._pageNum = result == null ? thisPageNum : result.data == null ? thisPageNum : (result.data.pages|| thisPageNum);
                    _tableManager._total = result == null ? thisTotal : result.data == null ? thisTotal : (result.data.total|| thisTotal);
                  /*  thisPageSize = result.data.pageSize || thisPageSize;
                    thisPageNum = result.data.pages || thisPageNum;
                    _tableManager._total = result.data.total || _tableManager._total;*/
                }
                $("#" + _tableManager._paginationName).bs_pagination({
                    showGoToPage: true,
                    rowsPerPage: _tableManager._pageSize,
                    totalPages: _tableManager._pageNum,
                    totalRows: _tableManager._total,
                    onChangePage: function (event, data) {
                        _tableManager.loadData(data.currentPage, data.rowsPerPage);
                    }
                });
                if (responseFun != null && responseFun != undefined) {
                    responseFun(result);
                }
            },null,_tableManager._showLoading)
        }
    };
    return _tableManager;
}

/**
 * 将获取集合数据转换成分页展示数据
 * @param data
 * @returns {Object}
 */
function createDataTable(data) {
    var dataTable = new Object();
    dataTable.aaData = [];
    dataTable.iTotalRecords = 0;
    dataTable.iTotalDisplayRecords = 0;
    if (data != null && data.rows != null && data.rows.length > 0 && data.total != null && data.total > 0) {
        var rows = data.rows;
        var total = data.total;
        dataTable.aaData = rows;
        dataTable.iTotalRecords = total;
        dataTable.iTotalDisplayRecords = total;
    }
    return dataTable;
}

$.fn.serializeObject = function () {
    var objs = $(this).find(":disabled");
    objs.removeAttr("disabled");
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    objs.attr("disabled","disabled");
    return o;
}

$.fn.setForm = function (jsonValue,prefix) {
    var prefixName = prefix||'';
    var obj = this;
    $.each(jsonValue, function (name, ival) {
        if(prefixName != '') name = prefixName + "." + name;
        var $oinput = obj.find("[name='" + name + "']");
        if ($oinput.attr("type") == "radio" || $oinput.attr("type") == "checkbox") {
            $oinput.each(function () {
                if (Object.prototype.toString.apply(ival) == '[object Array]') {//是复选框，并且是数组
                    for (var i = 0; i < ival.length; i++) {
                        if ($(this).val() == ival[i])
                            $(this).attr("checked", "checked");
                    }
                } else {
                    if ($(this).val() == ival)
                        $(this).attr("checked", "checked");
                }
            });
        } else if ($oinput.attr("type") == "textarea") {//多行文本框
            obj.find("[name='" + name + "']").html(nullToString(ival));
        } else {
            obj.find("[name='" + name + "']").val(nullToString(ival));
        }
    });
}

function formatMoney(val) {
    var reg = /^[0-9]+(.[0-9]{1,2})?$/;// 这里是 正则表达式，大小写英文字母都可以
    return reg.test(val);
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 获取当前时间day天的时间  YYYY-MM-DD
 * @param day
 * @returns {string}
 */
function getDate(day) {
    var today = new Date();
    var thisDate = today;
    if (day != null && day != undefined) {
        thisDate = new Date(today.getTime() + day * 24 * 3600 * 1000);
    }
    var nowdateTime = (thisDate.getFullYear()) + "-" + cunDate(thisDate.getMonth() + 1) + "-" + cunDate(thisDate.getDate());
    return nowdateTime;
}

/**
 * 获取当前时间month月的时间  YYYY-MM-DD
 * @param month
 * @returns {string}
 */
function getMonth(month) {
    var daysInMonth = new Array([0], [31], [28], [31], [30], [31], [30], [31], [31], [30], [31], [30], [31]);
    var thisDate = new Date();
    var _month = thisDate.getMonth() + 1;
    var _year = thisDate.getFullYear();
    var _day = thisDate.getDate();
    if (month) {
        _month = _month + month
    }
    while (_month < 1) {
        _month = _month + 12;
        _year = _year - 1;
    }
    while (_month > 12) {
        _month = _month - 12;
        _year = _year + 1;
    }

    if (_day > daysInMonth[_month]) {
        _day = daysInMonth[_month];
    }
    return (_year) + "-" + cunDate(_month) + "-" + cunDate(_day);
}

/**
 * 获取当前时间month月的时间  YYYY-MM
 * @param month
 * @returns {string}
 */
function getYearMonth(month) {
    var thisDate = new Date();
    var _month = thisDate.getMonth() + 1;
    var _year = thisDate.getFullYear();
    if (month) {
        _month = _month + month
    }
    while (_month < 1) {
        _month = _month + 12;
        _year = _year - 1;
    }
    while (_month > 12) {
        _month = _month - 12;
        _year = _year + 1;
    }
    return (_year) + "-" + cunDate(_month);
}

function getOnlyMonth(month) {
    var thisDate = new Date();
    var _month = thisDate.getMonth() + 1;
    var _year = thisDate.getFullYear();
    if (month) {
        _month = _month + month
    }
    while (_month < 1) {
        _month = _month + 12;
    }
    while (_month > 12) {
        _month = _month - 12;
    }
    return cunDate(_month);
}
function getOnlyDay(day) {
    var today = new Date();
    var thisDate = today;
    if (day != null && day != undefined) {
        thisDate = new Date(today.getTime() + day * 24 * 3600 * 1000);
    }
    return cunDate(thisDate.getDate());
}

/**
 * 获取当前年
 * @returns {string}
 */
function getYear() {
    var today = new Date();
    return today.getFullYear();
}

function formatDate(date) {
    if (date == null) {
        return null;
    }
    date = new Date(date);
    if (date && date.getFullYear() && date.getMonth() && date.getDate()) {
        var nowdateTime = (date.getFullYear()) + "-" + cunDate(date.getMonth() + 1) + "-" + cunDate(date.getDate());
        return nowdateTime;
    }
    return getDate();
}
function cunDate(date) {
    if (date < 10) {
        return "0" + date;
    }
    return date;
}

function getStrDateYMD(andSeconds,date) {
    var _date = date||new Date();
    var _date = new Date(_date.getTime() + andSeconds * 1000);
    return _date.getFullYear() + "-" + cunDate(_date.getMonth() + 1) + "-" + cunDate(_date.getDate());
}

function getStrDateMD(andSeconds,date) {
    var _date = date||new Date();
    var _date = new Date(_date.getTime() + andSeconds * 1000);
    return cunDate(_date.getMonth() + 1) + "-" + cunDate(_date.getDate());
}

function getStrDateYMD000(andSeconds,date) {
    var _date = date||new Date();
    var _date = new Date(_date.getTime() + andSeconds * 1000);
    return _date.getFullYear() + "-" + cunDate(_date.getMonth() + 1) + "-" + cunDate(_date.getDate()) + " 00:00:00";
}

function getStrDateYMDHMS(andSeconds,date) {
    var _date = date||new Date();
    andSeconds = andSeconds||0;
    var _date = new Date(_date.getTime() + andSeconds * 1000);
    return _date.getFullYear() + "-" + cunDate(_date.getMonth() + 1) + "-" + cunDate(_date.getDate()) + " " + cunDate(_date.getHours()) + ":" + cunDate(_date.getMinutes()) + ":" + cunDate(_date.getSeconds());
}
function getStrDateHMS(andSeconds,date) {
    var _date = date||new Date;
    andSeconds = andSeconds||0;
    var _date = new Date(_date.getTime() + andSeconds * 1000);
    return cunDate(_date.getHours()) + ":" + cunDate(_date.getMinutes()) + ":" + cunDate(_date.getSeconds());
}
function getStrDateHM(andSeconds,date) {
    var _date = date||new Date;
    andSeconds = andSeconds||0;
    //console.log(_date);
    var _date = new Date(_date.getTime() + andSeconds * 1000);
    //console.log(_date);
    return cunDate(_date.getHours()) + ":" + cunDate(_date.getMinutes());
}
/**
 * 加载下拉框
 * @param name 元素id名称
 * @param type 类型 1包含全部、2编辑框不包含全部
 * @param list 加载数据
 */
function initSelect(name, type, list) {
    var typeHtml = "";
    if (type == 1) {
        typeHtml += "<option value=''>全部</option>";
    }
    if (list != null && list != undefined) {
        for (var i = 0; i < list.length; i++) {
            typeHtml += "<option value='" + (i + 1) + "'>" + list[i] + "</option>";
        }
    }
    $("#" + name).html(typeHtml);
}

function nullToString(data) {
    if (data == null || data == undefined) {
        return "";
    }
    return data;
}

function isNull(data) {
    if (data == null || data == undefined) {
        return true;
    }
    return false;
}

function isEmpty(data) {
    if (data == null || data == undefined || data == "") {
        return true;
    }
    return false;
}

//功能：将浮点数四舍五入，取小数点后2位
function toDecimal(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return;
    }
    f = Math.round(x*100)/100;
    return f;
}


//制保留2位小数，如：2，会在2后面补上00.即2.00
function toDecimal2(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x*100)/100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}

function onlyMoney(name) {
    var num = $("#" + name).val();
    var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
    if (!exp.test(num)) {
        return false;
    }
    return true;
}

function onlyValMoney(val) {
    var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
    if (!exp.test(val)) {
        return false;
    }
    return true;
}

function onlyValPercent(val) {
    //console.log(val);
    var exp = /^([1-9]|[1-9]\d|100)$/;
    //console.log(exp.test(val));
    if (!exp.test(val)) {
        return false;
    }
    return true;
}

function createPickerYMD(obj, val,onChangeDate, position) {
    var _position = position || 'top-right';
    var datetimepickerOption = {
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        pickerPosition: _position
    };
    obj.datetimepicker(datetimepickerOption).on('changeDate', function(ev){
        if (onChangeDate!=null && onChangeDate!=undefined){
            onChangeDate(ev);
        }
    });
    if (val != null && val != undefined) {
        obj.val(val);
    }
}

function createPickerYMDHMS(obj, val,onChangeDate, position) {
    var _position = position || 'top-right';
    var datetimepickerOption = {
        language: 'zh-CN',
        format: 'yyyy-mm-dd hh:ii:ss',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1,
        pickerPosition: _position
    };
    obj.datetimepicker(datetimepickerOption).on('changeDate', function(ev){
        if (onChangeDate!=null && onChangeDate!=undefined){
            onChangeDate(ev);
        }
    });
    if (val != null && val != undefined) {
        obj.val(val);
    }
}

function createPickerHMS(obj, val,onChangeDate, position) {
    var _position = position || 'top-right';
    var datetimepickerOption = {
        language: 'zh-CN',
        format: 'hh:ii:ss',
        autoclose: 1,
        todayHighlight: 1,
        startView: 1,
        minView: 0,
        maxView: 0,
        forceParse: 0,
        pickerPosition: _position
    };
    obj.datetimepicker(datetimepickerOption).on('changeDate', function(ev){
        if (onChangeDate!=null && onChangeDate!=undefined){
            onChangeDate(ev);
        }
    });
    if (val != null && val != undefined) {
        obj.val(val);
    }
}

function createPickerHM(obj, val,onChangeDate, position) {
    var _position = position || 'top-right';
    var datetimepickerOption = {
        language: 'zh-CN',
        format: 'hh:ii',
        autoclose: 1,
        todayHighlight: 1,
        startView: 1,
        minView: 0,
        maxView: 0,
        forceParse: 0,
        pickerPosition: _position
    };
    obj.datetimepicker(datetimepickerOption).on('changeDate', function(ev){
        if (onChangeDate!=null && onChangeDate!=undefined){
            onChangeDate(ev);
        }
    });
    if (val != null && val != undefined) {
        obj.val(val);
    }
}

/**
 * 获取给定日期为星期几
 * @param date 日期类型
 * @returns {*}
 */
function getWeek(date) {
    if (isEmpty(date)) {
        return "";
    }
    var a = new Array("日", "一", "二", "三", "四", "五", "六");
    var week = date.getDay();
    return "星期" + a[week];
}
/**
 * 获取给定日期为星期几
 * @param date 字符串类型
 * @returns {*}
 */
function getWeekByStrDate(date) {
    if (isEmpty(date)) {
        return "";
    }
    var a = new Array("日", "一", "二", "三", "四", "五", "六");
    var week = new Date(date).getDay();
    return "星期" + a[week];
}

/**
 * 根据
 * @param str
 * @returns {*}
 */
function getAgeByStrDate(date) {
    var r = date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
    if (r == null)return false;
    var d = new Date(r[1], r[3] - 1, r[4]);
    if (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]) {
        var Y = new Date().getFullYear();
        return (Y - r[1]);
    }
    return "";
}

function initDepartmentSelect(suffix, haveAll,defVal,responseFun) {
    var _departmentUrl = baseUrl + "reception/listSysDepartmentAll";
    suffix = suffix || "";
    var _department = $("#departmentId" + suffix);
    _department.empty();
    if (haveAll) {
        _department.html('<option value="">全部</option>');
    }
    getJsonSimple(_departmentUrl, {}, function (result) {
        if (result && result.data && result.data.length > 0) {
            for (var i = 0; i < result.data.length; i++) {
                var one = result.data[i];
                if(defVal!=null && defVal!=undefined && defVal==one.id){
                    _department.append('<option value="' + one.id + '" selected="selected">' + one.name + '</option>');
                }else{
                    _department.append('<option value="' + one.id + '">' + one.name + '</option>');
                }
            }
        }
        if(responseFun!=null && responseFun!=undefined){
            responseFun();
        }
    });
}

function initDoctorSelect(suffix, departmentId, other,defVal,resCountFlag) {
    var _doctorUrl = baseUrl + "reception/listDoctorAllByDepartmentId";
    if(resCountFlag){
        _doctorUrl = baseUrl + "reception/listDoctorResCountAllByDepartmentId";
    }
    suffix = suffix || "";
    //console.log("#doctorId" + suffix);
    var _doctor = $("#doctorId" + suffix);
    _doctor.empty();
    var type = other=='全部'?1:2;
    if (other!=null) {
        _doctor.html('<option value="">'+other+'</option>');
    }


    if (departmentId != null && departmentId > 0) {
        getJsonSimple(_doctorUrl, {type:type,departmentId: departmentId}, function (result) {
            if (result && result.data && result.data.length > 0) {
                for (var i = 0; i < result.data.length; i++) {
                    var one = result.data[i];
                    var countStr = '';
                    if(resCountFlag){
                        countStr = '('+(isEmpty(one.reservationCount)?0:one.reservationCount)+'人)';
                    }
                    if(defVal!=null && defVal!=undefined && defVal==one.id){
                        _doctor.append('<option value="' + one.id + '" selected="selected">' + one.name +countStr+'</option>');
                    }else{
                        _doctor.append('<option value="' + one.id + '">' + one.name +countStr+ '</option>');
                    }

                }
            }
        });
    }
}

function getIdsByCheckBox(name) {
    var _ckb = $("input[name='" + name + "']:checked");
    var ids = "";
    for (var i = 0; i < _ckb.length; i++) {
        if (!isEmpty(ids)) {
            ids += ',';
        }
        ids += $(_ckb[i]).attr("val");
    }
    return ids;
}

//关闭读写器
function readerClose(MWRFATL) {
    try {
        var result = MWRFATL.closeReader();
        if (MWRFATL.LastRet != 0) {
            return 1;//关闭读写器失败
        }else {
            return 0;//读写器已关闭
        }
    }
    catch (e) {
        return -1;//异常
    }
}


//打开读写器，打开卡片
function openReaderAndCard(MWRFATL,block){
    var result = new Object();
    result.status = -1;
    try {
        //打开读写器
        var version = MWRFATL.openReader(1, 9600);
        if (MWRFATL.LastRet != 0) {
            result.error = "打开读写器失败，请重试";
            readerClose(MWRFATL);
            return result;
        }
        //读写器鸣响
        MWRFATL.readerBeep(60);
        if (MWRFATL.LastRet != 0) {
            result.error = "读写器鸣响失败，请重试";
            readerClose(MWRFATL);
            return result;
        }
        //打开卡片
        MWRFATL.openCard(1, 16); //打开卡片,让其显示16进制字符串卡号
        if (MWRFATL.LastRet != 0) {
            result.error = "打开卡片失败，请重试";
            readerClose(MWRFATL);
            return result;
        }
        MWRFATL.cardDirVerifyPassword(0, block, "ffffffffffff"); //直接密码认证,验证4块所在扇区密码(1扇区),此函数传入的是块号
        if (MWRFATL.LastRet != 0) {
            result.error = "验证密码失败，请重试";
            readerClose(MWRFATL);
            return result;
        }
        var msg = MWRFATL.cardRead(block);
        if (MWRFATL.LastRet != 0) {
            result.error = "读数据失败，请重试";
            readerClose(MWRFATL);
            return result;
        }
        result.msg = msg;
        result.status = 0;
        return result;
    }catch (e) {
        result.error = "请先安装读卡器控件！";
        readerClose(MWRFATL);
        return result;
    }
}

//读卡内容
function readCardMsg(MWRFATL,block){
    var result = openReaderAndCard(MWRFATL,block);
    if(result){ //打开读写器且打开卡片成功
        if(result.status==0){
            result.status = -1;
            var msg = MWRFATL.cardRead(block);
            if (MWRFATL.LastRet != 0) {
                result.error = "读数据失败，请重试";
                readerClose(MWRFATL);
                return result;
            }
            result.msg = msg;
            result.status = 0;
            readerClose(MWRFATL);
            return result;
        }else{
            showHint(result.error);
        }
        readerClose(MWRFATL);
        return result;
    }
    result = new Object();
    result.error = "读数据失败，请重试";
    result.status = -1;
    readerClose(MWRFATL);
    return result;
}

//写卡内容
function writeCardMsg(MWRFATL,block,msg){
    var result = openReaderAndCard(MWRFATL,block);
    if(result){ //打开读写器且打开卡片成功
        if(result.status==0){
            result.status = -1;
            MWRFATL.cardWrite(block, msg);
            if (MWRFATL.LastRet != 0) {
                result.error = "写数据失败，请重试";
                readerClose(MWRFATL);
                return result;
            }
            result.status = 0;
            readerClose(MWRFATL);
            return result;
        }
        readerClose(MWRFATL);
        return result;
    }
    result = new Object();
    result.error = "写数据失败，请重试";
    result.status = -1;
    readerClose(MWRFATL);
    return result;
}

//收费状态(0-待收费/1-已收费/2-取消)
var tollStateName = ["待收费","已收费","取消"];
//治疗状态(0-待治疗 1-治疗中 2-完成 3-取消）
var treatmentStateName = ["待治疗","治疗中","完成","取消"];