SELECT
    id, type, status, hogeId, parameter, count, created_at as createdAt, updated_at as updatedAt, update_user as updateUser
FROM RECOVERY_TASKS as RT
WHERE
    RT.STATUS = 'CREATED' OR RT.STATUS = 'RUNNING' AND
    RT.COUNT < 20;
