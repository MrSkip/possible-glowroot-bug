package com.glowroot.possiblebug.mongochanges;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.glowroot.possiblebug.dao.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@ChangeLog(order = "1")
public class Change1 {

  @ChangeSet(id = "_id_1", order = "1", author = "Tanos")
  public void performChange1(final MongoTemplate template) {
    final Query query = getQuery();
    final List<User> users = template.find(query, User.class);
    System.out.println("Number of users in DB " + users.size());
  }

  private Query getQuery() {
    final Query query = new Query();
    Criteria criteria = Criteria.where("name").is("any name works");
    query.addCriteria(criteria);
    return query;
  }
}
