package dao;

import java.util.List;

public interface BaseDAO<T, K> {
	boolean insert(T entity);

	boolean update(T entity);

	boolean delete(K id);

	T findById(K id);

	List<T> findAll();
}
