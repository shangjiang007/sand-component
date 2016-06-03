package cn.com.sandpay.processmanager.core;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;

public class ExecutionContext implements Serializable {

	private final Map<String, Object> map;

	public ExecutionContext() {
		map = new ConcurrentHashMap<String, Object>();
	}

	public ExecutionContext(Map<String, Object> map) {
		this.map = new ConcurrentHashMap<String, Object>(map);
	}

	public ExecutionContext(ExecutionContext executionContext) {
		this();
		if (executionContext == null) {
			return;
		}
		for (Entry<String, Object> entry : executionContext.entrySet()) {
			this.map.put(entry.getKey(), entry.getValue());
		}
	}

	public void put(String key, Object value) {
		if (value != null) {
			// FIXME 不需要判断序列化
			//Assert.isInstanceOf(Serializable.class, value, "Value: [ " + value + "must be serializable.");
			Object result = map.put(key, value);
		}
		else {
			Object result = map.remove(key);
		}
	}

	public Object get(String key) {
		return map.get(key);
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<Entry<String, Object>> entrySet() {
		return map.entrySet();
	}

	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	public Object remove(String key) {
		return map.remove(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public boolean equals(Object obj) {
		if (obj instanceof ExecutionContext == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ExecutionContext rhs = (ExecutionContext) obj;
		return this.entrySet().equals(rhs.entrySet());
	}

	public int hashCode() {
		return map.hashCode();
	}

	public String toString() {
		return map.toString();
	}

	public int size() {
		return map.size();
	}

}
