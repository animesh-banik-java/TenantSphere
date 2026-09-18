package com.banik.user_service.specification;

import com.banik.user_service.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> whereAll() {
        return ((root, query, cb) -> cb.conjunction());
    }
    public static Specification<User> whereName(final String name) {
        return ((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
    }

    public static Specification<User> wherePhone(final String phone) {
        return ((root, query, cb) -> cb.like(cb.lower(root.get("phoneNo")), "%" + phone + "%"));
    }

    public static Specification<User> whereEmail(final String email) {
        return ((root, query, cb) -> cb.like(root.get("email"), "%" + email.toLowerCase() + "%"));
    }


}
