package com.edu.nchu.service.user;

import com.edu.nchu.entity.VirtualAcct;

import java.util.List;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： VirtualAcctService
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.user 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/5/10 下午 08:00
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public interface VirtualAcctService {

    List<VirtualAcct> getAccts(String acct);
}

