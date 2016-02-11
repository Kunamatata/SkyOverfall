package skyoverfall

class Vote  {

	/*
	*	upvote = 1
	*	neutral = 0
	*   downvote = -1
	*/
	int type = 0

	static belongsTo = [user : User, post : Post]
    static constraints = {
    }
}
