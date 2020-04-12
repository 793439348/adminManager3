var list = function() {

    var tables = function () {
        var tableList = $('#table-list');
        var tablePagelist = tableList.find('.page-list');
        var getSearchParams = function() {
            var name = tableList.find('input[name="brand-name"]').val();
            var type = tableList.find('select[name="brand-type"]').find("option:selected").val();
            return {name: name, type:type};
        };
        var pagination = $.pagination({
            render: tablePagelist,
            pageSize: 10,
            ajaxType: 'post',
            ajaxUrl: './site-template/search',
            ajaxData: getSearchParams,
            beforeSend: function() {
            },
            complete: function() {
            },
            success: function(list) {
                var table = tableList.find('table > tbody').empty();
                var innerHtml = '';
                $.each(list, function(idx, val) {
                    var typeName = '';
                    if (val.type == 1) {
                        typeName = '手机端';
                    }else if (val.type == 2)
                        typeName = 'PC端';
                    innerHtml +=
                        '<tr class="align-center" data-id="' + val.id + '">' +
                        '<td>' + val.id + '</td>' +
                        '<td>' + val.code + '</td>' +
                        '<td>' + val.name + '</td>' +
                        '<td>' + typeName + '</td>' +
                        '<td>' + '<img src="'+val.smallImage+"?"+Math.random() +'" alt="缩略图" width="40" height="40">' + '</td>' +
                        '<td>' + '<img src="'+ val.bigImage+"?"+Math.random() +'" alt="预览图" width="40" height="40">' + '</td>' +
                        '<td>' +
                        '<a data-command="edit" href="javascript:;" class="btn default btn-xs black"><i class="fa fa-edit"></i> 修改 </a>' +
                        '<a data-command="dele" href="javascript:;" class="btn default btn-xs black"><i class="fa fa-edit"></i> 删除 </a>' +
                        '</td>' +
                        '</tr>';
                });
                table.html(innerHtml);
                table.find('[data-command="edit"]').unbind('click').click(function() {
                    var id = $(this).parents('tr').attr('data-id');
                    doLoad(id, 'edit');
                });
                table.find('[data-command="dele"]').unbind('click').click(function() {
                    var id = $(this).parents('tr').attr('data-id');
                    deleTemplate(id);
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

        var deleTemplate = function (id) {
            $.ajax({
                type : 'post',
                url : '/site-template/delete',
                data : "id="+id,
                dataType : 'json',
                success : function(data) {
                    if(data.error == 0) {
                        tables.init();
                        toastr['success']('删除完成！', '操作提示');
                    }
                    if(data.error == 1 || data.error == 2) {
                        toastr['error']('删除失败！' + data.message, '操作提示');
                    }
                }
            });
        }
        var doLoad = function(id, action) {
            var url = './site-template/get';
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
                    code: {
                        required: true
                    },
                    name: {
                        required: true
                    }
                },
                messages: {
                    code: {
                        required: "模板编码不能为空"
                    },
                    name: {
                        required: "模板名称不能为空"
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
            var url = './site-template/update';
            var formData = new FormData();
            var id = modal.attr('data-id');
            var name = form.find('input[name="name"]').val();
            var code = form.find('input[name="code"]').val();
            var type = form.find('input[name="type"]:checked').val();
            var smallImage = form.find('input[name="smallImage"]').prop("files")[0];
            var bigImage = form.find('input[name="bigImage"]').prop("files")[0];
            formData.append("id",id);
            formData.append("code",code);
            formData.append("name",name);
            formData.append("type",type);
            formData.append("smallImage", smallImage);
            formData.append("bigImage", bigImage);
            isSending = true;
            $.ajax({
                type : 'post',
                url : url,
                data : formData,
                processData:false,
                contentType: false,
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
            modal.find('.modal-title').html('修改模板');
            modal.attr('data-id', data.bean.id);

            form.find('input[name="code"]').val(data.bean.code);
            form.find('input[name="name"]').val(data.bean.name);
            form.find('input[name="type"][value="' + data.bean.type + '"]').attr('checked', true);

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

        var initForm = function() {
            form.validate({
                rules: {
                    code: {
                        required: true,
                        remote: {
                            url: '/site-template/notexistCode',
                            type: 'post'
                        }
                    },
                    name: {
                        required: true
                    },
                    smallImage: {required: true},
                    bigImage: {required: true}
                },
                messages: {
                    code: {
                        required: "模板编码不能为空",
                        remote: '商户Id已存在！'
                    },
                    name: {
                        required: "模板名称不能为空"
                    },
                    smallImage: {required: "缩略图不能为空"},
                    bigImage: {required: "预览图不能为空"}
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
            var url = './site-template/add';
            var name = form.find('input[name="name"]').val();
            var code = form.find('input[name="code"]').val();
            var type = form.find('input[name="type"]:checked').val();
            var smallImage = form.find('input[name="smallImage"]').prop("files")[0];
            var bigImage = form.find('input[name="bigImage"]').prop("files")[0];
            var formData = new FormData();
            formData.append("code",code);
            formData.append("name",name);
            formData.append("type",type);
            formData.append("smallImage", smallImage);
            formData.append("bigImage", bigImage);
            isSending = true;
            $.ajax({
                type : 'post',
                url : url,
                data : formData,
                processData:false,
                contentType: false,
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
            modal.find('.modal-title').html('添加模板');

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