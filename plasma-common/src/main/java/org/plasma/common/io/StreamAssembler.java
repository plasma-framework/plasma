package org.plasma.common.io;

public interface StreamAssembler {
	public boolean isIndent();

	public void setIndent(boolean indent);

	public String getIndentationToken();

	public void setIndentationToken(String indentationToken);
	
    public void start();
}
