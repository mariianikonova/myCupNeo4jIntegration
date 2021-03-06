package org.mycup.datastore.dao;

import org.mycup.datastore.entity.User;
import org.springframework.data.neo4j.repository.CRUDRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mariiarichka on 11.04.14.
 */
@Repository
public interface UserDAO extends CRUDRepository <User>{

    User findByMail(String mail);

}
