package com.edu.nchu.service.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.edu.nchu.entity.VirtualAcct;
import com.edu.nchu.entity.VirtualAcctExample;
import com.edu.nchu.mapper.VirtualAcctMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： VirtualAcctServiceImpl
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.user 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/5/10 下午 08:29
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Service
public class VirtualAcctServiceImpl implements VirtualAcctService {

    @Autowired
    private VirtualAcctMapper virtualAcctMapper;

    @Override
    public List<VirtualAcct> getAccts(String acct) {
        VirtualAcctExample virtualAcctExample = new VirtualAcctExample();
        VirtualAcctExample.Criteria criteria = virtualAcctExample.createCriteria();
        criteria.andAcctEqualTo(acct);
        return virtualAcctMapper.selectByExample(virtualAcctExample);
    }
}
