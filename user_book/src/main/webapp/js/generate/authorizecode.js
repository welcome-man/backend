$(function () {
    $("#jqGrid").jqGrid({
        url: '../authorizecode/list',
        datatype: "json",
        colModel: [			
			{ label: 'authId', name: 'authId', width: 50, key: true },
			{ label: '授权码', name: 'authCode', width: 80 }, 			
			{ label: '授权码状态（1：暂停；2：恢复；3：注销）', name: 'codeType', width: 80 }, 			
			{ label: '授权日期', name: 'authorizeData', width: 80 }, 			
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
		authorizeCode: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.authorizeCode = {};
		},
		update: function (event) {
			var authId = getSelectedRow();
			if(authId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(authId)
		},
		saveOrUpdate: function (event) {
			var url = vm.authorizeCode.authId == null ? "../authorizecode/save" : "../authorizecode/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.authorizeCode),
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
			var authIds = getSelectedRows();
			if(authIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../authorizecode/delete",
				    data: JSON.stringify(authIds),
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
		getInfo: function(authId){
			$.get("../authorizecode/info/"+authId, function(r){
                vm.authorizeCode = r.authorizeCode;
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