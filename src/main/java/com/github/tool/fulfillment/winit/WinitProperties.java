package com.github.tool.fulfillment.winit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WinitProperties {

    /**
     * 登录万邑联的账号
     */
    private String appKey = "rebecca";

    /**
     * 应用ID client_id，获取方法请参照<a href="https://developer.winit.com.cn/document/detail/id/127.html">《开发者控制台 操作手册》</a>
     */
    private String clientId = "ODJKMDU1YZCTYJQ5YY00ZWZLLTK5N2QTOWY4MZI5OGMWNDG2";

    /**
     * 账号信息，获取方法参考<a href="https://developer.winit.com.cn/document/detail/id/1.html">新手接入指南</a>
     */
    private String token = "xxx";

    /**
     * 应用密钥，获取方法参照<a href="https://developer.winit.com.cn/document/detail/id/127.html">《开发者控制台 操作手册》</a>
     */
    private String clientSecret = "xxx";

    /**
     * 应用Code，获取方法请参照<a href="https://developer.winit.com.cn/document/detail/id/127.html">《开发者控制台 操作手册》</a>
     */
    private String platform = "OWNERERP";

    /**
     * 接口版本
     */
    private String version = "1.0";

    /**
     * 格式
     */
    private String format = "json";

    /**
     * 签名方式
     */
    private String signMethod = "md5";

    /**
     * 语言
     */
    private String language = "zh_CN";

    /**
     * 是否沙箱环境
     */
    private boolean sandBox = true;

}
