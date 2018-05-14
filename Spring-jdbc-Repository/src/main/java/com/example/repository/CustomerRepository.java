package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Customer;

/**
 * @author Hansong Y
 *
 * Dao (DB 연결, 트랜잭션등의 관리, SQL 실행결과를  자바객체로 변환 ResultSet과 동일)
 */
@Repository
@Transactional
public class CustomerRepository {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	/**
	 * lamda 식을 활용한 SQL 실행결과를 자바 객체 형태로 변환 
	 * @return Customer
	 */
	private static final RowMapper<Customer> customerRowMapper = (rs, i)-> {
		Integer id = rs.getInt("id");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		
		return new Customer(id,firstName,lastName);
	};
	/**
	 * customers 테이블에 있는 컬럼 데이터를 자바 객체 리스트로 변환하여 가져온다.
	 * @return
	 */
	public List<Customer> findAll(){
		String sql = "SELECT id, first_name, first_name, last_name FROM customers ORDER BY id";
		List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper); // 실행결과를 자바 객체 List로 변환
		return customers;
	}
	/**
	 * customers 테이블에 있는 컬럼 데이터를 자바 객체로 변환하여 가져온다.
	 * MapSqlParameterSource 조회용 SqlParameterSource 객체
	 * @return
	 */
	public Customer findOne(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		String sql = "SELECT id, first_name, first_name, last_name FROM customers WHERE id = :id";
		return jdbcTemplate.queryForObject(sql, param, customerRowMapper);// 실행결과를 객체로 변환
	}
	
	/**
	 * 
	 * @param customer
	 * @return 
	 */
	public Customer save(Customer customer) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(customer);
		String sql = "INSERT INTO customers(first_name,last_name) " +
						"values(:firstName, :lastName)";
		if(customer.getId() == null) {
			jdbcTemplate.update(sql, param);
		}else {
			sql = "UPDATE customers SET first_name=:firstName, "
					+ "last_name=:lastName WHERE id = :id";
			jdbcTemplate.update(sql, param);
		}
		return customer;
	}
	
	/**
	 * @param id
	 * delte시 에도 update메소드 이용 
	 */
	public void delete(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		String sql = "DELETE FROM customers WHERE id =:id";
		jdbcTemplate.update(sql, param);
	}
}
