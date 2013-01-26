package org.plasma.provisioning.xsd;
import org.plasma.provisioning.Class;

public interface ClassVisitor {
    public void visit(Class target, Class source);
}
