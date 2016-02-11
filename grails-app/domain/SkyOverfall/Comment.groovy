package skyoverfall

class Comment extends Post{

	static belongsTo = [question : Question, answer : Answer]

    static constraints = {
    	
    }
}
