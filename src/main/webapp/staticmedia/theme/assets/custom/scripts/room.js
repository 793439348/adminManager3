var Room = function() {
	
	var isLotteryLoading = false;
	var loadLottery = function() {
		if(isLotteryLoading) return;
		var url = './lottery/list';
		isLotteryLoading = true;
		$.ajax({
			type : 'post',
			url : url,
			data : {},
			dataType : 'json',
			success : function(data) {
				isLotteryLoading = false;
				buildLottery(data);
			}
		});
	}
	
	var buildLottery = function(data) {
		var tableList = $('#table-room-list');
		var lottery = tableList.find('select[name="lottery"]');
		var modal = $('#modal-room-edit');
		var form = modal.find('form');
		var reLottery = form.find('select[name="reLottery"]');
		$.each(data, function(idx, val) {
			if (val.bean.status != -1) {
				lottery.append('<option value="' + val.bean.id + '">' + val.bean.showName + '</option>');
				reLottery.append('<option value="' + val.bean.id + '">' + val.bean.showName + '</option>');
			}
		});
		lottery.change(function() {
			loadData();
		});
		handleSelect();
		var roomType = tableList.find('select[name="roomType"]');
		roomType.change(function() {
			loadData();
		});
	}
	
	loadLottery();
	
	var RoomTable = function() {
		var tableList = $('#table-room-list');
		
		var isLoading = false;
		var loadData = function() {
			if(isLoading) return;
			var lottery = tableList.find('select[name="lottery"]').val();
			var roomType = tableList.find('select[name="roomType"]').val();
			var url = './room/list';
			var params = {lottery: lottery, type: roomType};
			isLoading = true;
			$.ajax({
				type : 'post',
				url : url,
				data : params,
				dataType : 'json',
				success : function(data) {
					isLoading = false;
					buildData(data);
				}
			});
		}
		
		var buildData = function(data) {
			var table = tableList.find('table > tbody').empty();
			var innerHtml = '';
			$.each(data, function(idx, val) {
				var statusAction = '<a data-command="status" data-status="' + val.state + '" href="javascript:;" class="btn default btn-xs black"><i class="fa fa-ban"></i> 禁用 </a>';
				if(val.state == -1) {
					statusAction = '<a data-command="status" data-status="' + val.state + '" href="javascript:;" class="btn default btn-xs black"><i class="fa fa-check"></i> 启用 </a>';
				}
				statusAction += '<a class="btn default btn-xs black">编辑</a>';
				innerHtml +=
				'<tr class="align-center" data-id="' + val.id + '">'+
					'<td>' + val.id + '</td>'+
					'<td>' + val.lottery + '</td>'+
					'<td>' + val.name + '</td>'+
					'<td>' + val.type + '</td>'+
					'<td>' + val.maxPlayer + '</td>'+
					'<td>' + val.minBalance + '</td>'+
					'<td>' + val.minBet + '</td>'+
					'<td>' + DataFormat.formatLotteryStatus(val.state) + '</td>'+
					'<td>' + statusAction + '</td>'+
				'</tr>';
			});
			table.html(innerHtml);
			table.find('[data-command="status"]').unbind('click').click(function() {
				var id = $(this).parents('tr').attr('data-id');
				var status = $(this).attr('data-status');
				var msg = '确定禁用该彩种？(禁用后用户不能进行投注操作，已投注的不影响。)';
				if(status == -1) {
					msg = '确定启用该彩种？';
				}
				bootbox.dialog({
					message: msg,
					title: '提示消息',
					buttons: {
						success: {
							label: '<i class="fa fa-check"></i> 确定',
							className: 'green-meadow',
							callback: function() {
								if(status == 0) {
									updateStatus(id, -1);
								}
								if(status == -1) {
									updateStatus(id, 0);
								}
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
		}
		
		var updateStatus = function(id, status) {
			var params = {id: id, status: status};
			var url = './lottery/update-status';
			$.ajax({
				type : 'post',
				url : url,
				data : params,
				dataType : 'json',
				success : function(data) {
					if(data.error == 0) {
						loadData();
						if(status == 0) {
							toastr['success']('该彩种已恢复正常状态！', '操作提示');
						}
						if(status == -1) {
							toastr['success']('该彩种已禁用！', '操作提示');
						}
					}
					if(data.error == 1 || data.error == 2) {
						toastr['error']('操作失败！' + data.message, '操作提示');
					}
				}
			});
		}
		
		tableList.find('[data-command="add"]').unbind('click').click(function() {
			EditRoomModal.show('add');
		});

		var init = function() {
			loadData();
		}
		
		return {
			init: init
		}
	}();
	
	var handleSelect = function() {
		$('.bs-select').selectpicker({
			iconBase: 'fa',
			tickIcon: 'fa-check'
		});
		$('.bs-select').selectpicker('refresh');
	}
	
	var EditRoomModal = function() {
		var modal = $('#modal-room-edit');
		var form = modal.find('form');
		var initForm = function() {
			form.validate({
				rules: {
					reLottery: {
						required: true
					},
					reType: {
						required: true
					},
					reName: {
						required: true
					}
				},
				messages: {
					reLottery: {
						required: '彩票不能为空！'
					},
					reType: {
	                    required: '类型不能为空！'
	                },
	                reName: {
	                    required: '名称不能为空！'
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
			var url = '';
			if(action == 'edit') {
				url = './room/edit';
				var id = modal.attr('data-id');
				var lottery = form.find('select[name="reLottery"]').val();
				var type = form.find('select[name="reType"]').val();
				var name = form.find('input[name="reName"]').val();
				var maxPlayer = form.find('input[name="reMaxPlayer"]').val();
				var minBalance = form.find('input[name="reMinBalance"]').val();
				var minBet = form.find('input[name="reMinBet"]').val();
				var desc = form.find('input[name="reDesc"]').val();
				var state = form.find('input[name="reState"]').val();
				params = {id: id, lottery: lottery, type: type, name: name, maxPlayer: maxPlayer, minBalance: minBalance, minBet: minBet, desc: desc, state: state};
			}
			if(action == 'add') {
				url = './room/add';
				var lottery = form.find('select[name="reLottery"]').val();
				var type = form.find('select[name="reType"]').val();
				var name = form.find('input[name="reName"]').val();
				var maxPlayer = form.find('input[name="reMaxPlayer"]').val();
				var minBalance = form.find('input[name="reMinBalance"]').val();
				var minBet = form.find('input[name="reMinBet"]').val();
				var desc = form.find('input[name="reDesc"]').val();
				params = {lottery: lottery, type: type, name: name, maxPlayer: maxPlayer, minBalance: minBalance, minBet: minBet, desc: desc};
			}
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
						UserCardTable.init();
						if(action == 'edit') {
							toastr['success']('房间修改成功！', '操作提示');
						}
						if(action == 'add') {
							toastr['success']('房间添加完成！', '操作提示');
						}
					}
					if(data.error == 1 || data.error == 2) {
						if(action == 'edit') {
							toastr['error']('房间修改失败！' + data.message, '操作提示');
						}
						if(action == 'add') {
							toastr['error']('房间添加失败！' + data.message, '操作提示');
						}
					}
				}
			});
		}

		var show = function(action, data) {
			form[0].reset();
			if('edit' == action) {
				modal.attr('data-action', 'edit');
				modal.attr('data-id', data.bean.id);
				modal.find('.modal-title').html('编辑房间');
				form.find('select[name="reLottery"]').attr('disabled', true);
				form.find('select[name="reLottery"]').find('option[text="' + data.lottery + '"]').attr('selected', true);
				form.find('select[name="reType"]').find('option[text="' + data.type + '"]').attr('selected', true);
				form.find('input[name="reName"]').val(data.name).removeAttr('disabled');
				form.find('input[name="reMaxPlayer"]').val(data.maxPlayer).removeAttr('disabled');
				form.find('input[name="reMinBalance"]').val(data.minBalance).removeAttr('disabled');
				form.find('input[name="reMinBet"]').val(data.minBet).removeAttr('disabled');
				form.find('input[name="reDesc"]').val(data.desc).removeAttr('disabled');
				form.find('input[name="reState"]').attr('checked', data.state == 0);
				Metronic.initAjax();
			}
			if('add' == action) {
				modal.attr('data-action', 'add');
				modal.removeAttr('data-id');
				modal.find('.modal-title').html('添加房间');
				form.find('select[name="reLottery"]').removeAttr('disabled').find('option').eq(0).attr('selected', true);
				form.find('select[name="reType"]').removeAttr('disabled').find('option').eq(0).attr('selected', true);
				form.find('input[name="reName"]').removeAttr('disabled');
				form.find('input[name="reMaxPlayer"]').removeAttr('disabled');
				form.find('input[name="reMinBalance"]').removeAttr('disabled');
				form.find('input[name="reMinBet"]').removeAttr('disabled');
				form.find('input[name="reDesc"]').removeAttr('disabled');
				form.find('input[name="reState"]').attr('checked', "checked");
				Metronic.initAjax();
			}
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
			RoomTable.init();
			EditRoomModal.init();
		}
	}
}();