package org.plasma.sdo.repository.fuml;

import java.util.List;

import org.plasma.sdo.Alias;
import org.plasma.sdo.repository.Comment;
import org.plasma.sdo.repository.OpaqueBehavior;

public class FumlOpaqueBehavior extends FumlElement<org.modeldriven.fuml.repository.OpaqueBehavior> implements OpaqueBehavior {

	protected FumlOpaqueBehavior(org.modeldriven.fuml.repository.OpaqueBehavior delegate) {
		super(delegate);
	}

	@Override
	public String getName() {
		return element.getName();
	}

	@Override
	public String getId() {
		return element.getXmiId();
	}

	@Override
	public String getPhysicalName() {
		return super.getPhysicalName();
	}

	@Override
	public Alias findAlias() {
		return super.findAlias();
	}

	@Override
	public List<Comment> getComments() {
		return super.getComments();
	}

	@Override
	public String getLanguage() {
		return element.getLanguage();
	}

	@Override
	public String getBody() {
		return element.getBody();
	}

}
