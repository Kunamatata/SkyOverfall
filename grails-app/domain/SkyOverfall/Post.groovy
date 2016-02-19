package skyoverfall

class Post {

	// The text of the post
	String text;

	// Reputation of the post
	int reputation = 0;

	// Time when the post was created (handled by Groovy)
	Date dateCreated;


	static belongsTo = [user : User]
	static hasMany = [votes : Vote]

    static constraints = {
    	text(blank:false ,size : 20..10000)
    	dateCreated(display:false)
    	reputation(display:false)
    }
}
