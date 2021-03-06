<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>第三方游戏管理 - 游戏列表</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${cdnDomain}theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="${cdnDomain}theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css">
<link href="${cdnDomain}theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${cdnDomain}theme/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
<link href="${cdnDomain}theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<link href="${cdnDomain}theme/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css"/>
<link href="${cdnDomain}theme/assets/global/plugins/bootstrap-datepicker/css/datepicker3.css" rel="stylesheet" type="text/css"/>
<link href="${cdnDomain}theme/assets/global/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css"/>

<link href="${cdnDomain}theme/assets/custom/plugins/jquery.easyweb/jquery.easyweb.css" rel="stylesheet" type="text/css"/>
<!-- BEGIN THEME STYLES -->
<link href="${cdnDomain}theme/assets/global/css/components.css?v=${cdnVersion}" rel="stylesheet" type="text/css"/>
<link href="${cdnDomain}theme/assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="${cdnDomain}theme/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link href="${cdnDomain}theme/assets/admin/layout/css/themes/default.css?v=${cdnVersion}" rel="stylesheet" type="text/css"/>
<link href="${cdnDomain}theme/assets/admin/layout/css/custom.css?v=${cdnVersion}" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico"/>
</head>
<body class="page-container-bg-solid">
<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<!-- BEGIN PAGE HEADER-->
			<h3 class="page-title">游戏列表</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>当前位置：第三方游戏管理<i class="fa fa-angle-right"></i></li><li>游戏列表</li>
				</ul>
			</div>
			<!-- END PAGE HEADER-->
			<div id="modal-game-add" class="modal fade" data-backdrop="static" tabindex="-1">
				<div class="modal-dialog" style="width: 680px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"></button>
							<h4 class="modal-title">添加游戏</h4>
						</div>
						<div class="modal-body" style="padding: 30px 20px 15px 20px;">
							<form action="javascript:;" class="form-horizontal">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">游戏名</label>
										<div class="col-md-9">
											<input name="gameName" class="form-control input-inline input-medium" type="text">
											<span class="help-inline" data-default="请输入游戏名。"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">游戏编码</label>
										<div class="col-md-9">
											<input name="gameCode" class="form-control input-inline input-medium" type="text">
											<span class="help-inline" data-default="第三方游戏编码。"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">游戏平台</label>
										<div class="col-md-9">
											<div class="radio-list">
												<label class="radio-inline"><input type="radio" name="platformId" value="11" checked="checked"> pt</label>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">游戏类型</label>
										<div class="col-md-9">
											<select name="typeId" class="form-control input-inline input-medium">
												<option value="1" selected>热门游戏</option>
												<option value="2">老虎机</option>
												<option value="3">奖池游戏</option>
												<option value="4">牌桌游戏</option>
												<option value="5">视频扑克</option>
												<option value="6">其它游戏</option>
											</select>
											<span class="help-inline" data-default="请选择游戏类型。"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">图片URL</label>
										<div class="col-md-9">
											<input name="imgUrl" class="form-control input-inline input-medium" type="text">
											<span class="help-inline" data-default="请填写图片URL。"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">排序号</label>
										<div class="col-md-9">
											<input name="sequence" class="form-control input-inline input-medium" type="number">
											<span class="help-inline" data-default="请输入该游戏在所选类型中的排序号。"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">前端显示</label>
										<div class="col-md-9">
											<label class="radio-inline"><input type="radio" name="display" value="1" checked="checked"> 显示</label>
											<label class="radio-inline"><input type="radio" name="display" value="0"> 不显示</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">Flash支持</label>
										<div class="col-md-9">
											<label class="radio-inline"><input type="radio" name="flashSupport" value="1" checked="checked"> 支持</label>
											<label class="radio-inline"><input type="radio" name="flashSupport" value="0"> 不支持</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">H5支持</label>
										<div class="col-md-9">
											<label class="radio-inline"><input type="radio" name="h5Support" value="1"> 支持</label>
											<label class="radio-inline"><input type="radio" name="h5Support" value="0" checked="checked"> 不支持</label>
											<span class="help-inline" data-default="手机端。"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">奖池支持</label>
										<div class="col-md-9">
											<label class="radio-inline"><input type="radio" name="progressiveSupport" value="1"> 支持</label>
											<label class="radio-inline"><input type="radio" name="progressiveSupport" value="0" checked="checked"> 不支持</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">奖池代码</label>
										<div class="col-md-9">
											<input name="progressiveCode" class="form-control input-inline input-medium" type="text">
											<span class="help-inline" data-default="PT奖池代码。"></span>
										</div>
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn default"><i class="fa fa-undo"></i> 返回列表</button>
							<button type="button" data-command="submit" class="btn green-meadow"><i class="fa fa-check"></i> 确认添加</button>
						</div>
					</div>
				</div>
			</div>
			<div id="modal-game-mod" class="modal fade" data-backdrop="static" tabindex="-1">
				<div class="modal-dialog" style="width: 680px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"></button>
							<h4 class="modal-title">修改游戏</h4>
						</div>
						<div class="modal-body" style="padding: 30px 20px 15px 20px;">
							<form action="javascript:;" class="form-horizontal">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">游戏名</label>
										<div class="col-md-9">
											<input name="id" type="hidden"/>
											<input name="gameName" class="form-control input-inline input-medium" type="text">
											<span class="help-inline" data-default="请输入游戏名。"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">游戏编码</label>
										<div class="col-md-9">
											<input name="gameCode" class="form-control input-inline input-medium" type="text">
											<span class="help-inline" data-default="第三方游戏编码。"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">游戏平台</label>
										<div class="col-md-9">
											<div class="radio-list">
												<label class="radio-inline"><input type="radio" name="platformId" value="11" checked="checked"> pt</label>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">游戏类型</label>
										<div class="col-md-9">
											<select name="typeId" class="form-control input-inline input-medium">
												<option value="1">热门游戏</option>
												<option value="2">老虎机</option>
												<option value="3">奖池游戏</option>
												<option value="4">牌桌游戏</option>
												<option value="5">视频扑克</option>
												<option value="6">其它游戏</option>
											</select>
											<span class="help-inline" data-default="请选择游戏类型。"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">图片URL</label>
										<div class="col-md-9">
											<input name="imgUrl" class="form-control input-inline input-medium" type="text">
											<span class="help-inline" data-default="请填写图片URL。"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">排序号</label>
										<div class="col-md-9">
											<input name="sequence" class="form-control input-inline input-medium" type="number">
											<span class="help-inline" data-default="请输入该游戏在所选类型中的排序号。"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">前端显示</label>
										<div class="col-md-9">
											<label class="radio-inline"><input type="radio" name="display" value="1"> 显示</label>
											<label class="radio-inline"><input type="radio" name="display" value="0"> 不显示</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">Flash支持</label>
										<div class="col-md-9">
											<label class="radio-inline"><input type="radio" name="flashSupport" value="1"> 支持</label>
											<label class="radio-inline"><input type="radio" name="flashSupport" value="0"> 不支持</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">H5支持</label>
										<div class="col-md-9">
											<label class="radio-inline"><input type="radio" name="h5Support" value="1"> 支持</label>
											<label class="radio-inline"><input type="radio" name="h5Support" value="0"> 不支持</label>
											<span class="help-inline" data-default="手机端。"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">奖池支持</label>
										<div class="col-md-9">
											<label class="radio-inline"><input type="radio" name="progressiveSupport" value="1"> 支持</label>
											<label class="radio-inline"><input type="radio" name="progressiveSupport" value="0"> 不支持</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">奖池代码</label>
										<div class="col-md-9">
											<input name="progressiveCode" class="form-control input-inline input-medium" type="text">
											<span class="help-inline" data-default="PT奖池代码。"></span>
										</div>
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn default"><i class="fa fa-undo"></i> 返回列表</button>
							<button type="button" data-command="submit" class="btn green-meadow"><i class="fa fa-check"></i> 确认添加</button>
						</div>
					</div>
				</div>
			</div>
			<!-- BEGIN PAGE CONTENT-->
			<div id="table-game-list" class="row">
				<div class="col-md-12">
					<!-- BEGIN PORTLET-->
					<div class="portlet light" style="margin-bottom: 10px;">
						<div class="portlet-body">
							<div class="table-toolbar">
								<div class="form-inline">
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<div class="input-group input-medium">
													<span class="input-group-addon no-bg fixed">游戏名</span>
													<input name="gameName" class="form-control" type="text">
												</div>
											</div>
											<div class="form-group">
												<div class="input-group input-medium">
													<span class="input-group-addon no-bg fixed">游戏编码</span>
													<input name="gameCode" class="form-control" type="text">
												</div>
											</div>
											<div class="form-group">
												<div class="input-group">
													<span class="input-group-addon no-bg fixed">游戏平台</span>
													<select name="platformId" class="form-control">
														<option value="11" selected>PT</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<div class="input-group">
													<span class="input-group-addon no-bg fixed">游戏类型</span>
													<select name="typeId" class="form-control">
														<option value="">全部游戏</option>
														<option value="1">热门游戏</option>
														<option value="2">老虎机</option>
														<option value="3">奖池游戏</option>
														<option value="4">牌桌游戏</option>
														<option value="5">视频扑克</option>
														<option value="6">其它游戏</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<a data-command="search" href="javascript:;" class="btn green-meadow"><i class="fa fa-search"></i> 搜索用户</a>
												<label><input name="advanced" type="checkbox"> 高级搜索</label>
											</div>
											<div class="form-group pull-right">
												<button data-command="add" class="btn green">
													<i class="fa fa-plus"></i> 添加游戏
												</button>
											</div>
										</div>
									</div>
									<div class="row" data-hide="advanced" style="display: none; padding-top: 3px;">
										<div class="col-md-12">
											<div class="form-group">
												<div class="input-group">
													<span class="input-group-addon no-bg fixed">前端显示</span>
													<select name="display" class="form-control">
														<option value="">全部</option>
														<option value="1">显示中</option>
														<option value="0">隐藏中</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<div class="input-group">
													<span class="input-group-addon no-bg fixed">Flash支持</span>
													<select name="flashSupport" class="form-control">
														<option value="">全部</option>
														<option value="1">支持</option>
														<option value="0">不支持</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<div class="input-group">
													<span class="input-group-addon no-bg fixed">H5支持</span>
													<select name="h5Support" class="form-control">
														<option value="">全部</option>
														<option value="1">支持</option>
														<option value="0">不支持</option>
													</select>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="table-scrollable table-scrollable-borderless">
								<table class="table table-hover table-light">
									<thead>
										<tr class="align-center">
											<th width="5%">ID</th>
											<th width="10%">游戏名</th>
											<th width="5%">游戏编码</th>
											<th width="5%">游戏平台</th>
											<th width="10%">游戏类型</th>
											<th width="10%">图片</th>
											<th width="5%">排序号</th>
											<th width="5%">前端显示</th>
											<th width="10%">Flash支持</th>
											<th width="10%">H5支持</th>
											<th class="three" width="15%">操作</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div>
							<div class="page-list"></div>
						</div>
					</div>
					<!-- END PORTLET-->
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
	</div>
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${cdnDomain}theme/assets/global/plugins/respond.min.js"></script>
<script src="${cdnDomain}theme/assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="${cdnDomain}theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="${cdnDomain}theme/assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${cdnDomain}theme/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>

<script src="${cdnDomain}theme/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js" type="text/javascript"></script>

<script src="${cdnDomain}theme/assets/global/plugins/bootstrap-daterangepicker/moment.min.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.js" type="text/javascript"></script>

<script src="${cdnDomain}theme/assets/custom/plugins/jquery.easyweb/jquery.easyweb.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${cdnDomain}theme/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/global/scripts/md5.js" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>

<script src="${cdnDomain}theme/assets/custom/scripts/global.js?v=${cdnVersion}" type="text/javascript"></script>
<script src="${cdnDomain}theme/assets/custom/scripts/game-list.js?v=${cdnVersion}" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script type="text/javascript">
jQuery(document).ready(function() {
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	// init data
	GameList.init();
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>