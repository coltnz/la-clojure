package org.jetbrains.plugins.clojure.repl;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiNamedElement;
import com.intellij.ui.content.Content;
import org.jetbrains.plugins.clojure.repl.toolwindow.REPLToolWindowFactory;

import java.util.Collection;

/**
* @author Colin Fleming
*/
public interface REPL
{
  Key<REPL> REPL_KEY = Key.create(REPLToolWindowFactory.TOOL_WINDOW_ID);
  Key<Content> CONTENT_KEY = Key.create("Clojure.REPL.Content");

  void execute(String command);

  void start() throws REPLException;

  void stop() throws REPLException;

  boolean stopQuery();

  void onShutdown(Runnable runnable);

  boolean isActive();

  ClojureConsoleView getConsoleView();

  // Guaranteed to be called after start()
  AnAction[] getToolbarActions() throws REPLException;

  void setTabName(String tabName);

  Collection<PsiNamedElement> getSymbolVariants(PsiManager manager, PsiElement symbol);
}
