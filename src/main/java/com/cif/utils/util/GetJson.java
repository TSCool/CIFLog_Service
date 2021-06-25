package com.cif.utils.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetJson {

    /**
     * 创建日期:2021年02月02日<br/>
     * 代码创建:lts<br/>
     * 功能描述:通过request来获取到json数据<br/>
     * @param request
     * @return
     */
    public static JSONObject getJSONParam(HttpServletRequest request){
        JSONObject jsonParam = null;
        try {
            // 获取输入流
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

            // 写入数据到Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = JSONObject.parseObject(sb.toString());
            // 直接将json信息打印出来
            System.out.println(jsonParam.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParam;
    }

}
