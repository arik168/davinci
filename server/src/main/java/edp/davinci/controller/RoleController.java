/*
 * <<
 *  Davinci
 *  ==
 *  Copyright (C) 2016 - 2018 EDP
 *  ==
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  >>
 *
 */

package edp.davinci.controller;


import edp.core.annotation.CurrentUser;
import edp.davinci.common.controller.BaseController;
import edp.davinci.core.common.Constants;
import edp.davinci.core.common.ResultMap;
import edp.davinci.dto.roleDto.*;
import edp.davinci.model.Role;
import edp.davinci.model.User;
import edp.davinci.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(value = "/roles", tags = "roles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ApiResponses(@ApiResponse(code = 404, message = "role not found"))
@Slf4j
@RestController
@RequestMapping(value = Constants.BASE_API_PATH + "/roles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;


    /**
     * 新建Role
     *
     * @param role
     * @param bindingResult
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "create role", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createProject(@Valid @RequestBody RoleCreate role,
                                        @ApiIgnore BindingResult bindingResult,
                                        @ApiIgnore @CurrentUser User user,
                                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message(bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        Role newRole = roleService.createRole(role, user);

        return ResponseEntity.ok(new ResultMap(tokenUtils).successAndRefreshToken(request).payload(newRole));
    }


    /**
     * 删除role
     *
     * @param id
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "delete role")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteRole(@PathVariable Long id,
                                     @ApiIgnore @CurrentUser User user,
                                     HttpServletRequest request) {
        if (invalidId(id)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid role id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        roleService.deleteRole(id, user);

        return ResponseEntity.ok(new ResultMap(tokenUtils).successAndRefreshToken(request));
    }


    /**
     * 修改 Role
     *
     * @param id
     * @param role
     * @param bindingResult
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "update role")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRole(@PathVariable Long id,
                                     @Valid @RequestBody RoleUpdate role,
                                     @ApiIgnore BindingResult bindingResult,
                                     @ApiIgnore @CurrentUser User user,
                                     HttpServletRequest request) {
        if (invalidId(id)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid role id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        if (bindingResult.hasErrors()) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message(bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        roleService.updateRole(id, role, user);

        return ResponseEntity.ok(new ResultMap(tokenUtils).successAndRefreshToken(request));
    }


    /**
     * 获取 Role详情
     *
     * @param id
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "get role")
    @GetMapping("/{id}")
    public ResponseEntity getRole(@PathVariable Long id,
                                  @ApiIgnore @CurrentUser User user,
                                  HttpServletRequest request) {
        if (invalidId(id)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid role id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        Role role = roleService.getRoleInfo(id, user);
        return ResponseEntity.ok(new ResultMap(tokenUtils).successAndRefreshToken(request).payload(role));
    }


    /**
     * 添加Role与User关联
     *
     * @param id
     * @param memberId
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "add relation between a role and a member")
    @PostMapping("/{id}/member/{memberId}")
    public ResponseEntity addMember(@PathVariable Long id,
                                    @PathVariable Long memberId,
                                    @ApiIgnore @CurrentUser User user,
                                    HttpServletRequest request) {
        if (invalidId(id)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid role id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        if (invalidId(memberId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid member id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        RelRoleMember relRoleMember = roleService.addMember(id, memberId, user);
        return ResponseEntity.ok(new ResultMap(tokenUtils).successAndRefreshToken(request).payload(relRoleMember));
    }


    /**
     * 删除Role与User关联
     *
     * @param relationId
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "delete relation between a role and a member")
    @DeleteMapping("/member/{relationId}")
    public ResponseEntity deleteMember(@PathVariable Long relationId,
                                       @ApiIgnore @CurrentUser User user,
                                       HttpServletRequest request) {
        if (invalidId(relationId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid relation id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        roleService.deleteMember(relationId, user);
        return ResponseEntity.ok(new ResultMap(tokenUtils).successAndRefreshToken(request));
    }


    /**
     * 获取 Role 关联用户
     *
     * @param id
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "get members")
    @GetMapping("/{id}/members")
    public ResponseEntity getMembers(@PathVariable Long id,
                                     @ApiIgnore @CurrentUser User user,
                                     HttpServletRequest request) {
        if (invalidId(id)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid role id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        List<RelRoleMember> members = roleService.getMembers(id, user);
        return ResponseEntity.ok(new ResultMap(tokenUtils).successAndRefreshToken(request).payloads(members));
    }


    /**
     * 添加Role与Project关联
     *
     * @param id
     * @param projectId
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "add relation between a role and a project")
    @PostMapping("/{id}/project/{projectId}")
    public ResponseEntity addProject(@PathVariable Long id,
                                     @PathVariable Long projectId,
                                     @ApiIgnore @CurrentUser User user,
                                     HttpServletRequest request) {
        if (invalidId(id)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid role id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        if (invalidId(projectId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid project id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        RoleProject roleProject = roleService.addProject(id, projectId, user);
        return ResponseEntity.ok(new ResultMap(tokenUtils).successAndRefreshToken(request).payload(roleProject));
    }


    /**
     * 删除Role和Project之间的关联
     *
     * @param relationId
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "delete relation between a role and a project")
    @DeleteMapping("/project/{relationId}")
    public ResponseEntity deleteProject(@PathVariable Long relationId,
                                        @ApiIgnore @CurrentUser User user,
                                        HttpServletRequest request) {
        if (invalidId(relationId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid relation id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        roleService.deleteProject(relationId, user);
        return ResponseEntity.ok(new ResultMap(tokenUtils).successAndRefreshToken(request));
    }


    /**
     * 修改Role和Project之间的关联
     *
     * @param relationId
     * @param projectRole
     * @param bindingResult
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "update relation between a role and a project", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "/project/{relationId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProjet(@PathVariable Long relationId,
                                       @Valid @RequestBody RelRoleProjectDto projectRole,
                                       @ApiIgnore BindingResult bindingResult,
                                       @ApiIgnore @CurrentUser User user,
                                       HttpServletRequest request) {
        if (invalidId(relationId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid releation id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        if (bindingResult.hasErrors()) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message(bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        roleService.updateProjectRole(relationId, projectRole, user);
        return ResponseEntity.ok(new ResultMap(tokenUtils).successAndRefreshToken(request));
    }
}
