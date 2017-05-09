package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private final ResultSetExtractor<List<User>> ENUM_ROW_MAPPER = rs -> {
        List<User> users = new ArrayList<>();
        Map<Integer, User> uniqueUsers = new HashMap<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            if (!uniqueUsers.containsKey(user.getId())) {
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRegistered(rs.getTimestamp("registered"));
                user.setEnabled(rs.getBoolean("enabled"));
                user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                Set<Role> roles = new HashSet<>();
                roles.add(Role.valueOf(rs.getString("role")));
                user.setRoles(roles);
                users.add(user);
                uniqueUsers.put(user.getId(), user);
            } else {
                Set<Role> roles = uniqueUsers.get(user.getId()).getRoles();
                roles.add(Role.valueOf(rs.getString("role")));
            }
        }
        return users;
    };

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        /*String sql = "UPDATE user_roles SET user_id = ?, role = ?";*/
        String sql = "INSERT INTO user_roles (user_id, role) VALUES (?, ?)";
        ArrayList roles = new ArrayList(Arrays.asList(user.getRoles().toArray()));
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            batchUpdate(user, sql, roles);
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource);
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
            batchUpdate(user, sql, roles);
        }
        return user;
    }

    private void batchUpdate(final User user, String sql, final ArrayList roles) {
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, user.getId());
                ps.setString(2, roles.get(0).toString());
                roles.remove(0);
            }

            @Override
            public int getBatchSize() {
                return user.getRoles().size();
            }
        });
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users INNER JOIN user_roles r ON id=r.user_id WHERE id=?", ENUM_ROW_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON (users.id = user_roles.user_id) WHERE email=?", ENUM_ROW_MAPPER, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON (users.id = user_roles.user_id) ORDER BY name, email", ENUM_ROW_MAPPER);
    }
}