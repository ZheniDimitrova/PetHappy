package com.example.pethappy.filters;

import com.example.pethappy.model.dto.MessageExportDto;
import com.example.pethappy.service.BannedOwnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


public class BannedOwnerInterceptor implements HandlerInterceptor {

    private final BannedOwnerService bannedOwnerService;

    public BannedOwnerInterceptor(BannedOwnerService bannedOwnerService) {
        this.bannedOwnerService = bannedOwnerService;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object handler, ModelAndView modelAndView) {

        Principal principal = httpServletRequest.getUserPrincipal();

       if(principal != null && bannedOwnerService.bannedOwnerExists(principal.getName())){

           modelAndView.setViewName("bannedOwner");
       };

    }
}
