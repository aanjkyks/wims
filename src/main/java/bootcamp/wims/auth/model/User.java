package bootcamp.wims.auth.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    @NotBlank
    @NotNull
	@Column(name = "name")
	private String username;
    @NotBlank
    @NotNull
	private String password;

	@Transient
	private String passwordConfirm;

	//	@Transient
	//	private Set<Role> roles = Collections.singleton("foobar");

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	//	public Set<Role> getRoles() {
	//		return roles;
	//	}
	//
	//	public void setRoles(Set<Role> roles) {
	//		this.roles = roles;
	//	}
}
