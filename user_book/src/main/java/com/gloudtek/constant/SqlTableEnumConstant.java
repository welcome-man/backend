package com.gloudtek.constant;

import java.util.HashMap;
import java.util.Map;

public class SqlTableEnumConstant {
	
	public static Map<Integer,String> SQL_TABLE_MAP = new HashMap<Integer,String>(){
		private static final long serialVersionUID = 1L;
		{
			put(1,"app_version");
			put(2,"authorize_code");
			put(3,"check_data");
			put(4,"check_unit");
			put(5,"d_check_data");
			put(6,"d_check_info");
			put(7,"equip_info");
			put(8,"sensor_info");
			put(9,"sensor_source");
			put(10,"sign_data");
			put(11,"sys_config");
			put(12,"sys_menu");
			put(13,"sys_role");
			put(14,"sys_role_menu");
			put(15,"sys_user");
			put(16,"sys_user_role");
			put(17,"unit");
			put(18,"unit_equip");
			put(19,"user_code");
			put(20,"work_order");
		}
	};
	
	public static Map<Integer,String> SQL_EXE_MAP = new HashMap<Integer,String>(){
		private static final long serialVersionUID = 1L;
		{
			put(1,"insert");
			put(2,"update");
			put(3,"delete");
			put(4,"select");
		}
	};

}
