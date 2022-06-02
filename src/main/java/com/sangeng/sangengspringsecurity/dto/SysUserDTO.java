/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.sangeng.sangengspringsecurity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@Data
@ApiModel(value = "用户管理")
public class SysUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiModelProperty(value = "姓名", required = true)
    private String realName;

    @ApiModelProperty(value = "头像")
    private String headUrl;

    @ApiModelProperty(value = "性别   0：男   1：女    2：保密", required = true)
    private Integer gender;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "部门ID", required = true)
    private Long deptId;

    @ApiModelProperty(value = "状态  0：停用  1：正常", required = true)
    private Integer status;

    @ApiModelProperty(value = "所属区域标识")
    private Long regionId;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;

    @ApiModelProperty(value = "超级管理员   0：否   1：是")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer superAdmin;

    @ApiModelProperty(value = "角色ID列表")
    private List<Long> roleIdList;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "所属区域名称")
    private String regionName;

    @ApiModelProperty(value = "所属角色")
    private String roleId;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "过期时间")
    private int expire;

    @ApiModelProperty(value = "openId")
    private String openId;

    @ApiModelProperty(value = "是否可以登录后台 1是 2否")
    private String isLoginBack;

    @ApiModelProperty(value = "小程序openid")
    private String mnOpenId;

    @ApiModelProperty(value = "用户来源 1：后台（默认） 2：入户调查 3：综合执法")
    private String source;

    @ApiModelProperty(value = "数据是否被删除,0未删除,1删除")
    private Integer deleted;

    @ApiModelProperty(value = "用户备注")
    private String remark;
}
