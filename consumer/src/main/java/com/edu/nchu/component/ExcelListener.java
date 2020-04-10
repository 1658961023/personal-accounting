package com.edu.nchu.component;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： ExcelListener
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.component 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/9 上午 11:29
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public class ExcelListener extends AnalysisEventListener {

    /**
     * 自定义用于暂时存储data
     * 可以通过实例获取该值
     */
    private List<List<String>> datas = new ArrayList<>();

    /**
     * 每解析一行都会回调invoke()方法
     *
     * @param object  读取后的数据对象
     * @param context 内容
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        @SuppressWarnings("unchecked") Map<String, String> stringMap = (HashMap<String, String>) object;
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        datas.add(new ArrayList<>(stringMap.values()));
        //根据自己业务做处理
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //解析结束销毁不用的资源
        //注意不要调用datas.clear(),否则getDatas为null
    }

    /**
     * 返回数据
     *
     * @return 返回读取的数据集合
     **/
    public List<List<String>> getDatas() {
        return datas;
    }

    /**
     * 设置读取的数据集合
     *
     * @param datas 设置读取的数据集合
     **/
    public void setDatas(List<List<String>> datas) {
        this.datas = datas;
    }
}
