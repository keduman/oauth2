package com.example.oauth2.util;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;

public class CaptchaUtil {
    private CaptchaUtil() { throw new IllegalStateException("Utility Class");   }

    public static Captcha createCaptcha( Integer width, Integer height ){
        return new Captcha.Builder(width, height).addBackground(new GradiatedBackgroundProducer()).addText(new DefaultTextProducer(), new DefaultWordRenderer()).addNoise(new CurvedLineNoiseProducer()).build();
    }

    public static String encodeCaptcha( Captcha captcha ){
        String image = null;
        return null;
    }
}
