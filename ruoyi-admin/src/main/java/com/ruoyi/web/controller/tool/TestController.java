package com.ruoyi.web.controller.tool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * swagger 用户测试方法
 * 
 * @author ruoyi
 */
@Api("User Info Management")
@RestController
@RequestMapping("/test/user")
public class TestController extends BaseController
{
    private final static Map<Integer, UserEntity> users = new LinkedHashMap<Integer, UserEntity>();
    {
        users.put(1, new UserEntity(1, "admin", "admin123", "15888888888"));
        users.put(2, new UserEntity(2, "ry", "admin123", "15666666666"));
    }

    @ApiOperation("List users")
    @GetMapping("/list")
    public R<List<UserEntity>> userList()
    {
        List<UserEntity> userList = new ArrayList<UserEntity>(users.values());
        return R.ok(userList);
    }

    @ApiOperation("Get user info")
    @ApiImplicitParam(name = "userId", value = "User ID", required = true, dataType = "int", paramType = "path",
            dataTypeClass = Integer.class)
    @GetMapping("/{userId}")
    public R<UserEntity> getUser(@PathVariable Integer userId)
    {
        if (!users.isEmpty() && users.containsKey(userId))
        {
            return R.ok(users.get(userId));
        }
        else
        {
            return R.fail("User doesn't exist");
        }
    }

    @ApiOperation("Add new user")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "user id", dataType = "Integer", dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "username", value = "username", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "password", value = "user password", dataType = "String", dataTypeClass =
                String.class),
        @ApiImplicitParam(name = "mobile", value = "user phone number", dataType = "String", dataTypeClass =
                String.class)
    })
    @PostMapping("/save")
    public R<String> save(UserEntity user)
    {
        if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId()))
        {
            return R.fail("User ID can't be null");
        }
        users.put(user.getUserId(), user);
        return R.ok();
    }

    @ApiOperation("Update user")
    @PutMapping("/update")
    public R<String> update(@RequestBody UserEntity user)
    {
        if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId()))
        {
            return R.fail("User ID can't be blank");
        }
        if (users.isEmpty() || !users.containsKey(user.getUserId()))
        {
            return R.fail("User doesn't exist");
        }
        users.remove(user.getUserId());
        users.put(user.getUserId(), user);
        return R.ok();
    }

    @ApiOperation("Delete user")
    @ApiImplicitParam(name = "userId", value = "User ID", required = true, dataType = "int", paramType = "path",
            dataTypeClass = Integer.class)
    @DeleteMapping("/{userId}")
    public R<String> delete(@PathVariable Integer userId)
    {
        if (!users.isEmpty() && users.containsKey(userId))
        {
            users.remove(userId);
            return R.ok();
        }
        else
        {
            return R.fail("User doesn't exist");
        }
    }
}

@ApiModel(value = "UserEntity", description = "User entity")
class UserEntity
{
    @ApiModelProperty("userId")
    private Integer userId;

    @ApiModelProperty("username")
    private String username;

    @ApiModelProperty("user password")
    private String password;

    @ApiModelProperty("user phone number")
    private String mobile;

    public UserEntity()
    {

    }

    public UserEntity(Integer userId, String username, String password, String mobile)
    {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
}
