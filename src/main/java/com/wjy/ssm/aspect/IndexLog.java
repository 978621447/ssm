package com.wjy.ssm.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
/**
 * @author WangJinYi 2019/2/24 0024
 */
@Component
@Aspect
public class IndexLog {

    @Pointcut("execution(public * com.wjy.ssm..*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void before() {
        System.out.println("SpringAOP切面before");
    }
}
