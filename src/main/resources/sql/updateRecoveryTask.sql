UPDATE RECOVERY_TASKS as RT
SET
    RT.STATUS = #{status},
    RT.COUNT = #{count},
    RT.UPDATED_AT = sysdate,
    RT.UPDATE_USER = #{updateUser}
WHERE
    RT.ID = #{id}
