package com.example.repair;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class RepairUtils {
    public static void insertCode() {

    }

    public static void getCode(String url, String methodName) {

    }

    public static synchronized String run(String[] cmd, String workdirectory) throws IOException {
        StringBuffer result = new StringBuffer();
        try {
            //创建操作系统进程（也可以由Runtime.exec() 启动）
            //Runtime runtime =Runtime.getRuntime();
            // Process proc = runtime.exec(cmd);
            //InputStream inputstream = proc.getInputStream();
            ProcessBuilder builder = new ProcessBuilder(cmd);
            InputStream in = null;
            //设置一个路径（绝对路径了就不一定需要）
            if (workdirectory != null) { // 设置工作目录（同上）
                builder.directory(new File(workdirectory)); // 合并标准错误和标准输出
                builder.redirectErrorStream(true); // 启动一个新进程
                Process process =builder.start(); // 读取进程标准输出流
                in = process.getInputStream();
                byte[] re = new byte[1024];
                while (in.read(re) != -1) {
                    result = result.append(new String(re));
                }
            } // 关闭输入流 if (in != null) {
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result.toString();
    }


}
