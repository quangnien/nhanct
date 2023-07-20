package com.example.nhanct.utils;

import com.example.nhanct.security.CustomUserDetail;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
	public static CustomUserDetail getPrincipal() {
		CustomUserDetail myUser =  (CustomUserDetail) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
        return myUser;
    }
}
