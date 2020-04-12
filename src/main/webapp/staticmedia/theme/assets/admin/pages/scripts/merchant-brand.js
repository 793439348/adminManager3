var list = function () {

    var tables = function () {
        var tableList = $('#table-list');
        var tablePagelist = tableList.find('.page-list');
        var getSearchParams = function() {
            var merchant = tableList.find('select[name="merchant"]').find("option:selected").val();
            var name = tableList.find('input[name="brand-name"]').val();
            return {merchant:merchant,name:name};
        };
        var pagination = $.pagination({
            render: tablePagelist,
            pageSize: 10,
            ajaxType: 'post',
            ajaxUrl: './merchant-brand/list',
            ajaxData: getSearchParams,
            beforeSend: function () {
            },
            complete: function () {
            },
            success: function (list) {
                var table = tableList.find('table > tbody').empty();
                var innerHtml = '';
                $.each(list, function (idx, val) {
                    var type = '';
                    if (val.status == 0) {
                        type = '<form>' +
                            '<input type="radio" name="m-type" value="0" checked>启用' +
                            '<input type="radio" name="m-type" value="1">停用' +
                            '</form>';
                    } else if (val.status == 1) {
                        type = '<form>' +
                            '<input type="radio" name="m-type" value="0" >启用' +
                            '<input type="radio" name="m-type" value="1" checked>停用' +
                            '</form>';
                    }
                    var templeImg = '-';
                    var mtempleImg = '-';
                    if (val.templete) {
                        templeImg = val.templete.smallImage + "?" + Math.random();
                    }
                    if (val.mtemplete) {
                        mtempleImg = val.mtemplete.smallImage + "?" + Math.random();

                    }
                    innerHtml +=
                        '<tr class="align-center" data-id="' + val.id + '">' +
                        '<td>' + val.id + '</td>' +
                        '<td>' + val.merchantCode + '</td>' +
                        '<td>' + val.name + '</td>' +
                        '<td>' + val.code + '</td>' +
                        '<td>' + '<img src="' + templeImg + '" alt="图片" width="40" height="40">' + '</td>' +
                        '<td>' + '<img src="' + mtempleImg + '" alt="图片" width="40" height="40">' + '</td>' +
                        '<td>' + type + '</td>' +
                        '<td>' +
                        '<a data-command="edit" href="javascript:;" class="btn default btn-xs black"><i class="fa fa-edit"></i> 修改 </a>' +
                        '</td>' +
                        '</tr>';
                });
                table.html(innerHtml);
                table.find('[data-command="edit"]').unbind('click').click(function () {
                    var id = $(this).parents('tr').attr('data-id');
                    doLoad(id, 'edit');
                });
                $("input[name=m-type]").click(function () {

                    var id = $(this).parents('tr').attr('data-id');
                    var status = $(this).val();
                    var msg = '确定要修改状态？';
                    bootbox.dialog({
                        message: msg,
                        title: '提示消息',
                        buttons: {
                            success: {
                                label: '<i class="fa fa-check"></i> 确定',
                                className: 'green-meadow',
                                callback: function () {
                                    updateStatus(id, status);
                                }
                            },
                            danger: {
                                label: '<i class="fa fa-undo"></i> 取消',
                                className: 'btn-danger',
                                callback: function () {
                                }
                            }
                        }
                    });
                });

            },
            pageError: function (response) {
                bootbox.dialog({
                    message: response.message,
                    title: '提示消息',
                    buttons: {
                        success: {
                            label: '<i class="fa fa-check"></i> 确定',
                            className: 'btn-success',
                            callback: function () {
                            }
                        }
                    }
                });
            },
            emptyData: function () {
                var tds = tableList.find('thead tr th').size();
                tableList.find('table > tbody').html('<tr><td colspan="' + tds + '">没有相关数据</td></tr>');
            }
        });

        var doLoad = function (id, action) {
            var url = './merchant-brand/get';
            var params = {id: id};
            $.ajax({
                type: 'post',
                url: url,
                data: params,
                dataType: 'json',
                success: function (data) {
                    if (data.error == 0) {
                        EditModal.show(action, data);
                    }
                    if (data.error == 1 || data.error == 2) {
                        toastr['error']('操作失败！' + data.message, '操作提示');
                    }
                }
            });
        }

        var updateStatus = function (id, status) {
            var params = {id: id, status: status};
            var url = './merchant-brand/modify-type';
            $.ajax({
                type: 'post',
                url: url,
                data: params,
                dataType: 'json',
                success: function (data) {
                    if (data.error == 0) {
                        reload();
                        toastr['success']('操作成功', '操作提示');
                    }
                    if (data.error == 1 || data.error == 2) {
                        toastr['error']('操作失败！' + data.message, '操作提示');
                    }
                }
            });
        }

        tableList.find('[data-command="search"]').unbind('click').click(function () {
            init();
        });
        tableList.find('[data-command="add"]').unbind('click').click(function () {
            AddModal.show();
        });
        var init = function () {
            pagination.init();
        };
        var reload = function () {
            pagination.reload();
        };
        return {
            init: init,
            reload: reload
        }
    }();

    var EditModal = function () {
        var modal = $('#modal-edit');
        var form = modal.find('form');
        var initForm = function () {
            form.validate({
                rules: {
                    name: {
                        required: true,
                        minlength: 4,
                        maxlength: 20
                    },
                    code: {required: true}
                },
                messages: {
                    name: {
                        required: '品牌名称不能为空！',
                        minlength: '至少输入{0}个字符',
                        maxlength: '最多输入{0}个字符'
                    },
                    code: {
                        required: '品牌名称不能为空！'
                    }
                },
                invalidHandler: function (event, validator) {
                },
                errorPlacement: function (error, element) {
                    $(element).closest('.form-group').find('.help-inline').html('<i class="fa fa-warning"></i> ' + error.text());
                },
                highlight: function (element) {
                    $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                },
                unhighlight: function (element) {
                    $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
                    $(element).closest('.form-group').find('.help-inline').html('<i class="fa fa-check"></i> 填写正确。');
                }
            });
            modal.find('[data-command="submit"]').unbind('click').click(function () {
                if (form.validate().form()) {
                    doSubmit();
                }
            });
        }
        var isSending = false;
        var doSubmit = function () {
            if (isSending) return;
            var action = modal.attr('data-action');
            var params = {};
            var url = './merchant-brand/update';
            var id = modal.attr('data-id');
            var merchantCode = form.find('select[name="merchantCode"]').find("option:selected").val();
            var name = form.find('input[name="name"]').val();
            var code = form.find('input[name="code"]').val();
            var status = form.find('input[name="status"]:checked').val();
            var template = form.find('select[name="template"]').find("option:selected").val();
            var mtemplate = form.find('select[name="mtemplate"]').find("option:selected").val();

            params = {
                id: id,
                merchantId: merchantCode,
                name: name,
                code: code,
                templete: template,
                mtemplete: mtemplate,
                status: status
            };
            isSending = true;
            $.ajax({
                type: 'post',
                url: url,
                data: params,
                dataType: 'json',
                success: function (data) {
                    isSending = false;
                    if (data.error == 0) {
                        modal.modal('hide');
                        tables.init();
                        toastr['success']('修改成功！', '操作提示');
                    }
                    if (data.error == 1 || data.error == 2) {
                        toastr['error']('修改失败！' + data.message, '操作提示');
                    }
                }
            });
        };
        var loadMarchant = function (code) {
            var url = './merchant/getlist';
            $.ajax({
                type: 'post',
                url: url,
                data: {},
                dataType: 'json',
                success: function (data) {
                    marchantList(data, code);
                }
            });
        }
        var marchantList = function (data, code) {
            var marchant = form.find('select[name="merchantCode"]');
            marchant.empty();
            $.each(data, function (idx, val) {
                if (val.code == code)
                    marchant.append('<option value="' + val.id + '" selected>' + val.code + '</option>');
                else
                    marchant.append('<option value="' + val.id + '">' + val.code + '</option>');
            });
        }

        var loadTemplateImg = function (tempId, mtempId) {
            var url = './site-template/list';
            $.ajax({
                type: 'post',
                url: url,
                data: {},
                dataType: 'json',
                success: function (data) {
                    templateList(tempId, mtempId, data);
                }
            });
        }
        var templateList = function (tempId, mtempId, data) {
            var selt1 = modal.find('select[name="template"]');
            var selt2 = modal.find('select[name="mtemplate"]');
            selt1.empty();
            selt2.empty();
            $.each(data, function (idx, val) {
                if (val.type == 1) {

                    if (tempId != 0 && val.id == tempId) {
                        selt1.append('<option value="' + val.code + '" selected>' + val.code + '</option>');
                        selt1.next().attr("src", "")
                        selt1.next().attr("src", (val.smallImage + "?" + Math.random()).toString())
                    } else if (tempId == 0) {
                        selt1.next().attr("src", "-");
                    } else {
                        selt1.append('<option value="' + val.code + '">' + val.code + '</option>');
                    }
                } else if (val.type == 2) {
                    if (mtempId != 0 && val.id == mtempId) {
                        selt2.append('<option value="' + val.code + '" selected>' + val.code + '</option>');
                        selt2.next().attr("src", "")
                        selt2.next().attr("src", (val.smallImage + "?" + Math.random()).toString())
                    }else if(mtempId == 0){
                        selt2.next().attr("src", "-");
                    }else {
                        selt2.append('<option value="' + val.code + '">' + val.code + '</option>');
                    }
                }

            });
        }

        var show = function (action, data) {
            form[0].reset();
            modal.attr('data-action', 'edit');
            modal.find('.modal-title').html('修改品牌');
            modal.attr('data-id', data.bean.id);
            form.find('input[name="name"]').val(data.bean.name);
            form.find('input[name="code"]').val(data.bean.code);
            form.find('input[name="status"][value="' + data.bean.status + '"]').attr('checked', true);

            loadMarchant(data.bean.merchantCode);

            if (data.bean.templete && data.bean.mtemplete) {
                loadTemplateImg(data.bean.templete.id, data.bean.mtemplete.id);
            } else if (data.bean.templete) {
                loadTemplateImg(data.bean.templete.id, 0);
            } else if (data.bean.mtemplete) {
                loadTemplateImg(0, data.bean.mtemplete.id);
            } else {
                loadTemplateImg(0, 0)
            }

            Metronic.initAjax();
            form.find('.help-inline').empty();
            form.find('.has-error').removeClass('has-error');
            form.find('.has-success').removeClass('has-success');
            modal.modal('show');
        };

        var init = function () {
            initForm();
        };

        return {
            init: init,
            show: show
        }

    }();
    var AddModal = function () {
        var modal = $('#modal-add');
        var form = $('form:last');

        var initForm = function () {
            form.validate({
                rules: {
                    name: {
                        required: true,
                        minlength: 4,
                        maxlength: 20
                    },
                    code: {
                        required: true,
                        minlength: 4,
                        maxlength: 10,
                        remote: {
                            url: '/merchant-brand/notexists',
                            type: 'post'
                        }
                    }
                },
                messages: {
                    name: {
                        required: '品牌名称不能为空！',
                        minlength: '至少输入{0}个字符',
                        maxlength: '最多输入{0}个字符'
                    },
                    code: {
                        required: '品牌代号不能为空！',
                        minlength: '至少输入{0}个字符',
                        maxlength: '最多输入{0}个字符',
                        remote: '品牌代号已存在！'
                    }
                },
                invalidHandler: function (event, validator) {
                },
                errorPlacement: function (error, element) {
                    $(element).closest('.form-group').find('.help-inline').html('<i class="fa fa-warning"></i> ' + error.text());
                },
                highlight: function (element) {
                    $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                },
                unhighlight: function (element) {
                    $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
                    $(element).closest('.form-group').find('.help-inline').html('<i class="fa fa-check"></i> 填写正确。');
                }
            });
            modal.find('[data-command="submit"]').unbind('click').click(function () {
                if (form.validate().form()) {
                    doSubmit();
                }
            });
        };

        var isSending = false;
        var doSubmit = function () {
            if (isSending) return;
            var action = modal.attr('data-action');
            var params = {};
            var url = './merchant-brand/add';

            var merchantCode = form.find('select[name="merchantCode"]').find("option:selected").val();
            var name = form.find('input[name="name"]').val();
            var code = form.find('input[name="code"]').val();
            var status = form.find('input[name="status"]:checked').val();
            var template = form.find('select[name="template"]').find("option:selected").val();
            var mtemplate = form.find('select[name="mtemplate"]').find("option:selected").val();
            params = {
                merchantCode: merchantCode,
                name: name,
                code: code,
                status: status,
                templete: template,
                mtemplete: mtemplate
            };
            isSending = true;
            $.ajax({
                type: 'post',
                url: url,
                data: params,
                dataType: 'json',
                success: function (data) {
                    isSending = false;
                    if (data.error == 0) {
                        modal.modal('hide');
                        tables.init();
                        toastr['success']('添加完成！', '操作提示');
                    }
                    if (data.error == 1 || data.error == 2) {
                        toastr['error']('添加失败！' + data.message, '操作提示');
                    }
                }
            });
        }
        var loadMarchant = function () {
            var url = './merchant/getlist';
            $.ajax({
                type: 'post',
                url: url,
                data: {},
                dataType: 'json',
                success: function (data) {
                    merchantList(data);
                }
            });
        }
        var merchantList = function (data) {
            var merchant = form.find('select[name="merchantCode"]');
            merchant.empty();
            $.each(data, function (idx, val) {
                if (idx == 0)
                    merchant.append('<option value="' + val.id + '" selected>' + val.code + '</option>');
                else
                    merchant.append('<option value="' + val.id + '">' + val.code + '</option>');
            });
        }

        var loadMerchantList = function () {
            var merchant = $('select[name="merchant"]');
            merchant.empty();
            $.ajax({
                type: 'post',
                url: './merchant/getlist',
                data: {},
                dataType: 'json',
                success: function (data) {
                    merchant.append('<option value="' + 0 + '" selected>' + "全部" + '</option>');
                    $.each(data, function (idx, val) {
                        merchant.append('<option value="' + val.id + '">' + val.code + '</option>');
                    });
                }
            });
        }
        var loadTemplateImg = function (select, type) {
            var url = './site-template/list';
            $.ajax({
                type: 'post',
                url: url,
                data: {},
                dataType: 'json',
                success: function (data) {
                    templateList(data, select, type);
                }
            });
        }
        var templateList = function (data, select, type) {
            var sel = $(select);
            sel.empty();
            var boo1 = true;
            var boo2 = true;
            $.each(data, function (idx, val) {
                if (val.type == type && type == 1 && boo1) {
                    boo1 = false;
                    sel.append('<option value="' + val.code + '" selected>' + val.code + '</option>');
                    sel.next().attr("src", (val.smallImage + "?" + Math.random()).toString());
                } else if (val.type == type && type == 1) {
                    sel.append('<option value="' + val.code + '">' + val.code + '</option>');
                } else if (val.type == type && type == 2 && boo2) {
                    boo2 = false;
                    sel.append('<option value="' + val.code + '" selected>' + val.code + '</option>');
                    sel.next().attr("src", (val.smallImage + "?" + Math.random()).toString());
                } else if (val.type == type && type == 2) {
                    sel.append('<option value="' + val.code + '">' + val.code + '</option>')
                }

            });
        }

        var show = function () {
            form[0].reset();
            modal.attr('data-action', 'add');
            modal.removeAttr('data-id');
            modal.find('.modal-title').html('添加品牌');

            loadMarchant();
            loadTemplateImg(form.find('select[name="template"]'), 1);
            loadTemplateImg(form.find('select[name="mtemplate"]'), 2);

            Metronic.initAjax();

            form.find('.help-inline').empty();
            form.find('.has-error').removeClass('has-error');
            form.find('.has-success').removeClass('has-success');
            modal.modal('show');
        }

        var init = function () {
            initForm();
            loadMerchantList();
        }

        return {
            init: init,
            show: show
        }

    }();


    return {
        init: function () {
            tables.init();
            EditModal.init();
            AddModal.init();
        }
    }
}();

function showTempIMG(selt) {
    var code = $(selt).find("option:selected").val();
    $.ajax({
        type: 'post',
        url: '/site-template/getbycode',
        data: 'code=' + code,
        dataType: "json",
        success: function (data) {
            $(selt).next().attr("src", data.smallImage + "?" + Math.random());
        }
    })
};