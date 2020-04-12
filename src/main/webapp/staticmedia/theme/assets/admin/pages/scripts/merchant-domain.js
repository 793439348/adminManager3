var list = function () {

    var tables = function () {
        var tableList = $('#table-list');
        var tablePagelist = tableList.find('.page-list');
        var getSearchParams = function () {
            var domain = $("#domain").val();
            var brand = $("#brand").find("option:selected").val();
            return {domain: domain, brand: brand};
        };
        var pagination = $.pagination({
            render: tablePagelist,
            pageSize: 10,
            ajaxType: 'post',
            ajaxUrl: './merchant-brand-domain/search',
            ajaxData: getSearchParams,
            beforeSend: function () {
            },
            complete: function () {
            },
            success: function (list) {
                var table = tableList.find('table > tbody').empty();
                var innerHtml = '';
                $.each(list, function (idx, val) {
                    innerHtml +=
                        '<tr class="align-center" data-id="' + val.id + '">' +
                        '<td>' + val.brandCode + '</td>' +
                        '<td>' + val.merchantCode + '</td>' +
                        '<td>' + val.domain + '</td>' +
                        '<td>' +
                        '<a data-command="edit" href="javascript:;" class="btn default btn-xs black"><i class="fa fa-edit"></i> 修改 </a>' +
                        '<a data-command="dele" href="javascript:;" class="btn default btn-xs black"><i class="fa fa-edit"></i> 删除 </a>' +
                        '</td>' +
                        '</tr>';

                });
                table.html(innerHtml);
                table.find('[data-command="edit"]').unbind('click').click(function () {
                    var id = $(this).parents('tr').attr('data-id');
                    doLoad(id, 'edit');
                });
                table.find('[data-command="dele"]').unbind('click').click(function () {
                    var id = $(this).parents('tr').attr('data-id');
                    deleDomain(id);
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

        var deleDomain = function (id) {
            $.ajax({
                type: 'post',
                url: '/merchant-brand-domain/delete',
                data: "id=" + id,
                dataType: 'json',
                success: function (data) {
                    if (data.error == 0) {
                        tables.init();
                        toastr['success']('删除完成！', '操作提示');
                    }
                    if (data.error == 1 || data.error == 2) {
                        toastr['error']('删除失败！' + data.message, '操作提示');
                    }
                }
            });
        }
        var doLoad = function (id, action) {
            var url = './merchant-brand-domain/get';
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
                    domain: {
                        required: true
                    }
                },
                messages: {
                    domain: {
                        required: "模板编码不能为空"
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
            var url = './merchant-brand-domain/update';
            var id = modal.attr('data-id');
            var domain = form.find('input[name="domain"]').val();
            var brand = form.find('select[name="brand"]').find("option:selected").val();
            var params = {id: id, domain: domain, brandId: brand};
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
        var brandList = function (code) {
            var selt = form.find('select[name="brand"]');
            selt.empty();
            $.ajax({
                type: 'post',
                url: '/merchant-brand/getlist',
                data: '',
                dataType: 'json',
                success: function (list) {
                    $.each(list, function (idx, val) {
                        if (code == val.code)
                            selt.append('<option value="' + val.id + '" selected>' + val.code + '</option>');
                        else
                            selt.append('<option value="' + val.id + '">' + val.code + '</option>');
                    })
                }
            })
        }
        var show = function (action, data) {
            form[0].reset();
            modal.attr('data-action', 'edit');
            modal.find('.modal-title').html('修改模板');
            modal.attr('data-id', data.bean.id);

            /*品牌加载*/
            form.find('input[name="domain"]').val(data.bean.domain);
            brandList(data.bean.brandCode);

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
                    domain: {
                        required: true
                    }
                },
                messages: {
                    domain: {
                        required: "模板编码不能为空"
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
            var domain = form.find('input[name="domain"]').val();
            var brand = form.find('select[name="brand"]').find("option:selected").val();
            var params = {domain: domain, brandId: brand};
            isSending = true;
            $.ajax({
                type: 'post',
                url: './merchant-brand-domain/add',
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

        var brandList = function () {
            var searchselt = $('#brand');
            var selt = form.find('select[name="brand"]');
            selt.empty();
            $.ajax({
                type: 'post',
                url: '/merchant-brand/getlist',
                data: '',
                dataType: 'json',
                success: function (list) {
                    searchselt.append('<option value="' + 0 + '" selected>' + "全部" + '</option>')
                    $.each(list, function (idx, val) {
                        if (idx == 0) {
                            selt.append('<option value="' + val.id + '" selected>' + val.code + '</option>');
                            searchselt.append('<option value="' + val.id + '" >' + val.code + '</option>');
                        } else {
                            selt.append('<option value="' + val.id + '">' + val.code + '</option>');
                            searchselt.append('<option value="' + val.id + '">' + val.code + '</option>');
                        }
                    })
                }
            })
        }
        var show = function () {
            form[0].reset();
            modal.attr('data-action', 'add');
            modal.removeAttr('data-id');
            modal.find('.modal-title').html('添加模板');

            Metronic.initAjax();
            /*加载品牌*/
            brandList();
            form.find('.help-inline').empty();
            form.find('.has-error').removeClass('has-error');
            form.find('.has-success').removeClass('has-success');
            modal.modal('show');
        }

        var init = function () {
            initForm();
            brandList();
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