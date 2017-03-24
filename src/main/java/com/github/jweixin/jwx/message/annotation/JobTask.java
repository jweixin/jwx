package com.github.jweixin.jwx.message.annotation;

/**
 * 任务通知
 * @author Administrator
 *
 */
public enum JobTask {
	MASS_SEND_JOB_FINISH("MASSSENDJOBFINISH"),
	TEMPLATE_SEND_JOB_FINISH("TEMPLATESENDJOBFINISH");
	
	private String type;
	
	private JobTask(String type){
		this.type = type;
	}
	
	public String type(){
		return this.type;
	}

}
