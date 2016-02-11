package skyoverfall

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)

class User implements Serializable {

	private static final long serialVersionUID = 1

	transient springSecurityService

	String username
	String password
	String avatar = "http://powerforum.newanglemedia.com/2013/themes/intellisystems/site/img/icons/user/default-profile.png"
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	int reputation = 0
	String profileDescription = "";

	/*String email;*/


	User(String username, String password) {
		this()
		this.username = username
		this.password = password
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this)*.role
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static hasMany = [posts: Post, votes : Vote];

	static transients = ['springSecurityService']

	static constraints = {
			username(unique: true, blank: false, size : 1..20)
			avatar(maxSize:500)/*
    	password(blank: false, size : 1..30)
    	//email(unique: true, email:true, blank : false)
      reputation(display:false)*/
	}

	static mapping = {
		password column: '`password`'
	}
}
