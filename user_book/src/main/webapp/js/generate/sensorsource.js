$(function () {
    $("#jqGrid").jqGrid({
        url: '../sensorsource/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 50, key: true },
			{ label: '传感器信息ID', name: 'sensorInfoId', width: 80 }, 			
			{ label: '检定证书号', name: 'standardNum', width: 80 }, 			
			{ label: '溯源单位', name: 'sourceUnit', width: 80 }, 			
			{ label: '溯源日期', name: 'sourceDate', width: 80 }, 			
			{ label: '有效期至', name: 'deadDate', width: 80 }, 			
			{ label: '不确定度', name: 'uncertainty', width: 80 }, 			
			{ label: '测量范围', name: 'measuringRange', width: 80 }, 			
			{ label: '标定数据表ID', name: 'signId', width: 80 }, 			
			{ label: '检定数据表ID', name: 'checkId', width: 80 }, 			
			{ label: '创建者', name: 'createBy', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', width: 80 }, 			
			{ label: '更新者', name: 'updateBy', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', width: 80 }, 			
			{ label: '是否删除（0：未删除；1：删除）', name: 'isdelete', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"currentPage", 
            rows:"pageSize", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sensorSource: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sensorSource = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.sensorSource.id == null ? "../sensorsource/save" : "../sensorsource/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.sensorSource),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sensorsource/delete",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get("../sensorsource/info/"+id, function(r){
                vm.sensorSource = r.sensorSource;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});