package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UsersUtil.USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (repository.containsValue(user.getName())) throw new RuntimeException("This name already exists");
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        ArrayList<User> usersSorted = new ArrayList<>(repository.values());
        Collections.sort(usersSorted, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        return usersSorted;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        User user = null;
        for (Map.Entry<Integer, User> entry : repository.entrySet())
        {
            User value = entry.getValue();
            if (email.equals(value.getEmail())) {
                user = value;
                break;
            }
        }
        return user;
    }
}
