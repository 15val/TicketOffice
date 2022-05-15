package model.dao;

import model.entity.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User>{

    List<User> findUsers(int currentPage, int recordsPerPage);
    int getNumberOfRows();
    Optional<User> findByNickname (String nickname);
    void topUpBalance(int id, BigInteger value);
}
