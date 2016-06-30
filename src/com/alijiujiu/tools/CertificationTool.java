package com.alijiujiu.tools;

public class CertificationTool {
	public static String verifyInformation(int code) {
		String msg = null;

		switch (code) {
		case 0:
			msg = "等待审核";
			break;
		case 66:
			msg = "审核通过，认证完成";
			break;
		case 10:
			msg = "审核未通过，营业执照信息不符";
			break;
		case 11:
			msg = "审核未通过，身份信息不符";
			break;
		case 12:
			msg = "审核未通过，餐厅电话未接通";
			break;
		case 13:
			msg = "审核未通过，餐厅门脸照片无法识别";
			break;
		default:
			msg = "等待审核";
		}
		return msg;
	}
}
