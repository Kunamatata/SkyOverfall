package skyoverfall

class Comment{

  // The text of the comment
  String text;

  // Time when the post was created (handled by Groovy)
  Date dateCreated;

	static belongsTo = [post: Post, user: User]

    static mapping = {
        sort "dateCreated"
    }

    static constraints = {
    }
}
