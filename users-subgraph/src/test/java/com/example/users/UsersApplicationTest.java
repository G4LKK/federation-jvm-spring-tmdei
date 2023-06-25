package com.example.users;

import com.example.users.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;

@AutoConfigureHttpGraphQlTester
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersApplicationTest {

  @Autowired
  private HttpGraphQlTester tester;

  @Test
  public void verifiesProductQuery() {
    String query = """
      query Users($userId: ID!) {
        User(id: $userId) {
          id
          name
          age
        }
      }
      """;

    // new User("1020","John Shine"),
    tester.document(query)
      .variable("userId", "1020")
      .execute()
      .path("user")
      .entity(User.class)
      .isEqualTo(new User("1020",25,"John Shine"));
  }
}
