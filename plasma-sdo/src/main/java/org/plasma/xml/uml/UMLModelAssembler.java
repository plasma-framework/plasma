package org.plasma.xml.uml;

import java.io.OutputStream;

import org.jdom2.Document;
import org.jdom2.output.Format;

public interface UMLModelAssembler {

	public String getDestNamespaceURI();

	public void setDestNamespaceURI(String destNamespaceURI);

	public String getDestNamespacePrefix();

	public void setDestNamespacePrefix(String destNamespacePrefix);

	public boolean isDerivePackageNamesFromURIs();

	public void setDerivePackageNamesFromURIs(
			boolean derivePackageNamesFromURIs);

	public Document getDocument();

	public String getContent();

	public String getContent(java.lang.String indent, boolean newlines);

	public String getContent(Format format);

	public void getContent(OutputStream stream);

	public void getContent(OutputStream stream,
			java.lang.String indent, boolean newlines);

	public void getContent(OutputStream stream, Format format);

}