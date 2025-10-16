package model.data;

import java.util.List;

import model.ModelException;

public interface GenericDAO<T> {
	void update(T data) throws ModelException;
	void insert(T data) throws ModelException;
	void delete(T data) throws ModelException;
	T findById(T data) throws ModelException;
	List<T> findAll() throws ModelException;
}
