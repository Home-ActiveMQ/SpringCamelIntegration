package com.mkyong.repository;

import com.mkyong.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @see https://stackoverflow.com/questions/8550124/what-is-the-difference-between-dao-and-repository-patterns
 *      https://www.baeldung.com/simplifying-the-data-access-layer-with-spring-and-java-generics
 */

public interface BankRepository extends JpaRepository<Bank, Long> {

}
