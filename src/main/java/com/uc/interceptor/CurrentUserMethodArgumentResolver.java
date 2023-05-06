package com.uc.interceptor;

import com.uc.annotation.CurrentUser;
import com.uc.entity.CurrentUserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

public class CurrentUserMethodArgumentResolver  implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(CurrentUserInfo.class)
                && methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        // 获取拦截器中获取的当前用户信息
        CurrentUserInfo currentUserInfo = (CurrentUserInfo)nativeWebRequest.getAttribute("currentUserInfo", RequestAttributes.SCOPE_REQUEST);
        if (currentUserInfo != null){
            return currentUserInfo;
        }
        // 如果当前用户信息为null  则抛出异常
        throw new MissingServletRequestPartException("currentUserInfo");
    }
}
