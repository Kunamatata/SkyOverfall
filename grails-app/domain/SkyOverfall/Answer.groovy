package skyoverfall

class Answer extends Post  {

	// Is this answer the best answer for the asker ?
	boolean bestAnswered = false;

	static belongsTo = [question : Question]
	static hasMany = [comments : Comment]

    static constraints = {
    	bestAnswered(display:false)
    }

    static mapping =
    {
      comments sort: 'dateCreated', order :'asc'
    }
}
