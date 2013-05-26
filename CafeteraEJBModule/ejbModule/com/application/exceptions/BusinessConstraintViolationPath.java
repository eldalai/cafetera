package com.application.exceptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Path;

/**
 * Identifica el path de una propiedad de un bean.
 * 
 * @author Nestor
 *
 */
public class BusinessConstraintViolationPath implements Path {
	String property;
	
	public BusinessConstraintViolationPath(String property) {
		this.property = property;
	}
	
	@Override
	public Iterator<Node> iterator() {
		List<Node> result = new ArrayList<Node>();
		if(property != null) {
			String[] props = property.split(".");
			for(String p:props) {
				result.add(new BusinessConstraintVuilationNode(p));
			}
		}
		return result.iterator();
	}

	@Override
	public String toString() {
		return property;
	}
}

class BusinessConstraintVuilationNode implements Path.Node {
	String name;
	
	public BusinessConstraintVuilationNode(String name) {
		this.name = name;
	}
	
	@Override
	public Integer getIndex() {
		return null;
	}

	@Override
	public Object getKey() {
		return name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isInIterable() {
		return false;
	}
	
}
