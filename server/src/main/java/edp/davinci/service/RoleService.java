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

package edp.davinci.service;

import edp.core.exception.NotFoundException;
import edp.core.exception.ServerException;
import edp.core.exception.UnAuthorizedExecption;
import edp.davinci.dto.roleDto.*;
import edp.davinci.model.Role;
import edp.davinci.model.User;

import java.util.List;

public interface RoleService {


    /**
     * 新建Role
     *
     * @param roleCreate
     * @param user
     * @return
     */
    Role createRole(RoleCreate roleCreate, User user) throws ServerException, UnAuthorizedExecption, NotFoundException;


    /**
     * 删除 Role
     *
     * @param id
     * @param user
     * @return
     */
    boolean deleteRole(Long id, User user) throws ServerException, UnAuthorizedExecption, NotFoundException;


    /**
     * 修改Role
     *
     * @param id
     * @param roleUpdate
     * @param user
     * @return
     */
    boolean updateRole(Long id, RoleUpdate roleUpdate, User user) throws ServerException, UnAuthorizedExecption, NotFoundException;


    /**
     * 获取单条Role详情
     *
     * @param id
     * @param user
     * @return
     * @throws ServerException
     * @throws UnAuthorizedExecption
     * @throws NotFoundException
     */
    Role getRoleInfo(Long id, User user) throws ServerException, UnAuthorizedExecption, NotFoundException;


    /**
     *  添加Role与User关联
     *
     * @param id
     * @param memberId
     * @param user
     * @return
     * @throws ServerException
     * @throws UnAuthorizedExecption
     * @throws NotFoundException
     */
    RelRoleMember addMember(Long id, Long memberId, User user) throws ServerException, UnAuthorizedExecption, NotFoundException;

    /**
     * 删除Role与User关联
     *
     * @param relationId
     * @param user
     * @return
     * @throws ServerException
     * @throws UnAuthorizedExecption
     * @throws NotFoundException
     */
    boolean deleteMember(Long relationId, User user) throws ServerException, UnAuthorizedExecption, NotFoundException;


    /**
     * 获取单条Role所关联的Users
     *
     * @param id
     * @param user
     * @return
     * @throws ServerException
     * @throws UnAuthorizedExecption
     * @throws NotFoundException
     */
    List<RelRoleMember> getMembers(Long id, User user) throws ServerException, UnAuthorizedExecption, NotFoundException;

    /**
     * 添加Role与Project关联
     *
     * @param id
     * @param projectId
     * @param user
     * @return
     * @throws ServerException
     * @throws UnAuthorizedExecption
     * @throws NotFoundException
     */
    RoleProject addProject(Long id, Long projectId, User user) throws ServerException, UnAuthorizedExecption, NotFoundException;

    /**
     * 删除Role与Project关联
     *
     * @param relationId
     * @param user
     * @return
     * @throws ServerException
     * @throws UnAuthorizedExecption
     * @throws NotFoundException
     */
    boolean deleteProject(Long relationId, User user) throws ServerException, UnAuthorizedExecption, NotFoundException;

    /**
     * 修改Role与Project关联信息
     *
     * @param relationId
     * @param projectRoleDto
     * @param user
     * @return
     * @throws ServerException
     * @throws UnAuthorizedExecption
     * @throws NotFoundException
     */
    boolean updateProjectRole(Long relationId, RelRoleProjectDto projectRoleDto, User user) throws ServerException, UnAuthorizedExecption, NotFoundException;
}
