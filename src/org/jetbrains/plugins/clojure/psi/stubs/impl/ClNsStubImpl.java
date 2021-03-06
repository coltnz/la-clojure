package org.jetbrains.plugins.clojure.psi.stubs.impl;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import org.jetbrains.plugins.clojure.psi.api.ns.ClNs;
import org.jetbrains.plugins.clojure.psi.stubs.api.ClNsStub;

/**
 * @author ilyas
 */
public class ClNsStubImpl extends StubBase<ClNs> implements ClNsStub {
  private final StringRef myName;

  public ClNsStubImpl(StubElement parent, StringRef name, final IStubElementType elementType) {
    super(parent, elementType);
    myName = name;
  }

  public String getName() {
    return StringRef.toString(myName);
  }

}