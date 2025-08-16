/*
* package：声明这个类属于哪个包（相当于文件夹路径）
*/
package com.bjh.aisuperagent.demo.invoke;// 建议dashscope SDK的版本 >= 2.12.0

import java.util.Arrays;
import java.lang.System;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


// public：访问修饰符，表示这个类可以被其他包访问
public class SdkAiInvoke {

    /*
    public：访问修饰符，表示方法可以被外部调用
    static：静态方法，不需要创建对象就能调用
    GenerationResult：返回类型，这个方法会返回GenerationResult对象
    callWithMessage()：方法名和参数列表（这里没有参数）
    throws：声明这个方法可能抛出的异常类型
    **/
    public static GenerationResult callWithMessage() throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("You are my exclusive AI, you possess human emotions, and we are close friends")
                .build();
        Message userMsg = Message.builder()
                /*
                每个方法返回builder对象本身，所以可以连续调用
                最后用.build()创建最终对象
                * */
                .role(Role.USER.getValue())
                .content("你好，今天怎么样")
                .build();
        GenerationParam param = GenerationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                .apiKey(TestApiKey.API_KEY)
                // 此处以qwen-plus为例，可按需更换模型名称。模型列表：https://help.aliyun.com/zh/model-studio/getting-started/models
                .model("qwen-plus")
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        return gen.call(param);
    }

    // 程序入口点
    /*
    main方法：Java程序的入口点
    String[] args：命令行参数数组
    程序从这里开始执行
    **/
    public static void printBeautifulJson() {
        try {
            GenerationResult result = callWithMessage();

            // 创建格式化的Gson对象
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()  // 启用美化打印
                    .create();

            // 将结果转换为JSON字符串并美化输出
            String jsonString = JsonUtils.toJson(result);
            Object jsonObject = gson.fromJson(jsonString, Object.class);
            String prettyJson = gson.toJson(jsonObject);

            System.out.println(prettyJson);

        } catch (Exception e) {
            System.err.println("调用失败: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
         printBeautifulJson();
    }
}














