package skyoverfall

class Question extends Post {

	enum UpdateType{
	  Asked,
	  Modified,
	  Answered
	}

	// Question's title
	String title;

	// Number of times the question has been viewed
	int views = 0;

	// Last time there was an update in the question (question modified, answer...)
	Date lastUpdated;

	// Type of the last update
	UpdateType updateType = UpdateType.Asked;

	static hasMany = [answers : Answer, tags: Tag, comments : Comment]

    static constraints = {
    	title(blank: false, size: 10..150)
    	views(minValue : 0, display:false)
    	lastUpdated(display:false)
    	updateType(display: false)
    	//tags(minSize : 1)
    }

    static mapping =
    {
    	answers sort:'reputation', order:'desc'
      comments sort: 'dateCreated', order :'asc'
    }

}

/*code métier dans les service et pas les controlleurs*/