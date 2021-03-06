package org.jetbrains.plugins.clojure.repl.toolwindow.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import org.jetbrains.plugins.clojure.ClojureIcons;
import org.jetbrains.plugins.clojure.repl.REPLProvider;
import org.jetbrains.plugins.clojure.utils.Actions;

public class NewInProcessConsoleAction extends AnAction implements DumbAware
{
  public NewInProcessConsoleAction()
  {
    getTemplatePresentation().setIcon(ClojureIcons.CLOJURE_ICON_16x16);
  }

  public void update(AnActionEvent e)
  {
    Module m = Actions.getModule(e);
    Presentation presentation = e.getPresentation();
    if (m == null)
    {
      presentation.setEnabled(false);
      return;
    }

//    Clojure clojure = ClojureImplementation.from(m.getProject());
//    REPLProvider provider = clojure.getInProcessREPLProvider();
//    presentation.setEnabled(provider.isSupported());
//    super.update(e);
  }

  public void actionPerformed(AnActionEvent event)
  {
    Module module = Actions.getModule(event);
    assert (module != null) : "Module is null";

    // Find the tool window
    Project project = module.getProject();
//    Clojure clojure = ClojureImplementation.from(project);
//    REPLProvider provider = clojure.getInProcessREPLProvider();
//    if (!provider.isSupported())
//    {
//      return;
//    }
//
//    provider.createREPL(project, module);
  }
}
