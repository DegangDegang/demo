package com.example.demo.global.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 메서드에 적용할 수 있도록 지정
@Retention(RetentionPolicy.RUNTIME) // 런타임에 처리되도록 설정
public @interface Notify {

}
