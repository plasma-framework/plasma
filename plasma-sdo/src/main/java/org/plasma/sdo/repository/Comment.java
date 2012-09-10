package org.plasma.sdo.repository;




public class Comment {
	private fUML.Syntax.Classes.Kernel.Comment comment;

	public Comment(fUML.Syntax.Classes.Kernel.Comment comment) {
		super();
		this.comment = comment;		
	}
	
	public String getBody() {
		return this.comment.body;
	}	
}
