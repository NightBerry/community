package com.nowcoder.community;

import java.io.IOException;

/**
 * @author HAN
 * @date 2022/5/26 10:37
 */
public class WKTests {

    public static void main(String[] args) {
        String cmd = "D:/ZNowCoderProject/wkhtmltopdf/bin/wkhtmltoimage --quality 75  https://www.nowcoder.com D:/ZNowCoderProject/data/wk-images/3.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
