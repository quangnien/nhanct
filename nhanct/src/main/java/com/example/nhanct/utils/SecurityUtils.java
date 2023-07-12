package com.example.nhanct.utils;

import com.example.nhanct.security.CustomUserDetail;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
	//gọi hàm để lấy đc username trong MyUser để xử lí phần header tên user 
	public static CustomUserDetail getPrincipal() {
		//khi we có dl r và để we duy trì đc cái data dl đó trong spring mvc thì we use đối tượng là Principal 
		CustomUserDetail myUser =  (CustomUserDetail) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
        return myUser;
    }
}
