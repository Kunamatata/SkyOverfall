package skyoverfall

class Tag {

	// Name of the tag
	String name;

	// Description of the tag
	String description;

    static belongsTo = [question: Question]

    static constraints = {
    	name(blank:false, size:1..20)
    	description(blank:false, size: 1..10000)
    }

    String toString()
    {
    	return name;
    }
}
