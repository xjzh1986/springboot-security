package com.reach.deepev.user.contorller;

import com.reach.deepev.auth.utils.JwtTokenUtil;
import com.reach.deepev.user.entity.SysUser;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import net.sf.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api2Doc(name = "用户")
@RestController
@RequestMapping("/sysUser")
public class SysUserContorller {

    @ApiComment("首页")
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('USER')")
    public SysUser index(@RequestBody SysUser sysUser) {
        System.out.println(sysUser.getUserName());
        System.out.println(sysUser.getPassword());

        return sysUser;
    }
}
