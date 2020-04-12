var list = function() {

    var tables = function () {
        var tableList = $('#table-list');
        var tablePagelist = tableList.find('.page-list');
        var getSearchParams = function() {
            var name = tableList.find('input[name="merchant-name"]').val();
            var code = tableList.find('input[name="merchant-id"]').val();
            var status = tableList.find('select[name="type"]').val();
            return {name: name, code: code, status: status};
        };
        var pagination = $.pagination({
            render: tablePagelist,
            pageSize: 10,
            ajaxType: 'post',
            ajaxUrl: './merchant/list',
            ajaxData: getSearchParams,
            beforeSend: function() {
            },
            complete: function() {
            },
            success: function(list) {
                var table = tableList.find('table > tbody').empty();
                var innerHtml = '';
                $.each(list, function(idx, val) {
                    var statusSelc = '';
                    if (val.status == 1) {
                        statusSelc = '<form>' +
                            '<input type="radio" name="m-type" value="1" checked>启用' +
                            '<input type="radio" name="m-type" value="2">停用' +
                            '<input type="radio" name="m-type" value="3">关闭' +
                            '<input type="radio" name="m-type" value="4">维护' +
                            '</form>';
                    } else if (val.status == 2) {
                        statusSelc = '<form>' +
                            '<input type="radio" name="m-type" value="1" >启用' +
                            '<input type="radio" name="m-type" value="2" checked>停用' +
                            '<input type="radio" name="m-type" value="3">关闭' +
                            '<input type="radio" name="m-type" value="4">维护' +
                            '</form>';
                    } else if (val.status == 3) {
                        statusSelc = '<form>' +
                            '<input type="radio" name="m-type" value="1" >启用' +
                            '<input type="radio" name="m-type" value="2">停用' +
                            '<input type="radio" name="m-type" value="3" checked>关闭' +
                            '<input type="radio" name="m-type" value="4">维护' +
                            '</form>';
                    } else if (val.status == 4) {
                        statusSelc = '<form>' +
                            '<input type="radio" name="m-type" value="1" >启用' +
                            '<input type="radio" name="m-type" value="2">停用' +
                            '<input type="radio" name="m-type" value="3">关闭' +
                            '<input type="radio" name="m-type" value="4" checked>维护' +
                            '</form>';
                    }
                    innerHtml +=
                        '<tr class="align-center" data-id="' + val.id + '">'+
                        '<td>' + val.id + '</td>' +
                        '<td>' + val.nickname + '</td>' +
                        '<td>' + val.code + '</td>' +
                        '<td>' + val.name + '</td>' +
                        '<td>' + val.balance + '</td>' +
                        '<td>' + statusSelc + '</td>' +
                        '<td>' + val.userNumber + '</td>' +
                        '<td>' + val.createTime + '</td>' +
                        '<td>' + val.loginTime + '</td>' +
                        '<td>' +
                        '<a data-command="edit" href="javascript:;" class="btn default btn-xs black"><i class="fa fa-edit"></i> 修改 </a>' +
                        '</td>'+
                        '</tr>';
                });
                table.html(innerHtml);
                table.find('[data-command="edit"]').unbind('click').click(function() {
                    var id = $(this).parents('tr').attr('data-id');
                    doLoad(id, 'edit');
                });
                $("input[name=m-type]").click(function(){
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
                                callback: function() {
                                    updateStatus(id, status);
                                }
                            },
                            danger: {
                                label: '<i class="fa fa-undo"></i> 取消',
                                className: 'btn-danger',
                                callback: function() {}
                            }
                        }
                    });
                });

            },
            pageError: function(response) {
                bootbox.dialog({
                    message: response.message,
                    title: '提示消息',
                    buttons: {
                        success: {
                            label: '<i class="fa fa-check"></i> 确定',
                            className: 'btn-success',
                            callback: function() {}
                        }
                    }
                });
            },
            emptyData: function() {
                var tds = tableList.find('thead tr th').size();
                tableList.find('table > tbody').html('<tr><td colspan="'+tds+'">没有相关数据</td></tr>');
            }
        });

        var doLoad = function(id, action) {
            var url = './merchant/get';
            var params = {id: id};
            $.ajax({
                type : 'post',
                url : url,
                data : params,
                dataType : 'json',
                success : function(data) {
                    if(data.error == 0) {
                        EditModal.show(action, data);
                    }
                    if(data.error == 1 || data.error == 2) {
                        toastr['error']('操作失败！' + data.message, '操作提示');
                    }
                }
            });
        }

        var updateStatus = function(id, status) {
            var params = {id: id, status: status};
            var url = './merchant/modify-type';
            $.ajax({
                type : 'post',
                url : url,
                data : params,
                dataType : 'json',
                success : function(data) {
                    if(data.error == 0) {
                        reload();
                        toastr['success']('操作成功', '操作提示');
                    }
                    if(data.error == 1 || data.error == 2) {
                        toastr['error']('操作失败！' + data.message, '操作提示');
                    }
                }
            });
        }

        tableList.find('[data-command="search"]').unbind('click').click(function() {
            init();
        });
        tableList.find('[data-command="add"]').unbind('click').click(function() {
            AddModal.show();
        });
        var init = function() {
            pagination.init();
        };
        var reload = function() {
            pagination.reload();
        };
        return {
            init: init,
            reload: reload
        }
    }();

    var EditModal = function() {
        var modal = $('#modal-edit');
        var form = modal.find('form');

        var initForm = function() {
            form.validate({
                rules: {
                    nickname: {
                        required: true
                    },
                    code: {
                        required: true,
                        minlength: 4,
                        maxlength: 4,
                        isInegal: true
                    },
                    account: {
                        required: true,
                        isInegal: true
                    },
                    phone: {
                        required: true,
                        isPhone: true
                    },
                    email:{
                        required: true,
                        isEmail: true
                    },
                    qq:{
                        required: true,
                        isQQ: true
                    },
                    wechat:{
                        required: true,
                        isWechat: true
                    }
                },
                messages: {
                    nickname: {
                        required: '商户别名不能为空！'
                    },
                    code: {
                        required: '商户Id不能为空！',
                        minlength: '至少输入{0}个字符',
                        maxlength: '最多输入{0}个字符'
                    },
                    account: {
                        required: '商户账号不能为空！'
                    },
                    phone: {
                        required: '手机号不能为空！'
                    },
                    email: {
                        required: '邮箱不能为空！'
                    },
                    qq: {
                        required: '手机号不能为空！'
                    },
                    wechat: {
                        required: '微信号不能为空！'
                    }
                },
                invalidHandler: function (event, validator) {},
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
            modal.find('[data-command="submit"]').unbind('click').click(function() {
                if(form.validate().form()) {
                    doSubmit();
                }
            });
        }

        var isSending = false;
        var doSubmit = function() {
            if(isSending) return;
            var action = modal.attr('data-action');
            var params = {};
            var url = './merchant/update';
            var id = modal.attr('data-id');
            var nickname = form.find('input[name="nickname"]').val();
            var code = form.find('input[name="code"]').val();
            var account = form.find('input[name="account"]').val();
            var status = form.find('input[name="status"]:checked').val();
            var roleId = form.find('select[name="roleId"]').find("option:selected").val();
            var phone = form.find('input[name="phone"]').val();
            var email = form.find('input[name="email"]').val();
            var qq = form.find('input[name="qq"]').val();
            var wechat = form.find('input[name="wechat"]').val();
            params = {id: id, nickname: nickname, code: code, account: account, status: status,roleId:roleId,phone:phone,email:email,qq:qq,wechat:wechat};
            isSending = true;
            $.ajax({
                type : 'post',
                url : url,
                data : params,
                dataType : 'json',
                success : function(data) {
                    isSending = false;
                    if(data.error == 0) {
                        modal.modal('hide');
                        tables.init();
                        toastr['success']('修改成功！', '操作提示');
                    }
                    if(data.error == 1 || data.error == 2) {
                        toastr['error']('修改失败！' + data.message, '操作提示');
                    }
                }
            });
        };

        var show = function(action, data) {
            form[0].reset();
            modal.attr('data-action', 'edit');
            modal.find('.modal-title').html('修改商户');
            modal.attr('data-id', data.bean.id);
            form.find('input[name="nickname"]').val(data.bean.nickname);
            form.find('input[name="code"]').val(data.bean.code);
            form.find('input[name="account"]').val(data.bean.account);
            form.find('input[name="nickname"]').val(data.bean.nickname);
            form.find('input[name="status"][value="' + data.bean.status + '"]').attr('checked', true);
            form.find('select[name="roleId"]').find('option[value="' + data.bean.roleId + '"]').attr('selected', true);
            form.find('input[name="phone"]').val(data.bean.phone);
            form.find('input[name="email"]').val(data.bean.email);
            form.find('input[name="qq"]').val(data.bean.qq);
            form.find('input[name="wechat"]').val(data.bean.wechat);


            Metronic.initAjax();
            form.find('.help-inline').empty();
            form.find('.has-error').removeClass('has-error');
            form.find('.has-success').removeClass('has-success');
            modal.modal('show');
        };

        var init = function() {
            initForm();
        };

        return {
            init: init,
            show: show
        }

    }();
    var AddModal = function() {
        var modal = $('#modal-add');
        var form = $('form:last');
        // 手机号码验证
        jQuery.validator.addMethod("isPhone", function(value, element) {
            var length = value.length;
            return this.optional(element) || (length == 11 && /^[1][3,4,5,7,8][0-9]{9}$/.test(value));
        }, "请正确填写您的手机号码。");
        // 邮箱验证
        jQuery.validator.addMethod("isEmail",function (val, element) {
            return this.optional(element) || (/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$/.test(val));
        },"请填写正确的邮箱。");
        // qq验证
        jQuery.validator.addMethod("isQQ",function (val, element) {
            return this.optional(element) || (/[1-9][0-9]{4,14}/.test(val));
        },"请填写正确的QQ号码。");
        // 微信验证
        jQuery.validator.addMethod("isWechat",function (val, element) {
            return this.optional(element) || (/^[a-zA-Z\d_]{5,}$/.test(val));
        },"请填写正确的微信号。");
        // 非法字符
        jQuery.validator.addMethod("isInegal",function (val, element) {
            return this.optional(element) || (/^[0-9a-zA-Z_]{1,}$/.test(val));
        },"包含非法字符，只能使用字母、数字、下划线。");
        var initForm = function() {
            form.validate({
                rules: {
                    nickname: {
                        required: true
                    },
                    code: {
                        required: true,
                        minlength: 4,
                        maxlength: 4,
                        remote: {
                            url: '/merchant/notexists',
                            type: 'post'
                        },
                        isInegal: true
                    },
                    account: {
                        required: true,
                        remote: {
                            url: '/merchant/notexists',
                            type: 'post'
                        },
                        isInegal: true
                    },
                    pwd1: {
                        required: true,
                        minlength: 6,
                        maxlength: 20
                    },
                    pwd2: {
                        required: true,
                        minlength: 6,
                        maxlength: 20,
                        equalTo: 'input[name="pwd1"]'
                    },
                    phone: {
                        required: true,
                        isPhone: true
                    },
                    email:{
                        required: true,
                        isEmail: true
                    },
                    qq:{
                        required: true,
                        isQQ: true
                    },
                    wechat:{
                        required: true,
                        isWechat: true
                    }
                },
                messages: {
                    nickname: {
                        required: '商户别名不能为空！'
                    },
                    code: {
                        required: '商户Id不能为空！',
                        minlength: '至少输入{0}个字符',
                        maxlength: '最多输入{0}个字符',
                        remote: '商户Id已存在！'
                    },
                    account: {
                        required: '商户账号不能为空！',
                        remote: '商户账号已存在！'
                    },
                    pwd1: {
                        required: '密码不能为空！',
                        minlength: '至少输入{0}个字符',
                        maxlength: '最多输入{0}个字符'
                    },
                    pwd2: {
                        required: '确认密码不能为空！',
                        equalTo: '两次密码不一致！',
                        minlength: '至少输入{0}个字符',
                        maxlength: '最多输入{0}个字符'
                    },
                    phone: {
                        required: '手机号不能为空！'
                    },
                    email: {
                        required: '邮箱不能为空！'
                    },
                    qq: {
                        required: '手机号不能为空！'
                    },
                    wechat: {
                        required: '微信号不能为空！'
                    }
                },
                invalidHandler: function (event, validator) {},
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
            modal.find('[data-command="submit"]').unbind('click').click(function() {
                if(form.validate().form()) {
                    doSubmit();
                }
            });
        };

        var isSending = false;
        var doSubmit = function() {
            if(isSending) return;
            var action = modal.attr('data-action');
            var params = {};
            var url = './merchant/add';

            var nickname = form.find('input[name="nickname"]').val();
            var code = form.find('input[name="code"]').val();
            var account = form.find('input[name="account"]').val();
            var pwd1 = form.find('input[name="pwd1"]').val();
            var pwd2 = form.find('input[name="pwd2"]').val();
            var status = form.find('input[name="status"]:checked').val();
            var role_id = form.find('select[name="roleId"]').find("option:selected").val();
            var phone = form.find('input[name="phone"]').val();
            var email = form.find('input[name="email"]').val();
            var qq = form.find('input[name="qq"]').val();
            var wechat = form.find('input[name="wechat"]').val();
            params = { nickname: nickname, code: code, account: account,pwd1:pwd1, status: status,role_id:role_id,phone:phone,email:email,qq:qq,wechat:wechat};
            isSending = true;
            $.ajax({
                type : 'post',
                url : url,
                data : params,
                dataType : 'json',
                success : function(data) {
                    isSending = false;
                    if(data.error == 0) {
                        modal.modal('hide');
                        tables.init();
                        toastr['success']('添加完成！', '操作提示');
                    }
                    if(data.error == 1 || data.error == 2) {
                        toastr['error']('添加失败！' + data.message, '操作提示');
                    }
                }
            });
        }

        var show = function() {
            form[0].reset();
            modal.attr('data-action', 'add');
            modal.removeAttr('data-id');
            modal.find('.modal-title').html('添加商户');

            Metronic.initAjax();

            form.find('.help-inline').empty();
            form.find('.has-error').removeClass('has-error');
            form.find('.has-success').removeClass('has-success');
            modal.modal('show');
        }

        var init = function() {
            initForm();
        }

        return {
            init: init,
            show: show
        }

    }();

    return {
        init: function() {
            tables.init();
            EditModal.init();
            AddModal.init();
        }
    }
}();