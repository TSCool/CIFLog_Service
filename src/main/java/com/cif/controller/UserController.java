package com.cif.controller;

import com.alibaba.fastjson.JSONObject;
import com.cif.domain.PermissionsBean;
import com.cif.domain.RolesBean;
import com.cif.domain.UserBean;
import com.cif.service.PermissionsService;
import com.cif.service.RolesService;
import com.cif.service.UserService;
import com.cif.utils.constant.FilePath;
import com.cif.utils.token.TokenUtil;
import com.cif.utils.util.GetJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@Api(value = "用户接口模块",tags = "用户接口模块")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RolesService rolesService;
    @Autowired
    PermissionsService permissionsService;

    /**
     * 根据名称获取用户信息
     * @param name
     * @return
     */
    @ApiOperation(value = "根据名称获取用户信息")
    @RequestMapping(value = "/user/name",method = RequestMethod.GET)
    public ResponseEntity getUserByName(String name){
        Map<String,Object> data = new HashMap<>();
        UserBean user = userService.getUserByName(name);
        if(user != null){
            data.put("data",user);
            data.put("status",200);
        }else{
            data.put("data","暂无信息");
            data.put("status",200);
        }
        return new ResponseEntity(data, HttpStatus.OK);
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取用户信息")
    @RequestMapping(value = "/user/id",method = RequestMethod.GET)
    public ResponseEntity getUser(int id){
        Map<String,Object> data = new HashMap<>();
        UserBean user = userService.getUser(id);
        if(user != null){
            data.put("data",user);
            data.put("status",200);
        }else{
            data.put("data","暂无信息");
            data.put("status",200);
        }
        return new ResponseEntity(data, HttpStatus.OK);
    }

    /**
     * 新增一条用户信息记录
     * @param request
     * @return
     */
    @ApiOperation(value = "新增一条用户信息记录")
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public ResponseEntity insertUser(HttpServletRequest request){
        Map<String,Object> data = new HashMap<>();

        boolean isx = isHavePermission(Integer.parseInt(request.getHeader("userId")),"w");//是否有写入的权限

        if(isx){

            //获取到JSONObject
            JSONObject jsonObject = GetJson.getJSONParam(request);

            //JSON对象转换成UserBean实体
            UserBean user = JSONObject.parseObject(jsonObject.toJSONString(),UserBean.class);

            int index = userService.insertUser(user);

            data.put("data",index);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }else{

            data.put("data","暂无操作权限!");

            data.put("status",403);

            return new ResponseEntity(data,HttpStatus.FORBIDDEN);
        }

    }

    /**
     * 删除一条用户信息记录
     * @param id
     */
    @ApiOperation(value = "删除一条用户信息记录")
    @RequestMapping(value = "/user",method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(HttpServletRequest request,int id){
        Map<String,Object> data = new HashMap<>();

        boolean isx = isHavePermission(Integer.parseInt(request.getHeader("userId")),"x");//是否有执行的权限

        if(isx){

            //如果有权限 -- 先删除对应用户的 角色、工区关联表中记录

            userService.deleteUserRole(id);

            rolesService.deleteWorkSpaceUserRole(id);

            //删除完成后，删除该用户
            int index = userService.deleteUser(id);

            data.put("data",index);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }else{

            data.put("data","暂无操作权限!");

            data.put("status",403);

            return new ResponseEntity(data,HttpStatus.FORBIDDEN);
        }

    }

    /**
     * 更新一条用户信息记录
     * @param request
     * @return
     */
    @ApiOperation(value = "更新一条用户信息记录")
    @RequestMapping(value = "/user",method = RequestMethod.PUT)
    public ResponseEntity updateUser(HttpServletRequest request){
        Map<String,Object> data = new HashMap<>();

        boolean isx = isHavePermission(Integer.parseInt(request.getHeader("userId")),"x");//是否有执行的权限

        if(isx){

            //获取到JSONObject
            JSONObject jsonObject = GetJson.getJSONParam(request);

            //JSON对象转换成UserBean实体
            UserBean user = JSONObject.parseObject(jsonObject.toJSONString(),UserBean.class);

            int index = userService.updateUser(user);

            data.put("data",index);

            data.put("status",200);

            return new ResponseEntity(data,HttpStatus.OK);

        }else{

            data.put("data","暂无操作权限!");

            data.put("status",403);

            return new ResponseEntity(data,HttpStatus.FORBIDDEN);
        }

    }

    /**
     * 获取所有的用户信息
     * @return
     */
    @ApiOperation(value = "获取所有的用户信息")
    @RequestMapping(value = "/user/all",method = RequestMethod.GET)
    public ResponseEntity getAllUsers(int userId){
        Map<String,Object> data = new HashMap<>();


            UserBean[] userBeans = userService.getAllUsers();

            if(userBeans != null && userBeans.length > 0){

                data.put("data",userBeans);

                data.put("status",200);

            }else{

                data.put("data","暂无信息");

                data.put("status",200);

            }

            return new ResponseEntity(data,HttpStatus.OK);

    }

    /**
     * 用户登录接口
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value = "用户登录接口")
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public ResponseEntity login(String username,String password){
        HashMap<String,Object> result=new HashMap<>();
        String token= TokenUtil.sign(new UserBean(username,password));
        UserBean user = userService.getUserByNameAndPassword(username,password);
//        UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        System.out.println(userDetails);
        if(user != null){
            user.setToken(token);
            HashMap<String,Object> data=new HashMap<>();
            data.put("userInfo",user);
            result.put("data",data);
            result.put("status",200);
            return new ResponseEntity(result,HttpStatus.OK);
        }else{
            return new ResponseEntity(result,HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 用户提交接口
     * @param username
     * @param submitPassword
     * @return
     */
    @ApiOperation(value = "用户提交接口")
    @RequestMapping(value = "/user/submit",method = RequestMethod.POST)
    public ResponseEntity submit(String username,String submitPassword){
        HashMap<String,Object> result=new HashMap<>();
        //根据用户名和提交密码获取提交人的用户信息
        UserBean user = userService.getUserByNameAndSubmitPassword(username,submitPassword);
            if(user != null){
            HashMap<String,Object> data=new HashMap<>();
            data.put("userInfo",user);
            result.put("data",data);
            result.put("status",200);
            return new ResponseEntity(result,HttpStatus.OK);
        }else{
            return new ResponseEntity(result,HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 是否有对应的权限
     * @param id
     * @return
     */
    public boolean isHavePermission(int id,String operation){
        //根据id获取用户多个角色 -- 优先
        Set<RolesBean> roleList = rolesService.findRoleByUserId(id);

        Set<PermissionsBean> permissionsBeansAll = new HashSet<PermissionsBean>();

        for(RolesBean rolesBean:roleList){

            //根绝角色获取所有的权限
            Set<PermissionsBean> permissionsBeansList = permissionsService.findByRoleId(rolesBean.getId());

            for(PermissionsBean permissionsBean:permissionsBeansList){

                //查看用户所有的权限
                permissionsBeansAll.add(permissionsBean);

            }

        }

        boolean isx = false;//是否有执行的权限

        //遍历权限
        for(PermissionsBean permissionsBean:permissionsBeansAll){

            //如果有执行的权限,则能够分配角色
            if(permissionsBean.getName().equals(operation)){

                isx = true;

                break;

            }
        }

        return isx;
    }
}
