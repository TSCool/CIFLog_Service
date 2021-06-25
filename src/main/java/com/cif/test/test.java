package com.cif.test;

import com.cif.domain.UserBean;
import com.cif.service.PermissionsService;
import com.cif.service.RolesService;
import com.cif.service.UserService;
import com.cif.service.WorkSpaceService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Arg;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

public class test {
    @Autowired
    private static RolesService rolesService;
    @Autowired
    private static PermissionsService permissionsService;
    @Autowired
    private static UserService userService;
    @Autowired
    private static WorkSpaceService workSpaceService;
    public static void main(String[] args) {
//        byte[] tta = new byte[10];
//        tta[0] = 23;
//        tta[1] = -23;
//        tta[2] = 43;
//        tta[3] = -45;
//        tta[4] = 63;
//        tta[5] = 73;
//        JSONArray arrayValue = JSONArray.fromObject(tta);
//        System.out.println(tta.toString());
//        String[] arr = new String[2];
//        arr[0] = "name1";
//        arr[1] = "name2";
//        System.out.println(Arrays.toString(arr));
//        String aa = Arrays.toString(arr).substring(1,Arrays.toString(arr).length()-1);
//        System.out.println(aa);
//        String[] bb = aa.split(", ");
//        for (int i=0;i<bb.length;i++){
//            System.out.println("名称"+bb[i]);
//        }

//        System.out.println(rolesService.findByUserId(1));
//        System.out.println(permissionsService.findByRoleId(1));
        UserBean userBean = userService.getUser(1);
        System.out.println(workSpaceService.getAllWorkSpaces(1));
    }
}
