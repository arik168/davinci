package edp.davinci.dao;

import edp.davinci.dto.roleDto.RelRoleMember;
import edp.davinci.model.RelRoleUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RelRoleUserMapper {
    int insert(RelRoleUser record);

    @Delete({
            "delete from rel_role_user where id = #{id,jdbcType=BIGINT}"
    })
    int deleteById(Long id);


    @Select({
            "select",
            "id, user_id, role_id, create_by, create_time, update_by, update_time",
            "from rel_role_user",
            "where id = #{id,jdbcType=BIGINT}"
    })
    RelRoleUser getById(Long id);

    @Update({
            "update rel_role_user",
            "set user_id = #{userId,jdbcType=BIGINT},",
            "role_id = #{roleId,jdbcType=BIGINT},",
            "create_by = #{createBy,jdbcType=BIGINT},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "update_by = #{updateBy,jdbcType=BIGINT},",
            "update_time = #{updateTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateById(RelRoleUser record);


    @Select({
            "SELECT rru.id, u.id as 'user.id', IFNULL(u.`name`, u.username) as 'user.username', u.avatar",
            "FROM rel_role_user rru LEFT JOIN `user` u on u.id = rru.user_id",
            "WHERE rru.role_id = #{id}",
    })
    List<RelRoleMember> getMembersByRoleId(Long id);

    @Select({
            "select * from rel_role_user where role_id = #{roleId} and user_id = #{userId}"
    })
    RelRoleUser getByRoleAndMember(@Param("roleId") Long roleId, @Param("userId") Long userId);
}