package com.statkevich.receipttask.aspect;

import com.statkevich.receipttask.annotation.CacheEvict;
import com.statkevich.receipttask.annotation.CacheKey;
import com.statkevich.receipttask.annotation.CachePut;
import com.statkevich.receipttask.annotation.Cacheable;
import com.statkevich.receipttask.cache.Cache;
import com.statkevich.receipttask.cache.CacheHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Described class serves to intercept method calls
 * and include caching into annotated methods.
 * Using {@link com.statkevich.receipttask.cache.Cache} class for caching.
 */
@Aspect
public class CacheAspect {

    @Around(value = "@annotation(com.statkevich.receipttask.annotation.Cacheable)")
    public Object get(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Cacheable annotation = signature.getMethod().getAnnotation(Cacheable.class);
        String name = annotation.name();
        Cache cache = CacheHolder.get(name);
        Object cacheKey = pjp.getArgs()[0];

        try {
            Object returnedObject = cache.get(cacheKey);
            if (null == returnedObject) {
                Object returnedFromGetMethod = pjp.proceed();
                cache.put(cacheKey, returnedFromGetMethod);
                return returnedFromGetMethod;
            }
            return returnedObject;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @AfterReturning(pointcut = "@annotation(com.statkevich.receipttask.annotation.CachePut)", returning = "returnedObject")
    public void update(JoinPoint joinPoint, Object returnedObject) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CachePut annotation = signature.getMethod().getAnnotation(CachePut.class);
        String name = annotation.name();
        Cache cache = CacheHolder.get(name);
        Object cacheKey = getCacheKey(returnedObject);
        cache.put(cacheKey, returnedObject);
    }

    @After(value = "@annotation(com.statkevich.receipttask.annotation.CacheEvict)")
    public void evict(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CacheEvict annotation = signature.getMethod().getAnnotation(CacheEvict.class);
        String name = annotation.name();
        Cache cache = CacheHolder.get(name);
        Object cacheKey = joinPoint.getArgs()[0];
        cache.evict(cacheKey);
    }


    private Object getCacheKey(Object obj) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        return Arrays.stream(declaredFields)
                .filter(x -> x.getDeclaredAnnotation(CacheKey.class) != null)
                .map(x -> {
                    try {
                        x.setAccessible(true);
                        return x.get(obj);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .findAny()
                .orElseThrow();
    }
}
