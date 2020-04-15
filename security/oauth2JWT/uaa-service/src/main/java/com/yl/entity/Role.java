package com.yl.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

/**
 * Created Cao Yuliang on 2020/4/15.
 */
@Data
@Entity
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 4248823631478765788L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Override
	public String getAuthority() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
