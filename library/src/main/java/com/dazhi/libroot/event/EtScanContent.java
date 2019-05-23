package com.dazhi.libroot.event;

/**
 * 功能：批量扫描识别内容RxBus回传对象
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2019/4/9 15:49
 * 修改日期：2019/4/9 15:49
 */
public class EtScanContent {
    private String strContent="";

    private EtScanContent() {}
    public EtScanContent(String strContent) {
        this.strContent = strContent;
    }

    public String getStrContent() {
        return strContent;
    }

}
